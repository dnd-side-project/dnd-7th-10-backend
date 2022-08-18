package com.io.linkapp.link.domain;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Embeddable
@NoArgsConstructor
@Getter
public class OpenGraph {
    private String linkTitle;
    private String linkDescription;
    private String linkImage;

    @JsonIgnore
    @Transient
    private Elements openGraphTags;

    @Builder
    public OpenGraph(String linkTitle, String linkDescription, String linkImage) {
        this.linkTitle = linkTitle;
        this.linkDescription = linkDescription;
        this.linkImage = linkImage;
    }

    public OpenGraph openGraphTitle(Elements openGraphTags, String title){
        this.openGraphTags = openGraphTags;

        for (Element openGraphTag : openGraphTags) {
            String property = openGraphTag.attr("property");
            if(property.equals("og:title")){
                this.linkTitle = openGraphTag.attr("content");
            }
        }

        if (this.linkTitle == null) {
            this.linkTitle = title;
        }
        return this;
    }

    public OpenGraph openGraphImage(){
        for (Element openGraphTag : this.openGraphTags) {
            String property = openGraphTag.attr("property");
            if(property.equals("og:image")){
                this.linkImage = openGraphTag.attr("content");
            }
        }

        if (this.linkImage == null) {
            this.linkImage = "";
        }
        return this;
    }

    public OpenGraph openGraphDescription(Elements descriptionTag){
        for (Element openGraphTag : this.openGraphTags) {
            String property = openGraphTag.attr("property");
            if(property.equals("og:description")){
                this.linkDescription = openGraphTag.attr("content");
            }
        }

        if (this.linkDescription == null) {
            this.linkDescription = descriptionTag.attr("content");
        }
        return this;
    }
}
