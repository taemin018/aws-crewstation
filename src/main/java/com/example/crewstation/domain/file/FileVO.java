package com.example.crewstation.domain.file;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@ToString(callSuper = true)
@Getter
@EqualsAndHashCode(of = "id")
public class FileVO {
    private Long id;
    private String fileOriginalName;
    private String filePath;
    private String fileName;

}
