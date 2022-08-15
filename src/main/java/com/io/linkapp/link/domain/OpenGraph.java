package com.io.linkapp.link.domain;

import javax.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public class OpenGraph {
    private String linkTitle;
    private String linkDescription;
    private String linkImage;

    @Builder
    public OpenGraph(String linkTitle, String linkDescription, String linkImage) {
        this.linkTitle = linkTitle;
        this.linkDescription = linkDescription;
        this.linkImage = linkImage;
    }
}
