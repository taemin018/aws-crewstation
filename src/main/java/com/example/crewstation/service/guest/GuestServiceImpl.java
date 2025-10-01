package com.example.crewstation.service.guest;

import com.example.crewstation.common.exception.MemberLoginFailException;
import com.example.crewstation.dto.guest.GuestDTO;
import com.example.crewstation.dto.guest.GuestOrderDetailDTO;
import com.example.crewstation.repository.guest.GuestDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GuestServiceImpl implements GuestService {
    private final GuestDAO guestDAO;

    @Override
    public GuestDTO login(GuestDTO guestDTO) {
        return guestDAO.findGuest(guestDTO).orElseThrow(MemberLoginFailException::new);
    }

    @Override
    public GuestOrderDetailDTO getOrderDetail(String guestOrderNumber) {
        return guestDAO.findOrderDetail(guestOrderNumber)
                .orElseThrow(() -> new RuntimeException(" 주문 내역을 찾을 수 없습니다. 주문번호=" + guestOrderNumber));
    }
}
