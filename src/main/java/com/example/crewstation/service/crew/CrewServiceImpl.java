package com.example.crewstation.service.crew;

import com.example.crewstation.aop.aspect.annotation.LogReturnStatus;
import com.example.crewstation.common.exception.PostNotFoundException;
import com.example.crewstation.dto.crew.CrewDTO;
import com.example.crewstation.dto.post.PostDTO;
import com.example.crewstation.repository.crew.CrewDAO;
import com.example.crewstation.service.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrewServiceImpl implements CrewService {

    private final CrewDAO crewDAO;
    private final CrewDTO crewDTO;
    private final S3Service s3Service;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(value = "crews", key="'crews_' + #postid")
    @LogReturnStatus
    public List<CrewDTO> getCrews() {
        List<CrewDTO> crews = crewDAO.getCrews(crewDTO);
        crews.forEach(crew -> {
            crew.setFilePath(s3Service.getPreSignedUrl(crew.getFilePath(),
                    Duration.ofMinutes(5)));

        });
        return crews;






    }
}
