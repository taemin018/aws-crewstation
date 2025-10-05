package com.example.crewstation.service.reply;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.dto.reply.ReplyCriteriaDTO;
import com.example.crewstation.dto.reply.ReplyDTO;
import com.example.crewstation.repository.reply.ReplyDAO;
import com.example.crewstation.service.s3.S3Service;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyServiceImpl implements ReplyService {
    private final ReplyDAO replyDAO;
    private final S3Service s3Service;

    @Override
    public ReplyCriteriaDTO getReplies(int page,
                                       Long postId,
                                       CustomUserDetails customUserDetails) {
        ReplyCriteriaDTO replyCriteriaDTO = new ReplyCriteriaDTO();
        Criteria criteria = new Criteria(page, replyDAO.findAllCountByPostId(postId),5,5);
        List<ReplyDTO> replies = replyDAO.findAllByPostId(postId, criteria);
        replies.forEach(reply -> {
            if(reply.getFilePath() !=null){
                reply.setFilePath(s3Service.getPreSignedUrl(reply.getFilePath(), Duration.ofMinutes(5)));
            }
            reply.setRelativeDate(DateUtils.toRelativeTime(reply.getCreatedDatetime()));
            if (customUserDetails != null) {
                reply.setWriter(reply.getMemberId().equals(customUserDetails.getId()));
            }
        });
        replyCriteriaDTO.setReplies(replies);
        return replyCriteriaDTO;
    }
}
