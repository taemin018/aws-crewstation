package com.example.crewstation.service.member;

import com.example.crewstation.dto.member.AddressDTO;
import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.repository.member.AddressDAO;
import com.example.crewstation.repository.member.MemberFileDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberDAO memberDAO;
    private final MemberFileDAO memberFileDAO;
    private final AddressDAO addressDAO;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void join(MemberDTO memberDTO, MultipartFile multipartFile, AddressDTO addressDTO) {
        memberDTO.setMemberPassword(passwordEncoder.encode(memberDTO.getMemberPassword()));
        addressDTO = memberDTO.getAddressDTO();
        addressDTO.setMemberId( memberDTO.getId());
        addressDAO.save(toVO(addressDTO));

//        memberFileDAO.save();
//
//        if(multipartFile.getOriginalFilename().equals("")){
//            return;
//        }
//        MemberFileDTO memberFileDTO = new MemberFileDTO();
//        try {
////            String s3Key = s3Service.uploadPostFile(multipartFile, getPath());
//        }

        memberDAO.save(toVO(memberDTO));
    }

    @Override
    public boolean checkEmail(String memberEmail) {
        return memberDAO.checkEmail(memberEmail);
    }

}
