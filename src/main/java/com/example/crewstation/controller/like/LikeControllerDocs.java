package com.example.crewstation.controller.like;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.dto.diary.DiaryCriteriaDTO;
import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.dto.diary.LikedDiaryCriteriaDTO;
import com.example.crewstation.dto.diary.ReplyDiaryCriteriaDTO;
import com.example.crewstation.dto.like.LikeDTO;
import com.example.crewstation.util.Search;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

// http://localhost:10000/swagger-ui/index.html
@Tag(name = "Like", description = "Like API")
public interface LikeControllerDocs {
    @Operation(summary = "다이어리 조회",
    description = "모든 다이어리 조회 및 이미지 개수랑 좋아요 여부",
    parameters = {
            @Parameter(name = "likeDTO",description = "다이어리의 게시글 이이디를 가져오고 회원 정보를 넣어준다."),
            @Parameter(name="customUserDetails",description = "로그인한 회원의 정보가 들어온다.")
    })
    public ResponseEntity<String> addLike(@RequestBody LikeDTO likeDTO, @AuthenticationPrincipal CustomUserDetails userDetails);

    @Operation(summary = "다이어리 설정 변경",
            description = "다이어리 공개, 비공개 설정 변경",
            parameters = {
                    @Parameter(name = "postId",description = "다이어리의 게시글 아이디가 들어온다"),
                    @Parameter(name="customUserDetails",description = "로그인한 회원의 정보가 들어온다.")
            })
    public ResponseEntity<String> deleteLike(@PathVariable Long postId,@AuthenticationPrincipal CustomUserDetails userDetails);
}
