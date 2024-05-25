package com.dimon.parsingMain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParseHref {
    private List<String> hrefs;
    private static ParseHref parseHref;
    private ParseHref(){
        hrefs = new ArrayList<String>();
    }

    public static ParseHref getParseHref() {
        if(parseHref == null){
            parseHref = new ParseHref();
            return parseHref;
        }
        return parseHref;
    }

    public void addHref(String href){
        hrefs.add(href);
    }

    public void mapHref(){
        List<String> filteredHrefs = IntStream.range(0, hrefs.size())
                .filter(i -> i % 2 != 1)
                .mapToObj(hrefs::get)
                .toList();

        // Print the result
        filteredHrefs.forEach(System.out::println);
    }
    public List<String> getHrefs() {
        return hrefs;
    }
}
