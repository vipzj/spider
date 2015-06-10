package com.liuy202;

import java.io.IOException;
import java.net.SocketTimeoutException;
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
public class HttpParserImpl implements HttpParser {
	public List<URI> parseLinks(final String uri) {
		final List<URI> uris = new ArrayList<URI>();
		
		// TODO parse HTML content using JSoup
		Document doc;
		String alink="";
		try {
			
			doc = Jsoup.connect(uri).get();
			Elements links = doc.select("a[href]");
			for (Element link : links) {
				if (link.attr("href") != ""
						&& link.attr("href").contains("/")
						&& !link.attr("abs:href").contains("{")
						&& !link.attr("abs:href").contains("LOGIN")
						&& !link.attr("abs:href").contains(".pdf")
						&& !link.attr("abs:href").contains(".image")
						&& !link.attr("abs:href").contains("twitter")
						&& !link.attr("abs:href").contains("facebook")
						&& !link.attr("abs:href").contains("google")
						&& !link.attr("abs:href").contains("linkedin")
						)
				{
					alink = link.attr("abs:href").replace(" ", "");
					uris.add(URI.create(alink));
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			uris.remove(URI.create(alink));
			System.out.println("remove one timeouted link");
		}
		
		return Collections.unmodifiableList(uris);
		
	}
}
