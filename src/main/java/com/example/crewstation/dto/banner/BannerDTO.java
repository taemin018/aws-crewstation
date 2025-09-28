package com.example.crewstation.dto.banner;

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
public class BannerDTO {
    private Long id;
    private int bannerOrder;
    private String createdDatetime;
    private String updatedDatetime;

}
