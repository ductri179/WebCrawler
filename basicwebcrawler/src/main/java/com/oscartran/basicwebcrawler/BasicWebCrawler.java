package com.oscartran.basicwebcrawler;

import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Hello world!
 *
 */
public class BasicWebCrawler 
{

    private static final int MAX_DEPTH = 2;
    private HashSet<String> links;

    public BasicWebCrawler() {
        links = new HashSet<String>();
    }

    public void getPageLinks(String URL, int depth) {
        if (!links.contains(URL) && (depth < MAX_DEPTH)) {
            System.out.println(">> Depth: " + depth + " [" + URL + "]");
            try {
                links.add(URL);
                Document doc = Jsoup.connect(URL).get();
                Elements linksOnPage = doc.select("a[href]");

                depth++;
                for (Element page : linksOnPage) {
                    getPageLinks(page.attr("abs:href"), depth);
                }

            } catch (Exception e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }

    public static void main( String[] args )
    {
        new BasicWebCrawler().getPageLinks("http://www.mkyong.com/", 0);
    }
}
