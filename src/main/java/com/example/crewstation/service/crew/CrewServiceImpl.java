package com.example.crewstation.service.crew;

import com.example.crewstation.common.exception.PostNotFoundException;
import com.example.crewstation.dto.crew.CrewDTO;
import com.example.crewstation.dto.post.PostDTO;
import com.example.crewstation.repository.crew.CrewDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrewServiceImpl implements CrewService {

    private final CrewDAO crewDAO;
    private final PostDTO postDTO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(value = "posts", key="'post_' + #id")
    public List<CrewDTO> getCrews() {
        CrewDTO crewDTO = new CrewDTO();
           crewDAO.getCrews(crewDTO);

        return crewDAO.getCrews(crewDTO);
    }
}
