package com.liuy202;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@EnableAutoConfiguration
public class SpiderController {

	
	@Bean
	public Spider getSpider()
	{
		return new Spider(getHttpClient(), getHttpParser(),getIndex());
	}
	
	@Bean
	public Index getIndex() {
		// TODO Auto-generated method stub
		return new IndexImpl();
	}

	@Bean
	public HttpClient getHttpClient()
	{
		return new HttpClientImpl();
	}
	
	@Bean
	public HttpParser getHttpParser()
	{
		return new HttpParserImpl();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/home")
	public String home(){
		return "home";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/crawling")
	public String crawling(){
		Spider spider = getSpider();
		spider.startCrawl();
		return "search";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/search")
	@ResponseBody
	public String search(String searchContent){
		Index index = new IndexImpl();
		String result =index.search(searchContent);
		return "<p>"+result+"</p>";
	}
	public static void main(String[] args)
	{
		SpringApplication.run(SpiderController.class, args);
		
	}
}
