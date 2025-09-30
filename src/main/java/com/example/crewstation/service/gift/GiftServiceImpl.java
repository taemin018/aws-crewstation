package com.example.crewstation.service.gift;

import com.example.crewstation.common.exception.PostNotFoundException;
import com.example.crewstation.dto.gift.GiftDTO;
import com.example.crewstation.mapper.gift.GiftMapper;
import com.example.crewstation.repository.gift.GiftDAO;
import com.example.crewstation.util.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GiftServiceImpl implements GiftService {

    private final GiftDAO giftDAO;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(value = "gifts", key="'gifts_' + #post_id")
    public List<GiftDTO> getGift(int limit) {

        giftDAO.getMainGifts(limit).forEach(giftDTO -> {
            giftDTO.setRelativeDate(DateUtils.toRelativeTime(giftDTO.getRelativeDate().split("\\.")[0]));
        });
        return giftDAO.getMainGifts(limit);
    }
}
