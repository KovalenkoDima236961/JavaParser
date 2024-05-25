package com.dimon.parsingInfo;

import com.dimon.parsingMain.ParseHref;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
        titles = new CopyOnWriteArrayList<>();
        years = new CopyOnWriteArrayList<>();
        ageRating = new CopyOnWriteArrayList<>();
        duration = new CopyOnWriteArrayList<>();
        genre = new CopyOnWriteArrayList<>();
        casts = new CopyOnWriteArrayList<>();
        casteWhichTheyPlay = new CopyOnWriteArrayList<>();
        storyLines = new CopyOnWriteArrayList<>();
    }


    public void parse() throws IOException {
        ParseHref parseHref = ParseHref.getParseHref();
        List<String> hrefs = parseHref.getHrefs();
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (String href : hrefs) {
            executor.submit(new ParseTask(href));
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.MINUTES)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("Executor did not terminate");
                }
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("All tasks finished");
    }

    private class ParseTask implements Runnable {
        private String href;

        public ParseTask(String href) {
            this.href = href;
        }

        @Override
        public void run() {
            try {
                Document doc = Jsoup.connect("https://m.imdb.com" + href).get();
                Elements text = doc.select(".sc-d8941411-1");
                Elements test = doc.select(".sc-d8941411-2");
                Elements gennre = doc.select(".ipc-chip-list__scroller .ipc-chip");
                Elements cast = doc.select(".sc-bfec09a1-5 .sc-bfec09a1-1");
                Elements castWhichTheyPlay = doc.select(".sc-bfec09a1-5 .sc-bfec09a1-4");
                Elements storyLines1 = doc.select(".ipc-html-content-inner-div");
                String te = text.text();
                String tet = te.substring(16);
                String yearOfFilm = test.text();
                String[] need = yearOfFilm.split(" ");
                String duration2 = String.format(need[2] + " " + need[3]);
                String genre2 = gennre.text();
                String cast2 = cast.text();
                String castWhichTheyPlay2 = castWhichTheyPlay.text();
                String storyLines2 = storyLines1.text();

                synchronized (ParsingInfo.this) {
                    years.add(need[0]);
                    ageRating.add(need[1]);
                    duration.add(duration2);
                    titles.add(tet);
                    genre.add(genre2);
                    casts.add(cast2);
                    casteWhichTheyPlay.add(castWhichTheyPlay2);
                    storyLines.add(storyLines2);
                }

                System.out.println(href + " is already parsed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeTitles(){
        System.out.println("Here problem");
        for (String title : titles) {
            System.out.println(title);
        }
    }
    // Method to write parsed data to a CSV file
    public void writeToCsv(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Write CSV header
            writer.println("Title,Year,Age Rating,Duration,Genre,Cast,Cast Roles,Story Line");

            // Write each record
            for (int i = 0; i < titles.size(); i++) {
                writer.printf("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"%n",
                        titles.get(i),
                        years.get(i),
                        ageRating.get(i),
                        duration.get(i),
                        genre.get(i),
                        casts.get(i),
                        casteWhichTheyPlay.get(i),
                        storyLines.get(i));
            }

            System.out.println("Data successfully written to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}