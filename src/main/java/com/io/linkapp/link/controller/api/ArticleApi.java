package com.io.linkapp.link.controller.api;

import com.io.linkapp.link.domain.Article;
import com.io.linkapp.link.request.ArticleRequest;
import com.io.linkapp.link.response.ArticleResponse;
import com.io.linkapp.link.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Article", tags = {"Article"})
@RequiredArgsConstructor
@RestController
public class ArticleApi {

    private final ArticleService articleService;

    @ApiOperation("링크 저장")
    @PostMapping("/article")
    public void add(@RequestBody @Valid ArticleRequest articleRequest) {
        articleService.add(articleRequest);
    }

    @ApiOperation("링크 전체 조회")
    @GetMapping("/articles")
    public List<ArticleResponse> getList() {
        return articleService.getList();
    }

    @ApiOperation("링크 조회")
    @GetMapping("/article/{id}")
    public ArticleResponse get(@PathVariable("id") UUID uuid) {
        return articleService.findById(uuid);
    }

    @ApiOperation("링크 삭제")
    @DeleteMapping("/article/{id}")
    public void remove(@PathVariable("id") UUID uuid) {
        articleService.remove(uuid);
    }
}
