package com.dimon;

import com.dimon.parsingInfo.ParsingInfo;
import com.dimon.parsingMain.ParseHref;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://m.imdb.com/chart/top/").get();
        String title = doc.title();
        System.out.println("title: " + title);

        ParseHref parseHref = ParseHref.getParseHref();

        Elements links = doc.select(".ipc-title-link-wrapper");
        for(Element link : links){
            parseHref.addHref(link.attr("href"));
        }


        ParsingInfo info = new ParsingInfo();
        info.parse();
        info.writeTitles();

    }
}