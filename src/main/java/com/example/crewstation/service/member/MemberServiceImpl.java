package com.example.crewstation.service.member;

import com.example.crewstation.common.exception.MemberLoginFailException;
import com.example.crewstation.common.exception.MemberNotFoundException;
import com.example.crewstation.domain.file.FileVO;
import com.example.crewstation.domain.member.MemberVO;
import com.example.crewstation.dto.file.FileDTO;
import com.example.crewstation.dto.file.member.MemberFileDTO;
import com.example.crewstation.dto.member.AddressDTO;
import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.dto.member.MemberProfileDTO;
import com.example.crewstation.repository.file.FileDAO;
import com.example.crewstation.repository.member.AddressDAO;
import com.example.crewstation.repository.member.MemberDAO;
import com.example.crewstation.repository.member.MemberFileDAO;
import com.example.crewstation.service.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

        MemberVO vo = toVO(memberDTO);
        memberDAO.save(vo);

        Long memberId = vo.getId();

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

            String originalFileName = multipartFile.getOriginalFilename();
            String extension = "";

            if(originalFileName != null && originalFileName.contains(".")){
                extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }


            fileDTO.setFileOriginName(multipartFile.getOriginalFilename());
            fileDTO.setFilePath(s3Key);
            fileDTO.setFileSize(String.valueOf(multipartFile.getSize()));
            fileDTO.setFileName(UUID.randomUUID() + extension);

            FileVO filevo = toVO(fileDTO);
            fileDAO.save(filevo);

            Long fileId = filevo.getId();

            memberFileDTO.setMemberId(memberId);
            memberFileDTO.setFileId(fileId);

            memberFileDAO.save(toVO(memberFileDTO));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public boolean checkEmail(String memberEmail) {
        return memberDAO.checkEmail(memberEmail);
    }

    @Override
    public MemberDTO login(MemberDTO memberDTO) {
        log.info("memberDTO: {}", memberDTO);
        return memberDAO.findForLogin(memberDTO).orElseThrow(MemberLoginFailException::new);
    }

    public String getPath() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return today.format(formatter);
    }

    @Override
    @Cacheable(value="member", key="#memberEmail")
    public MemberDTO getMember(String memberEmail, String provider) {
        return (provider == null ? memberDAO.findByMemberEmail(memberEmail)
                : memberDAO.findBySnsEmail(memberEmail)).orElseThrow(MemberNotFoundException::new);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void joinSns(MemberDTO memberDTO, MultipartFile multipartFile) {
        MemberVO vo = toVO(memberDTO);
        memberDAO.saveSns(vo);

        Long memberId = vo.getId();

        AddressDTO addressDTO = new AddressDTO();


        log.info("memberId: {}", memberId);

        addressDTO.setMemberId(memberId);
        addressDTO.setAddressDetail(memberDTO.getAddressDTO().getAddressDetail());
        addressDTO.setAddress(memberDTO.getAddressDTO().getAddress());
        addressDTO.setAddressZipCode(memberDTO.getAddressDTO().getAddressZipCode());

        addressDAO.save(toVO(addressDTO));

        if (multipartFile.getOriginalFilename().equals("")) {
            return;
        }
        FileDTO fileDTO = new FileDTO();
        MemberFileDTO memberFileDTO = new MemberFileDTO();
        try {
            String s3Key = s3Service.uploadPostFile(multipartFile, getPath());

            String originalFileName = multipartFile.getOriginalFilename();
            String extension = "";

            if (originalFileName != null && originalFileName.contains(".")) {
                extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }


            fileDTO.setFileOriginName(multipartFile.getOriginalFilename());
            fileDTO.setFilePath(s3Key);
            fileDTO.setFileSize(String.valueOf(multipartFile.getSize()));
            fileDTO.setFileName(UUID.randomUUID() + extension);

            FileVO filevo = toVO(fileDTO);
            fileDAO.save(filevo);

            Long fileId = filevo.getId();

            memberFileDTO.setMemberId(memberId);
            memberFileDTO.setFileId(fileId);

            memberFileDAO.save(toVO(memberFileDTO));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        memberDAO.saveSns(toVO(memberDTO));

    }
    @Override
    public Optional<MemberProfileDTO> getMemberProfile (Long memberId) {
        return memberDAO.selectProfileById(memberId);
    }

    @Override
    public void resetPassword(String memberEmail, String memberPassword) {
        String newPassword = passwordEncoder.encode(memberPassword);

        memberDAO.updatePassword(memberEmail, newPassword);
    }

    @Override
    public List<MemberDTO> searchMember(String search) {
        List<MemberDTO> searchMember = memberDAO.findSearchMember(search);
        searchMember.forEach(member->{
            if(member.getFilePath() != null) {
                member.setFilePath(s3Service.getPreSignedUrl(member.getFilePath(), Duration.ofMinutes(5)));
            }

        });
//        return memberDAO.findSearchMember(search);
        return searchMember;
    }

    public Optional<MemberProfileDTO> getMember(Long memberId) {
        return Optional.empty();
    }

}
