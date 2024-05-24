package com.dimon.parsingInfo;

import com.dimon.parsingMain.ParseHref;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParsingInfo {
    private List<String> titles;
    private List<String> years;
    private List<String> ageRating;
    private List<String> duration;
    private List<String> genre;
    private List<String> casts;
    private List<String> casteWhichTheyPlay;
    private List<String> storyLines;

    public ParsingInfo() {
        titles = new ArrayList<String>();
        years = new ArrayList<String>();
        ageRating = new ArrayList<String>();
        duration = new ArrayList<String>();
        genre = new ArrayList<>();
        casts = new ArrayList<>();
        casteWhichTheyPlay = new ArrayList<>();
        storyLines = new ArrayList<>();
    }


    public void parse() throws IOException {
        ParseHref parseHref = ParseHref.getParseHref();
        List<String> hrefs = parseHref.getHrefs();
        int counter = 0;
        for(String href : hrefs){
            Document doc = Jsoup.connect("https://m.imdb.com"+href).get();
            Elements text = doc.select(".sc-d8941411-1");
            Elements test = doc.select(".sc-d8941411-2");
            Elements gennre = doc.select(".ipc-chip-list__scroller .ipc-chip");
            Elements cast = doc.select(".sc-bfec09a1-5 .sc-bfec09a1-1");
            Elements castWhichTheyPlay = doc.select(".sc-bfec09a1-5 .sc-bfec09a1-4");
            Elements storyLines1 = doc.select(".ipc-html-content-inner-div");
//            Elements image = doc.select(".ipc-lockup-overlay a[href]");
            String te = text.text();
            String tet = te.substring(16);
            String yearOfFilm = test.text();
            String [] need = yearOfFilm.split(" ");
            String duration2 = String.format(need[2] + " " + need[3]);
            String genre2 = gennre.text();
            String cast2 = cast.text();
            String castWhichTheyPlay2 = castWhichTheyPlay.text();
            String storyLines2 = storyLines1.text();
//            String image2 = image.text();
//            System.out.println(image2);
            System.out.println(storyLines2);


            years.add(need[0]);
            ageRating.add(need[1]);
            duration.add(duration2);
            titles.add(tet);
            genre.add(genre2);
            casts.add(cast2);
            casteWhichTheyPlay.add(castWhichTheyPlay2);
            storyLines.add(storyLines2);

            System.out.println(href+" is already parsed");
            if(counter == 2) {
                break;
            }
            counter++;
        }
    }
    public void writeTitles(){
        System.out.println("Here problem");
        for (String title : titles) {
            System.out.println(title);
        }
    }
}
