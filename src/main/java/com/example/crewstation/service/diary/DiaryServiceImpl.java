package com.example.crewstation.service.diary;

import com.example.crewstation.aop.aspect.annotation.LogReturnStatus;
import com.example.crewstation.aop.aspect.annotation.LogStatus;
import com.example.crewstation.common.enumeration.Secret;
import com.example.crewstation.common.enumeration.Type;
import com.example.crewstation.common.exception.DiaryNotFoundException;
import com.example.crewstation.domain.crew.CrewDiaryVO;
import com.example.crewstation.domain.diary.country.DiaryCountryVO;
import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.domain.file.section.FilePostSectionVO;
import com.example.crewstation.dto.country.CountryDTO;
import com.example.crewstation.dto.diary.*;
import com.example.crewstation.dto.file.FileDTO;
import com.example.crewstation.dto.file.section.FilePostSectionDTO;
import com.example.crewstation.dto.file.tag.ImageDTO;
import com.example.crewstation.dto.file.tag.PostDiaryDetailTagDTO;
import com.example.crewstation.dto.post.PostDTO;
import com.example.crewstation.dto.post.file.tag.PostFileTagDTO;
import com.example.crewstation.dto.post.section.SectionDTO;
import com.example.crewstation.mapper.crew.diary.CrewDiaryMapper;
import com.example.crewstation.repository.crew.diary.CrewDiaryDAO;
import com.example.crewstation.repository.diary.DiaryDAO;
import com.example.crewstation.repository.diary.country.DiaryCountryDAO;
import com.example.crewstation.repository.diary.diary.path.DiaryDiaryPathDAO;
import com.example.crewstation.repository.file.FileDAO;
import com.example.crewstation.repository.file.section.FilePostSectionDAO;
import com.example.crewstation.repository.like.LikeDAO;
import com.example.crewstation.repository.post.PostDAO;
import com.example.crewstation.repository.post.file.tag.PostFileTagDAO;
import com.example.crewstation.repository.section.SectionDAO;
import com.example.crewstation.service.s3.S3Service;
import com.example.crewstation.service.tag.TagTransactionService;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.DateUtils;
import com.example.crewstation.util.ScrollCriteria;
import com.example.crewstation.util.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

    private final DiaryDAO diaryDAO;
    private final S3Service s3Service;
    private final SectionDAO sectionDAO;
    private final DiaryTransactionService diaryTransactionService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, Map<String, Long>> countryRedisTemplate;
    private final LikeDAO likeDAO;
    private final TagTransactionService tagTransactionService;
    private static final Map<String, String> ORDER_TYPE_MAP = Map.of("좋아요순", "diary_like_count", "최신순", "post_id");
    private static final Map<String, String> CATEGORY_MAP = Map.of("crew", "not null", "individual", "null");
    private final DiaryCountryDAO diaryCountryDAO;
    private final DiaryDiaryPathDAO diaryDiaryPathDAO;
    private final PostDAO postDAO;
    private final FileDAO fileDAO;
    private final FilePostSectionDAO filePostSectionDAO;
    private final PostFileTagDAO postFileTagDAO;
    private final CrewDiaryDAO crewDiaryDAO;


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
        newSearch.setOrderType(ORDER_TYPE_MAP.getOrDefault(orderType, "post_id"));
        newSearch.setCategory(CATEGORY_MAP.getOrDefault(category, ""));
        Criteria criteria = new Criteria(page, diaryDAO.findCountAllByKeyword(newSearch), 3, 3);
        List<DiaryDTO> diaries = diaryDAO.findAllByKeyword(criteria, newSearch);
        diaries.forEach(diary -> {
            if (diary.getMemberFilePath() != null) {
                diary.setMemberFilePath(s3Service.getPreSignedUrl(diary.getMemberFilePath(), Duration.ofMinutes(5)));
            }
            if (diary.getDiaryFilePath() != null) {
                diary.setDiaryFilePath(s3Service.getPreSignedUrl(diary.getDiaryFilePath(), Duration.ofMinutes(5)));
            }
            if (customUserDetails != null) {
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

    @Override
    public DiaryCriteriaDTO countDiaryImg(Search search, CustomUserDetails customUserDetails) {
        DiaryCriteriaDTO dto = new DiaryCriteriaDTO();
        Search newSearch = new Search();
        int page = search.getPage();
        dto.setSearch(search);

        String category = search.getCategory();
        String orderType = search.getOrderType();

        newSearch.setKeyword(search.getKeyword());
        newSearch.setOrderType(ORDER_TYPE_MAP.getOrDefault(orderType, "post_id"));
        newSearch.setCategory(CATEGORY_MAP.getOrDefault(category, ""));

        Criteria criteria = new Criteria(page, diaryDAO.findCountAllByKeyword(newSearch), 4, 4);

        List<DiaryDTO> diaries = diaryDAO.findAllByKeyword(criteria, newSearch);
        diaries.forEach(diary -> {
            if (diary.getMemberFilePath() != null) {
                diary.setMemberFilePath(s3Service.getPreSignedUrl(diary.getMemberFilePath(), Duration.ofMinutes(5)));
            }
            if (diary.getDiaryFilePath() != null) {
                diary.setDiaryFilePath(s3Service.getPreSignedUrl(diary.getDiaryFilePath(), Duration.ofMinutes(5)));
            }
            if (customUserDetails != null) {
                diary.setUserId(customUserDetails.getId());
                diary.setLikeId(likeDAO.isLikeByPostIdAndMemberId(diary));
            }

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

    @Override
    @Transactional(rollbackFor = Exception.class)
//    @LogStatus
    public void write(PostDiaryDetailTagDTO request) {
        FileDTO fileDTO = new FileDTO();
        FilePostSectionDTO sectionFileDTO = new FilePostSectionDTO();
        Map<String, Long> cached = countryRedisTemplate.opsForValue().get("country::countries");
        PostDTO post = new PostDTO();
        AtomicBoolean check = new AtomicBoolean(false);
        List<Long> countryIds = null;
        List<ImageDTO> images = request.getImages();
        String[] arCountry = null;
        List<DiaryCountryVO> diaryCountryVOs = null;
        List<PostFileTagDTO> postFileTagDTOs = null;


        post.setPostTitle(request.getPostTitle());
        post.setMemberId(request.getMemberId());
        post.setSecret(request.isSecret() ? Secret.PRIVATE : Secret.PUBLIC);
        log.info("{}", cached);
        if (cached == null) {
            cached = tagTransactionService.getCountries();
        }
        arCountry = request.getCountries();
        countryIds = Arrays.stream(arCountry)
                .map(cached::get)
                .collect(Collectors.toList());
        request.setCountryIds(countryIds);
        postDAO.savePost(post);
        request.setPostId(post.getPostId());
        diaryDAO.save(toDiaryVO(post));
        diaryCountryVOs = toDiaryCountryVO(request);
        diaryCountryVOs.forEach(diaryCountryDAO::save);
        diaryDiaryPathDAO.save(toDiaryDiaryPathVO(request));
        if(request.getCrewId() != null) {
            crewDiaryDAO.save(CrewDiaryVO.builder().diaryId(request.getPostId()).crewId(request.getCrewId()).build());
        }
        images.forEach(image -> {
            MultipartFile file = image.getImage();
            log.info(":::::::::::::{}", image.toString());
            if((file == null || Objects.equals(file.getOriginalFilename(), "")) && (image.getPostContent() == null || image.getPostContent().isEmpty())) {
                log.info("다 여기로 오니?");
                return;
            }
            try{
                if (file != null && !Objects.equals(file.getOriginalFilename(), "")) {
                    log.info("어디로 들어오니1");
                    Type type = Type.SUB;
                    if(!check.get()){
                        type = Type.MAIN;
                        check.set(true);
                    }
                    String s3Key = s3Service.uploadPostFile(file,getPath());
                    String originalFileName = file.getOriginalFilename();
                    String extension = "";
                    if (originalFileName != null && originalFileName.contains(".")) {
                        extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                    }
                    fileDTO.setFileName(UUID.randomUUID() + extension);
                    fileDTO.setFilePath(s3Key);
                    fileDTO.setFileSize(String.valueOf(file.getSize()));
                    fileDTO.setFileOriginName(originalFileName);
                    fileDAO.saveFile(fileDTO);
                    image.setPostId(post.getPostId());
                    sectionDAO.saveDiary(image);
                    sectionFileDTO.setFileId(fileDTO.getId());
                    sectionFileDTO.setImageType(type);
                    sectionFileDTO.setPostSectionId(image.getPostSectionId());
                    FilePostSectionVO vo = toSectionFileVO(sectionFileDTO);
                    filePostSectionDAO.save(vo);
                    if(image.getTags() != null){
                        log.info("태그가 없어");
                        image.getTags().forEach((tag)->{
                            tag.setPostSectionFileId(fileDTO.getId());
                            postFileTagDAO.save(toPostFileTagVO(tag));
                        });
                    }

                }else {
                    log.info("어디로 들어오니2");
                    image.setPostId(post.getPostId());
                    sectionDAO.saveDiary(image);
                }
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        });


    }

    @Override
    @LogReturnStatus
    @Transactional(rollbackFor = Exception.class)
    public DiaryDetailDTO getDiary(Long postId,CustomUserDetails customUserDetails) {
        DiaryDetailDTO diaryDetailDTO = new DiaryDetailDTO();
        postDAO.updateReadCount(postId);
        List<CountryDTO> countries = diaryCountryDAO.findCountryByPostId(postId);
        Optional<DiaryDTO> byPostId = diaryDAO.findByPostId(postId);
        List<SectionDTO> sections = sectionDAO.findSectionsByPostId(postId);
        byPostId.ifPresent(diaryDTO ->{
            diaryDTO.setMemberFilePath(s3Service.getPreSignedUrl(diaryDTO.getMemberFilePath(),Duration.ofMinutes(5)));
            diaryDTO.setRelativeDate(DateUtils.toRelativeTime(diaryDTO.getCreatedDatetime()));
            if(customUserDetails != null) {
                diaryDTO.setUserId(Objects.equals(customUserDetails.getId(), diaryDTO.getMemberId()) ?  customUserDetails.getId() : null );
            }else{
                diaryDTO.setUserId(1L);
            }
        });
        diaryDetailDTO.setCountries(countries);
        sections.forEach(section -> {
            log.info("{}",section.getFileId());
            List<PostFileTagDTO> tags = postFileTagDAO.findByFileId(section.getFileId());
            log.info("{}:::::::::::::::::::::::::",tags);
            section.setTags(tags);
            if(section.getFilePath() != null){
                section.setFilePath(s3Service.getPreSignedUrl(section.getFilePath(),Duration.ofMinutes(5)));
            }
            tags.forEach((tag)->{
                tag.setFilePath(s3Service.getPreSignedUrl(tag.getFilePath(),Duration.ofMinutes(5)));
            });

        });
        diaryDetailDTO.setDiary(byPostId.orElseThrow(DiaryNotFoundException::new));
        diaryDetailDTO.setSections(sections);
        return diaryDetailDTO;
    }
    public String getPath() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return today.format(formatter);
    }
}
