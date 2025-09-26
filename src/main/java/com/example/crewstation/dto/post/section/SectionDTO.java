package com.example.crewstation.dto.post.section;

<<<<<<< HEAD:src/main/java/com/example/crewstation/dto/file/section/PostSectionFileVO.java

=======
import com.example.crewstation.common.enumeration.Type;
>>>>>>> ab8478d92921ff1694b2ecdb40d6f9018b49608e:src/main/java/com/example/crewstation/dto/post/section/SectionDTO.java
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode(of="fileId")
<<<<<<< HEAD:src/main/java/com/example/crewstation/dto/file/section/PostSectionFileVO.java
public class PostSectionFileVO{
    private Long id;
    private String fileOriginalName;
    private String filePath;
    private String fileName;
    private Long fileId;
    private Long memberId;
=======
public class SectionDTO {
    private Long postId;
    private Long fileId;
    private Long postSectionId;
    private String postContent;
    private Type imageType;
    private String fileOriginName;
    private String fileName;
    private String filePath;
    private String fileSize;
    private String createdDatetime;
    private String updatedDatetime;
>>>>>>> ab8478d92921ff1694b2ecdb40d6f9018b49608e:src/main/java/com/example/crewstation/dto/post/section/SectionDTO.java
}
