package com.example.crewstation.service.accompany;

import com.example.crewstation.aop.aspect.annotation.LogReturnStatus;
import com.example.crewstation.dto.accompany.AccompanyDTO;
import com.example.crewstation.service.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccompanyServiceImpl implements AccompanyService {
    private final S3Service s3Service;
    private final AccompanyTransactionService accompanyTransactionService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogReturnStatus
    public List<AccompanyDTO> getAccompanies(int limit) {
        List<AccompanyDTO> accompanies = (List<AccompanyDTO>) redisTemplate.opsForValue().get("accompanies");
        if (accompanies != null) {
            accompanies.forEach(accompany -> {
                String filePath = accompany.getFilePath();
                String presignedUrl = s3Service.getPreSignedUrl(filePath, Duration.ofMinutes(5));

                log.info("Accompany ID={}, 원본 filePath={}, 발급된 presignedUrl={}",
                        accompany, filePath, presignedUrl);
                accompany.setFilePath(s3Service.getPreSignedUrl(accompany.getFilePath(),
                        Duration.ofMinutes(5)));
            });
            return accompanies;

        }
        return accompanyTransactionService.getAccompanies(limit);

    }

}
