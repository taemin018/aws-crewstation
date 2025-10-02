package com.example.crewstation.service.diary;

import com.example.crewstation.dto.accompany.AccompanyDTO;
import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.dto.diary.*;
import com.example.crewstation.repository.diary.DiaryDAO;
import com.example.crewstation.repository.like.LikeDAO;
import com.example.crewstation.repository.section.SectionDAO;
import com.example.crewstation.service.s3.S3Service;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.DateUtils;
import com.example.crewstation.util.ScrollCriteria;
import com.example.crewstation.util.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

    private final DiaryDAO diaryDAO;
    private final S3Service s3Service;
    private final SectionDAO sectionDAO;
    private final DiaryTransactionService diaryTransactionService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final LikeDAO likeDAO;
    private static final Map<String,String> ORDER_TYPE_MAP = Map.of("좋아요순","diary_like_count","최신순","post_id");
    private static final Map<String,String> CATEGORY_MAP = Map.of("crew","not null","individual","null");
    private final DiaryDTO diaryDTO;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DiaryDTO> selectDiaryList(int limit) {
        List<DiaryDTO> diaries = (List<DiaryDTO>) redisTemplate.opsForValue().get("diaries");

        if (diaries != null) {
            diaries.forEach(diary -> {
                String filePath = diary.getDiaryFilePath();
                String presignedUrl = s3Service.getPreSignedUrl(filePath, Duration.ofMinutes(5));

                diary.setFileCount(sectionDAO.findSectionFileCount(diary.getPostId()));

                log.info("Diary ID={}, 원본 filePath={}, 발급된 presignedUrl={}",
                        diary, filePath, presignedUrl);
                diary.setDiaryFilePath(s3Service.getPreSignedUrl(diary.getDiaryFilePath(),
                        Duration.ofMinutes(5)));
            });
            return diaries;

        }
        return diaryTransactionService.selectDiaryList(limit);
    }

    @Override
    public LikedDiaryCriteriaDTO getDiariesLikedByMemberId(Long memberId, ScrollCriteria criteria) {
        log.info("좋아요 다이어리 조회 - memberId={}, page={}, size={}", memberId, criteria.getPage(), criteria.getSize());

        // 목록 조회
        List<LikedDiaryDTO> diaries = diaryDAO.findDiariesLikedByMemberId(memberId, criteria);

        // 전체 개수 (hasMore 계산용)
        int totalCount = diaryDAO.countDiariesLikedByMemberId(memberId);
        criteria.setTotal(totalCount);

        // DTO 조립
        LikedDiaryCriteriaDTO dto = new LikedDiaryCriteriaDTO();
        dto.setLikedDiaryDTOs(diaries);
        dto.setCriteria(criteria);

        return dto;
    }

    //  좋아요 한 다이어리 개수
    @Override
    public int getCountDiariesLikedByMemberId(Long memberId) {
        log.info("memberId: {}", memberId);
        return diaryDAO.countDiariesLikedByMemberId(memberId);
    }

    //  좋아요 취소
    @Override
    public void cancelLike(Long memberId, Long diaryId) {
        diaryDAO.deleteLike(memberId, diaryId);
    }

    // 댓글 단 다이어리 목록 조회
    @Override
    public ReplyDiaryCriteriaDTO getReplyDiariesByMemberId(Long memberId, ScrollCriteria criteria) {
        log.info("댓글 단 다이어리 조회 - memberId={}, page={}, size={}", memberId, criteria.getPage(), criteria.getSize());

        List<ReplyDiaryDTO> diaries = diaryDAO.findReplyDiariesByMemberId(memberId, criteria);

        // 상대시간 변환
        diaries.forEach(diary -> diary.setRelativeDatetime(DateUtils.toRelativeTime(diary.getCreatedDatetime())));

        // 전체 개수 (hasMore 계산용)
        int totalCount = diaryDAO.countReplyDiariesByMemberId(memberId);
        criteria.setTotal(totalCount);

        // DTO 조립
        ReplyDiaryCriteriaDTO dto = new ReplyDiaryCriteriaDTO();
        dto.setReplyDiaryDTOs(diaries);
        dto.setCriteria(criteria);

        return dto;
    }

    //  내가 댓글 단 일기 개수
    @Override
    public int getCountReplyDiariesByMemberId(Long memberId) {
        log.info("memberId: {}", memberId);
        return diaryDAO.countReplyDiariesByMemberId(memberId);
    }

    @Override
    public DiaryCriteriaDTO getDiaries(Search search, CustomUserDetails customUserDetails) {
        DiaryCriteriaDTO dto = new DiaryCriteriaDTO();
        Search newSearch = new Search();
        int page = search.getPage();
        dto.setSearch(search);

        String category = search.getCategory();
        String orderType = search.getOrderType();
        newSearch.setKeyword(search.getKeyword());
        newSearch.setOrderType(ORDER_TYPE_MAP.getOrDefault(orderType,"post_id"));
        newSearch.setCategory(CATEGORY_MAP.getOrDefault(category,""));
        Criteria criteria = new Criteria(page, diaryDAO.findCountAllByKeyword(newSearch),3,3);
        List<DiaryDTO> diaries = diaryDAO.findAllByKeyword(criteria, newSearch);
        diaries.forEach(diary -> {
            if(diary.getMemberFilePath()!= null){
                diary.setMemberFilePath(s3Service.getPreSignedUrl(diary.getMemberFilePath(), Duration.ofMinutes(5)));
            }
            if(diary.getDiaryFilePath()!= null){
                diary.setDiaryFilePath(s3Service.getPreSignedUrl(diary.getDiaryFilePath(), Duration.ofMinutes(5)));
            }
            if(customUserDetails != null){
                diary.setUserId(customUserDetails.getId());
                diary.setLikeId(likeDAO.isLikeByPostIdAndMemberId(diary));
            }
//            diary.setUserId(1L); // 임시
//            diary.setLikeId(likeDAO.isLikeByPostIdAndMemberId(diary));
            diary.setFileCount(sectionDAO.findSectionFileCount(diary.getPostId()));
        });
        criteria.setHasMore(diaries.size() > criteria.getRowCount());

        if (criteria.isHasMore()) {
            diaries.remove(diaries.size() - 1);
        }
        dto.setDiaryDTOs(diaries);
        dto.setCriteria(criteria);
        return dto;
    }
}
