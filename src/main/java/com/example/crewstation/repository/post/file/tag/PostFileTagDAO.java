package com.example.crewstation.repository.post.file.tag;

import com.example.crewstation.domain.post.file.tag.PostFileTagVO;
import com.example.crewstation.mapper.post.file.tag.PostFileTagMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
@Slf4j
public class PostFileTagDAO {
    private final PostFileTagMapper postFileTagMapper;

//    태그 저장
    public void save(PostFileTagVO postFileTagVO) {
        postFileTagMapper.insert(postFileTagVO);
    }
}
