//package com.example.crewstation.service.member;
//
//import com.example.crewstation.dto.file.member.MemberFileDTO;
//import com.example.crewstation.dto.member.AddressDTO;
//import com.example.crewstation.dto.member.MemberDTO;
//import com.example.crewstation.repository.member.AddressDAO;
//import com.example.crewstation.repository.member.MemberDAO;
//import com.example.crewstation.repository.member.MemberFileDAO;
//import com.example.crewstation.service.s3.S3Service;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class MemberServiceImpl implements MemberService {
//    private final MemberDAO memberDAO;
//    private final MemberFileDAO memberFileDAO;
//    private final AddressDAO addressDAO;
//    private final PasswordEncoder passwordEncoder;
//    private final S3Service s3Service;
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void join(MemberDTO memberDTO, MultipartFile multipartFile) {
//        memberDTO.setMemberPassword(passwordEncoder.encode(memberDTO.getMemberPassword()));
//
//        AddressDTO addressDTO = new AddressDTO();
//        addressDTO.setMemberId( memberDTO.getId());
//
//        addressDAO.save(toVO(addressDTO));
//
//        if(multipartFile.getOriginalFilename().equals("")){
//            return;
//        }
//        MemberFileDTO memberFileDTO = new MemberFileDTO();
//        try {
//            String s3Key = s3Service.uploadPostFile(multipartFile, getPath());
//
//            memberFileDTO.setMemberId(memberDTO.getId());
//            memberFileDTO.setFileOriginalName(multipartFile.getOriginalFilename());
//            memberFileDTO.setFilePath(s3Key);
//            memberFileDTO.setFileSize(memberFileDTO.getFileSize());
//
//            memberFileDAO.save(memberFileDTO);
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        memberDAO.save(toVO(memberDTO));
//    }
//
//    @Override
//    public boolean checkEmail(String memberEmail) {
//        return memberDAO.checkEmail(memberEmail);
//    }
//
//    public String getPath() {
//        LocalDate today = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//        return today.format(formatter);
//    }
//
//}
