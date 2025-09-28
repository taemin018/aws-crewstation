package com.example.crewstation.service.gift;

import com.example.crewstation.dto.gift.GiftDTO;
import com.example.crewstation.mapper.gift.GiftMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GiftServiceImpl implements GiftService {

    private final GiftMapper giftMapper;


    @Override
    public List<GiftDTO> getGift(int limit) {

        return giftMapper.getGift(limit);
    }
}
