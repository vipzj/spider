package com.liuy202;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class Spider {
	
	private HttpClient client;
	private HttpParser parser;
	private Index index;
	
	private Set<URI> alreadyVisited = new HashSet<URI>();
	
	public Spider(HttpClient client, HttpParser parser, Index index)
	{
		this.client = client;
		this.parser = parser;
		this.index = index;
	}
	
	public void startCrawl()
	{
		try {
			crawl(new URI("http://www.unitec.ac.nz/"),20);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public void crawl(URI uri, int depth)
	{		
		//TODO implement web crawler algorithm
		if(depth <= 0){
			System.out.println("Crawling finish!");
			return;
		}
		
		System.out.println(uri);
		StringBuilder messageResponse = new StringBuilder();
		
		if(client.get(uri, messageResponse)){
			List<URI> uris = parser.parseLinks(messageResponse.toString(), uri.getHost());
			
			//index page content in lucene
			index.index(messageResponse.toString(), uri.toString());
			for(URI request: uris){
				if(!alreadyVisited.contains(request)){
					alreadyVisited.add(request);
					System.out.println(request);
					crawl(request, depth-1);
				}
			}
		}
			
	}
}
