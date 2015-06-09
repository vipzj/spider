package com.liuy202;

import org.apache.http.HttpClientConnection;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

public class SpiderControllerXML {
	public static void main(String[] args)
	{
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(new ClassPathResource("spring.xml"));

		Spider webCrawler = (Spider) beanFactory.getBean("Spider");
		webCrawler.startCrawl();
	}
}
