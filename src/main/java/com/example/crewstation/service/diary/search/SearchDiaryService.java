package com.example.crewstation.service.diary.search;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.dto.diary.DiaryCriteriaDTO;
import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.repository.diary.DiaryDAO;
import com.example.crewstation.repository.like.LikeDAO;
import com.example.crewstation.repository.section.SectionDAO;
import com.example.crewstation.service.diary.DiaryTransactionService;
import com.example.crewstation.service.s3.S3Service;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;



@Service
@Slf4j
@RequiredArgsConstructor
public class SearchDiaryService {




}
