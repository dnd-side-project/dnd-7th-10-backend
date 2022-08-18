package com.io.linkapp.common;

import com.io.linkapp.exception.CustomGlobalException;
import com.io.linkapp.exception.ErrorCode;
import com.io.linkapp.link.domain.OpenGraph;
import com.io.linkapp.link.domain.OpenGraph.OpenGraphBuilder;
import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
        Document doc = Jsoup.parse(linkUrl);
        OpenGraph openGraph;

        try {
             openGraph = getOpenGraph(connect, doc);
        } catch (IOException e) {
            throw new CustomGlobalException(ErrorCode.PARSE_ERROR);
        }

        return openGraph;
    }

    public OpenGraph getOpenGraph(Connection connection, Document doc) throws IOException {
        Elements openGraphTags = connection.get().select("meta[property^=og:]");
        //TODO 타이틀 핸들
        connection.get().title();
        String title = doc.title();
        OpenGraph openGraph = new OpenGraph();

        System.out.println(title);
        System.out.println(title);
        System.out.println(title);
        System.out.println(title);
        System.out.println(title);
        System.out.println(title);
        System.out.println(title);

        openGraph = openGraph.openGraphTitle(openGraphTags, title)
                .openGraphImage()
                .openGraphDescription();

        return openGraph;
}
}
