package com.io.linkapp.common;

import com.io.linkapp.exception.CustomGlobalException;
import com.io.linkapp.exception.ErrorCode;
import com.io.linkapp.link.domain.OpenGraph;
import com.io.linkapp.link.domain.OpenGraph.OpenGraphBuilder;
import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class OpenGraphParser {

    public OpenGraph parse(String linkUrl){

        if (!linkUrl.startsWith("https://")) {
            linkUrl = "https://" + linkUrl;
        }

        Connection connect = Jsoup.connect(linkUrl);
        OpenGraph openGraph;

        try {
             openGraph = getOpenGraph(connect);
        } catch (IOException e) {
            throw new CustomGlobalException(ErrorCode.PARSE_ERROR);
        }

        return openGraph;
    }

    public OpenGraph getOpenGraph(Connection connection) throws IOException {
        Elements ogTags = connection.get().select("meta[property^=og:]");

        OpenGraphBuilder openGraphBuilder = OpenGraph.builder();

        for (Element ogTag : ogTags) {
            String property = ogTag.attr("property");
            if (property.equals("og:title")) {
                openGraphBuilder.linkTitle(ogTag.attr("content"));
            } else if (property.equals("og:image")) {
                openGraphBuilder.linkImage(ogTag.attr("content"));
            } else if (property.equals("og:description")) {
                openGraphBuilder.linkDescription(ogTag.attr("content"));
            }
        }

        return openGraphBuilder.build();
    }
}
