package com.example.crewstation.domain.file.member;

import com.example.crewstation.audit.Period;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@ToString(callSuper = true)
@Getter
@EqualsAndHashCode(of = "fileId")
public class MemberFileVO extends Period {
    private Long fileId;
    private Long memberId;

}
