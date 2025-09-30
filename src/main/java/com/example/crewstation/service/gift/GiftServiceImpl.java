//package com.example.crewstation.service.gift;
//
//import com.example.crewstation.dto.gift.GiftDTO;
//import com.example.crewstation.mapper.gift.GiftMapper;
//import com.example.crewstation.repository.gift.GiftDAO;
//import lombok.RequiredArgsConstructor;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class GiftServiceImpl implements GiftService {
//
//    private final GiftMapper giftMapper;
//    private final GiftDAO giftDAO;
//
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    @Cacheable(value = "posts", key="'post_' + #id")
//    public List<GiftDTO> getGift(int limit) {
//        giftDAO.getMainGifts(limit);
//
//        GiftDTO giftDTO = giftDAO.getMainGifts(limit);
//
//        return giftDAO.getMainGifts(limit);
//    }
//}
