package com.example.crewstation.service.reply;


import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.dto.reply.ReplyCriteriaDTO;
import com.example.crewstation.dto.reply.ReplyDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ReplyService {
    public ReplyCriteriaDTO getReplies(int page,
                                       Long postId,
                                       CustomUserDetails customUserDetails);
}
