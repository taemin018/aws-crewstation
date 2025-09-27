package com.example.crewstation.service.member;

import com.example.crewstation.dto.file.FileDTO;
import com.example.crewstation.dto.file.member.MemberFileDTO;
import com.example.crewstation.dto.member.AddressDTO;
import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.repository.file.FileDAO;
import com.example.crewstation.repository.member.AddressDAO;
import com.example.crewstation.repository.member.MemberDAO;
import com.example.crewstation.repository.member.MemberFileDAO;
import com.example.crewstation.service.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberDAO memberDAO;
    private final MemberFileDAO memberFileDAO;
    private final AddressDAO addressDAO;
    private final FileDAO fileDAO;
    private final PasswordEncoder passwordEncoder;
    private final S3Service s3Service;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void join(MemberDTO memberDTO, MultipartFile multipartFile) {
        memberDTO.setMemberPassword(passwordEncoder.encode(memberDTO.getMemberPassword()));

        memberDAO.save(toVO(memberDTO));

        Long memberId = memberDTO.getId();
        AddressDTO addressDTO = new AddressDTO();


        log.info("memberId: {}",memberId);

        addressDTO.setMemberId(memberId);
        addressDTO.setAddressDetail(memberDTO.getAddressDTO().getAddressDetail());
        addressDTO.setAddress(memberDTO.getAddressDTO().getAddress());
        addressDTO.setAddressZipCode(memberDTO.getAddressDTO().getAddressZipCode());

        addressDAO.save(toVO(addressDTO));

        if(multipartFile.getOriginalFilename().equals("")){
            return;
        }
        FileDTO fileDTO = new FileDTO();
        MemberFileDTO memberFileDTO = new MemberFileDTO();
        try {
            String s3Key = s3Service.uploadPostFile(multipartFile, getPath());


            fileDTO.setFileOriginName(multipartFile.getOriginalFilename());
            fileDTO.setFilePath(s3Key);
            fileDTO.setFileSize(String.valueOf(multipartFile.getSize()));

            fileDAO.save(toVO(fileDTO));

            memberFileDTO.setMemberId(memberDTO.getId());
            memberFileDTO.setFileId(fileDTO.getId());

            memberFileDAO.save(toVO(memberFileDTO));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public boolean checkEmail(String memberEmail) {
        return memberDAO.checkEmail(memberEmail);
    }

    public String getPath() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return today.format(formatter);
    }

}
