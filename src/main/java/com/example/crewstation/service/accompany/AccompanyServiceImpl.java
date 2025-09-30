package com.example.crewstation.service.accompany;

import com.example.crewstation.aop.aspect.annotation.LogReturnStatus;
import com.example.crewstation.dto.accompany.AccompanyDTO;
import com.example.crewstation.repository.accompany.AccompanyDAO;
import com.example.crewstation.service.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccompanyServiceImpl implements AccompanyService {
    private final AccompanyDAO accompanyDAO;
    private final S3Service s3Service;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(value = "accompanies", key="'accompanies_' + #post_id")
    @LogReturnStatus
    public List<AccompanyDTO> getAccompanies(int limit) {
        List<AccompanyDTO> accompanies = accompanyDAO.getAccompanies(limit);
        accompanies.forEach(accompany -> {
            accompany.setFilePath(s3Service.getPreSignedUrl(accompany.getFilePath(),
                    Duration.ofMinutes(5)));
        });
        return accompanies;

    }

}
