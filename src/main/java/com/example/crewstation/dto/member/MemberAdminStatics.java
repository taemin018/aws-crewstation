package com.example.crewstation.dto.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MemberAdminStatics {
    public List<MemberStatics> monthlyJoins;
    public int todayJoin;

}
