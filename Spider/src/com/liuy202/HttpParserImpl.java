package com.liuy202;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class HttpParserImpl implements HttpParser{
	public List<URI> parseLinks(final String content, final String host)
	{
		final List<URI> urls = new ArrayList <URI> ();
		
		//TODO parse HTML content using JSoup or Jerry HTML Parsers
		Document doc = Jsoup.parse(content);
		Elements links = doc.select("a[href]");
		for(Element link:links){
			urls.add(URI.create(link.attr("abs:href")));
		}
		return Collections.unmodifiableList(urls);
	}
}
