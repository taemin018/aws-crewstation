package com.example.crewstation.domain.file;

import com.example.crewstation.audit.Period;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@ToString(callSuper = true)
@Getter
@EqualsAndHashCode(of = "id")
public class FileVO extends Period {
    private Long id;
    private String fileOriginalName;
    private String filePath;
    private String fileName;
    private String fileSize;

}
