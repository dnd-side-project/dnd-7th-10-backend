package com.io.linkapp.link.controller.api;

import com.io.linkapp.config.security.auth.PrincipalDetails;
import com.io.linkapp.link.controller.predicate.ArticleFormPredicate;
import com.io.linkapp.link.request.ArticleRequest;
import com.io.linkapp.link.request.ArticleTagRequest;
import com.io.linkapp.link.response.ArticleResponse;
import com.io.linkapp.link.response.SuccessResponse;
import com.io.linkapp.link.service.ArticleService;
import com.io.linkapp.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Api(value = "Article", tags = {"Article"})
@RequiredArgsConstructor
@RestController
public class ArticleApi {

    private final ArticleService articleService;

    @ApiOperation("링크 저장")
    @PostMapping("/article")
    public ArticleResponse add(@RequestBody @Valid ArticleRequest articleRequest, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return articleService.add(articleRequest, principalDetails.getUser());
    }

    @ApiOperation(value = "링크 검색", notes = "조회 시 파라미터 없이, 검색 시 쿼리 스트링으로 요청 값이 있는 필드에 대해 검색 조건(or) 처리 됨")
    @GetMapping("/articles")
    public List<ArticleResponse.Tags> getList(String title, String description, String tag, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        ArticleRequest.Search search = ArticleRequest.Search.builder()
                .user(principalDetails.getUser())
                .title(title)
                .description(description)
                .tag(tag)
                .build();

        return articleService.searchArticle(ArticleFormPredicate.searchArticle(search));
    }

    @ApiOperation(value = "아티클 폴더, 태그 변경")
    @PatchMapping("/article")
    public ArticleResponse.Tags modify(@RequestBody ArticleRequest.Modify modifyRequest) {
        return articleService.modify(modifyRequest);
    }

    @ApiOperation(value = "아티클에 태그 제거")
    @DeleteMapping(value = "/article/tag")
    public SuccessResponse removeTag(@RequestBody ArticleRequest.DeleteTag deleteTagRequest){
        return articleService.removeTag(deleteTagRequest);
    }

    @ApiOperation("링크 조회")
    @GetMapping("/article/{articleId}")
    public ArticleResponse.Tags get(@PathVariable("articleId") UUID uuid) {
        return articleService.findById(uuid);
    }

    @ApiOperation("북마크한 링크들만 조회")
    @GetMapping("/articles/mark")
    public List<ArticleResponse.Tags> getListByBookmark(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        User user = principalDetails.getUser();
       return articleService.findByBookmark(user);
    }

    @ApiOperation("링크 삭제")
    @DeleteMapping("/article/{articleId}")
    public SuccessResponse remove(@PathVariable("articleId") UUID uuid) {
        return articleService.remove(uuid);
    }

    @ApiOperation(value = "북마크 등록/해제", notes = "등록 상태에서 요청 시 해제, 해제 상태에서 요청 시 등록")
    @PatchMapping("/article/mark/{articleId}")
    public ArticleResponse bookmark(@PathVariable("articleId") UUID uuid,@AuthenticationPrincipal PrincipalDetails principalDetails){
        UUID userId = principalDetails.getUser().getId();
        return articleService.bookmark(uuid,userId);
    }

    @ApiOperation(value = "아티클에 태그 등록")
    @PostMapping("/article/tag")
    public SuccessResponse tag(@RequestBody ArticleTagRequest articleTag) {
        return articleService.setTagInArticle(articleTag);
    }
}
