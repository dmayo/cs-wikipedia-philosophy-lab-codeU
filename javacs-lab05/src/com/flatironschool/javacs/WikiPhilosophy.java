package com.flatironschool.javacs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import org.jsoup.select.Elements;

public class WikiPhilosophy {
	
	final static WikiFetcher wf = new WikiFetcher();
	
	/**
	 * Tests a conjecture about Wikipedia and Philosophy.
	 * 
	 * https://en.wikipedia.org/wiki/Wikipedia:Getting_to_Philosophy
	 * 
	 * 1. Clicking on the first non-parenthesized, non-italicized link
     * 2. Ignoring external links, links to the current page, or red links
     * 3. Stopping when reaching "Philosophy", a page with no links or a page
     *    that does not exist, or when a loop occurs
	 * 
	 * @param args
	 * @throws IOException
	 */

	private static Queue<String> queue = new LinkedList<String>();

	public static void main(String[] args) throws IOException {
		
        // some example code to get you started

		String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
		String last = "https://en.wikipedia.org/wiki/Philosophy";

		//Element firstPara = paragraphs.get(0);
		queue.offer(url);
		boolean found=false;
		boolean validLink=false;
		while(queue.size()>0){
			if(found){
				System.out.println(queue.poll());
				break;
			}
			Elements paragraphs = wf.fetchWikipedia(url);
			validLink=false;
			for (Element paragraph: paragraphs){
				Elements links = paragraph.select("a[href]");
				if(links.size()>0){
					for (Element link: links){
						String linkURL = link.attr("href");
						if(linkURL.equals(found)){
							found=true;
							break;
						}
						//valid link if links to wikipedia page
						if (linkURL.startsWith("/wiki/")) {
							String fullURL = "https://en.wikipedia.org" + linkURL;
							System.out.println(fullURL);
							queue.offer(fullURL);
							validLink=true;
							break;
						}
					}
				}
				if(validLink||found){
					break;
				}
			}
		}

		


        // the following throws an exception so the test fails
        // until you update the code
        //String msg = "Complete this lab by adding your code and removing this statement.";
        //throw new UnsupportedOperationException(msg);
	}
}
