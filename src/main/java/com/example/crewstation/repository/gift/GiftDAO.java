package com.example.crewstation.repository.gift;

import com.example.crewstation.dto.gift.GiftDTO;
import com.example.crewstation.mapper.gift.GiftMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GiftDAO {
    private final GiftMapper giftMapper;

    public List<GiftDTO> getMainGifts(int limit) {
        return giftMapper.getGift(limit);
    }
}

