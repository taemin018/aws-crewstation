package com.example.crewstation.service.gift;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.dto.gift.GiftCriteriaDTO;
import com.example.crewstation.dto.gift.GiftDTO;
import com.example.crewstation.repository.gift.GiftDAO;
import com.example.crewstation.service.s3.S3Service;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class GiftServiceImpl implements GiftService {

    private final GiftDAO giftDAO;
    private final S3Service s3Service;
    private final RedisTemplate<String, Object> redisTemplate;
    private final GiftTransactionService giftTransactionService;
    private static final Map<String, String> ORDER_TYPE_MAP = Map.of(
            "좋아요순", "purchase_product_count desc",
            "최신순", "created_datetime desc"
    );
    private static final Map<String, String> CATEGORY_MAP = Map.of(
            "crew", "not null",   // 필요시 XML에 맞춰 사용
            "individual", "null"
    );


    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<GiftDTO> getGift(int limit) {
        List<GiftDTO> gifts = (List<GiftDTO>) redisTemplate.opsForValue().get("gifts");
        if (gifts != null) {
            gifts.forEach(gift -> {
                String filePath = gift.getFilePath();
                String presignedUrl = s3Service.getPreSignedUrl(filePath, Duration.ofMinutes(5));


                log.info("Gift ID={}, 원본 filePath={}, 발급된 presignedUrl={}",
                        gift, filePath, presignedUrl);
                gift.setFilePath(s3Service.getPreSignedUrl(gift.getFilePath(),
                        Duration.ofMinutes(5)));

            });
            redisTemplate.opsForValue().set("gifts", gifts, Duration.ofMinutes(5));
            return gifts;

        }

        return giftTransactionService.getMainGifts(limit);

    }

    @Override
    public GiftCriteriaDTO getGifts(Search search, CustomUserDetails customUserDetails) {
        GiftCriteriaDTO dto = new GiftCriteriaDTO();
        Search newSearch = new Search();
        int page = search.getPage();
        dto.setSearch(search);

        String category = search.getCategory();
        String orderType = search.getOrderType();

        newSearch.setKeyword(search.getKeyword());
        newSearch.setOrderType(ORDER_TYPE_MAP.getOrDefault(orderType, "created_datetime desc"));
        newSearch.setCategory(CATEGORY_MAP.getOrDefault(category, ""));

        int totalCount = giftDAO.countGifts(newSearch);
        Criteria criteria = new Criteria(page, totalCount, 4, 4);

        List<GiftDTO> gifts = giftDAO.findGifts(criteria, newSearch);

        gifts.forEach(gift -> {
            if (gift.getFilePath() != null) {
                gift.setFilePath(s3Service.getPreSignedUrl(gift.getFilePath(), Duration.ofMinutes(5)));
            }
        });

        criteria.setHasMore(gifts.size() > criteria.getRowCount());
        if (criteria.isHasMore()) {
            gifts.remove(gifts.size() - 1);
        }

        dto.setGiftDTOs(gifts);
        dto.setCriteria(criteria);
        dto.setTotalCount(totalCount);

        return dto;
    }

}
