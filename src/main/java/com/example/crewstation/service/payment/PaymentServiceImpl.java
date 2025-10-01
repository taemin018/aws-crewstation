package com.example.crewstation.service.payment;

import com.example.crewstation.aop.aspect.annotation.LogReturnStatus;
import com.example.crewstation.common.exception.PostNotActiveException;
import com.example.crewstation.domain.guest.GuestVO;
import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.dto.payment.status.PaymentStatusDTO;
import com.example.crewstation.repository.alarm.AlarmDAO;
import com.example.crewstation.repository.guest.GuestDAO;
import com.example.crewstation.repository.member.MemberDAO;
import com.example.crewstation.repository.payment.status.PaymentStatusDAO;
import com.example.crewstation.repository.post.PostDAO;
import com.example.crewstation.service.sms.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final PaymentStatusDAO paymentStatusDAO;
    private final AlarmDAO alarmDAO;
    private final PostDAO postDAO;
    private final MemberDAO memberDAO;
    private final GuestDAO guestDAO;
    private final SmsService smsService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogReturnStatus
    public Map<String,Object> requestPayment(PaymentStatusDTO paymentStatusDTO) {
        String code = null;
        String message = null;
        boolean isExist = postDAO.isActivePost(paymentStatusDTO.getPurchaseId());
        log.info("isExist={}", isExist);
        log.info("paymentStatusDTO={}", paymentStatusDTO.toString());

        if(!isExist){
            throw new PostNotActiveException("이미 삭제된 상품입니다.");
        }
        code = smsService.send(paymentStatusDTO.getMemberPhone());
        if(paymentStatusDTO.isGuest()){
//            멤버랑 게스트에 값 넣어주기
            MemberDTO memberDTO = new MemberDTO();
            memberDAO.saveGuest(memberDTO);
            paymentStatusDTO.setMemberId(memberDTO.getId());
            paymentStatusDTO.setGuestOrderNumber(code);
            GuestVO vo = toVO(paymentStatusDTO);
            log.info("vo={}", vo.toString());
            guestDAO.save(vo);
        }else if( paymentStatusDTO.getMemberId() == null){
            message = "비회원입니다.";
            return Map.of("guest" ,true,"message",message);
        }
        paymentStatusDAO.save(paymentStatusDTO);
        alarmDAO.savePaymentAlarm(paymentStatusDTO.getId());
        message = "판매요청 완료되었습니다.";
        return Map.of("message",message);
    }
}
