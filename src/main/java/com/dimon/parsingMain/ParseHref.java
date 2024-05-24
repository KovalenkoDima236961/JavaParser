package com.dimon.parsingMain;

import java.util.ArrayList;
import java.util.List;

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

    public List<String> getHrefs() {
        return hrefs;
    }
}
