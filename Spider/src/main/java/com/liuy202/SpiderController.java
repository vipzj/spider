package com.liuy202;

import org.apache.commons.io.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Controller
@EnableAutoConfiguration
@Configuration
@EnableWebMvc
public class SpiderController extends WebMvcConfigurerAdapter {

	Spider spider;
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();



		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

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
		spider = getSpider();
		spider.startCrawl();
		return "redirect:/search";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/search")
	public String search(){
		 
		return "search";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/searchInLucene")
	@ResponseBody
	public String searchInLucene(String searchContent){
		Index index = getIndex();
		String result =index.search(searchContent);
		return result;
	}
	public static void main(String[] args)
	{
		SpringApplication.run(SpiderController.class, args);
		
	}
}
