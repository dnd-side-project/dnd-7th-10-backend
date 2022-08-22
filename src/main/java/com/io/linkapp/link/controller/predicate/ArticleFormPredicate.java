package com.io.linkapp.link.controller.predicate;

import com.io.linkapp.link.domain.QArticle;
import com.io.linkapp.link.request.ArticleRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

@Slf4j
public class ArticleFormPredicate {

    public static Predicate searchArticle(ArticleRequest.Search search) {
        BooleanBuilder builder = new BooleanBuilder();
        QArticle qArticle = QArticle.article;
        builder.and(qArticle.user.eq(search.getUser()));

        String description = search.getDescription();
        String tag = search.getTag();
        String title = search.getTitle();

        boolean isDescriptionEmpty = ObjectUtils.isEmpty(description);
        boolean isTagEmpty = ObjectUtils.isEmpty(tag);
        boolean isTitleEmpty = ObjectUtils.isEmpty(title);

        boolean searchByDescription = !isDescriptionEmpty && isTitleEmpty && isTagEmpty;
        boolean searchByTitle = !isTitleEmpty && isDescriptionEmpty && isTagEmpty;
        boolean searchByTag = !isTagEmpty && isDescriptionEmpty && isTitleEmpty;

        boolean searchByDescriptionAndTitle = !isDescriptionEmpty && !isTitleEmpty && isTagEmpty;
        boolean searchByTitleAndTag = !isTitleEmpty && !isTagEmpty && isDescriptionEmpty;
        boolean searchByDescriptionAndTag = !isDescriptionEmpty && !isTagEmpty && isTitleEmpty;

        boolean searchByAll = !isDescriptionEmpty && !isTagEmpty && !isTitleEmpty;

        if(searchByDescription){
            log.info("Search By Description");
            builder.and(qArticle.openGraph.linkDescription.toUpperCase().contains(description.toUpperCase()));
        }
        else if (searchByTitle) {
            log.info("Search By Title");
            builder.and(qArticle.openGraph.linkTitle.toUpperCase().contains(title.toUpperCase()));
        }
        else if (searchByTag) {
            log.info("Search By Tag");
            builder.and(qArticle.articleTags.any().tag.tagName.toUpperCase().contains(tag.toUpperCase()));
        }
        else if (searchByDescriptionAndTitle) {
            log.info("Search By Description And Title");
            builder.and(qArticle.openGraph.linkTitle.toUpperCase().contains(title.toUpperCase())
                    .or(qArticle.openGraph.linkDescription.toUpperCase().contains(description.toUpperCase())));
        }
        else if (searchByDescriptionAndTag){
            log.info("Search By Description And Tag");
            builder.and(qArticle.openGraph.linkDescription.toUpperCase().contains(description.toUpperCase())
                    .or(qArticle.articleTags.any().tag.tagName.toUpperCase().contains(tag.toUpperCase())));
        }
        else if (searchByTitleAndTag) {
            log.info("Search By Title And Tag");
            builder.and(qArticle.openGraph.linkTitle.toUpperCase().contains(title.toUpperCase())
                    .or(qArticle.articleTags.any().tag.tagName.toUpperCase().contains(tag.toUpperCase())));
        }
        else if (searchByAll) {
            log.info("Search By All");
            builder.and(qArticle.openGraph.linkTitle.toUpperCase().contains(search.getTitle().toUpperCase())
                    .or(qArticle.openGraph.linkDescription.toUpperCase().contains(search.getDescription().toUpperCase()))
                    .or(qArticle.articleTags.any().tag.tagName.toUpperCase().contains(search.getTag().toUpperCase())));
        }

        return builder;
    }
}
