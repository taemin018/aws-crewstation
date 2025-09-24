package com.example.crewstation.dto.crew;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode(of="id")
public class CrewDTO {
    private Long id;
    private String crewName;
    private String crewDescription;
    private int crewMemberCount;
    private String createdDatetime;
    private String updatedDatetime;
}
