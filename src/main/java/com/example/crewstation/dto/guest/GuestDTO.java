package com.example.crewstation.dto.guest;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class GuestDTO {
    private Long id;
    private Long memberId;
    private String guestPhone;
    private String guestOrderNumber;
    private String addressZipCode;
    private String addressDetail;
    private String address;
}
