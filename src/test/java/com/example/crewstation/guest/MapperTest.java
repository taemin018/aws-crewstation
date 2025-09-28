package com.example.crewstation.guest;

import com.example.crewstation.domain.guest.GuestVO;
import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.dto.payment.status.PaymentStatusDTO;
import com.example.crewstation.mapper.guest.GuestMapper;
import com.example.crewstation.mapper.payment.status.PaymentStatusMapper;
import com.example.crewstation.repository.guest.GuestDAO;
import com.example.crewstation.repository.member.MemberDAO;
import com.example.crewstation.service.payment.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class MapperTest {

    @Autowired
    private GuestMapper guestMapper;
    @Autowired
    private MemberDAO memberDAO;
    @Autowired
    private PaymentService paymentService;

    @Test
    @Transactional
    public void testInsert() {
        MemberDTO memberDTO = new MemberDTO();
        memberDAO.saveGuest(memberDTO);
        PaymentStatusDTO paymentStatusDTO = new PaymentStatusDTO();
        paymentStatusDTO.setMemberId(memberDTO.getId());
        paymentStatusDTO.setGuestOrderNumber("test");
        paymentStatusDTO.setAddress("test");
        paymentStatusDTO.setMemberPhone("test");
        paymentStatusDTO.setAddressDetail("test");
        paymentStatusDTO.setAddressZipCode("test");
        GuestVO vo = paymentService.toVO(paymentStatusDTO);
        guestMapper.insert(vo);
    }
}
