package com.io.linkapp.common;

import com.io.linkapp.exception.CustomGlobalException;
import com.io.linkapp.exception.ErrorCode;
import com.io.linkapp.link.domain.OpenGraph;
import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class OpenGraphParser {

    public OpenGraph parse(String linkUrl){

        if (!linkUrl.startsWith("https://") || !linkUrl.startsWith("http://")) {
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
        Elements openGraphTags = connection.get().select("meta[property^=og:]");
        String title = connection.get().title();
        Elements descriptionTag = connection.get().select("meta[name=description]");

        OpenGraph openGraph = new OpenGraph();

        openGraph = openGraph.openGraphTitle(openGraphTags, title)
                .openGraphImage()
                .openGraphDescription(descriptionTag);

        return openGraph;
}
}
