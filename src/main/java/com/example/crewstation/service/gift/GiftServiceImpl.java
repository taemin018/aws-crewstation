package com.example.crewstation.service.gift;

import com.example.crewstation.common.exception.PostNotFoundException;
import com.example.crewstation.dto.accompany.AccompanyDTO;
import com.example.crewstation.dto.gift.GiftDTO;
import com.example.crewstation.mapper.gift.GiftMapper;
import com.example.crewstation.repository.gift.GiftDAO;
import com.example.crewstation.service.s3.S3Service;
import com.example.crewstation.util.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GiftServiceImpl implements GiftService {

    private final GiftDAO giftDAO;
    private final S3Service s3Service;
    private final RedisTemplate<String, Object> redisTemplate;
    private final GiftTransactionService giftTransactionService;


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
}
