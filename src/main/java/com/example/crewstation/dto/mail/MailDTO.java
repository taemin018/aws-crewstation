package com.example.crewstation.dto.mail;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class MailDTO {
    private String email;
    private String content;

}
