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

        System.out.println(title);
        System.out.println(title);
        System.out.println(title);
        System.out.println(title);
        System.out.println(title);
        return this;
    }

    public OpenGraph openGraphImage(){
        for (Element openGraphTag : this.openGraphTags) {
            String property = openGraphTag.attr("property");
            if(property.equals("og:image")){
                this.linkImage = openGraphTag.attr("content");
            }
        }
        return this;
    }

    public OpenGraph openGraphDescription(){
        for (Element openGraphTag : this.openGraphTags) {
            String property = openGraphTag.attr("property");
            if(property.equals("og:description")){
                this.linkTitle = openGraphTag.attr("content");
            }
        }
        return this;
    }
}
