package com.liuy202;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

@Component
public class HttpClientImpl implements HttpClient
{	
	@Override
	public boolean get(URI uri, StringBuilder response)
	{
		InputStream is = null;
		boolean exist = false;
		//TODO implement org.apache.http.client.HttpClient
		try {
			
			
//			System.out.println("aaa "+uri);
			URL u = uri.toURL();
			
			is = u.openStream();
			
			List<String> lists = IOUtils.readLines(is);
			String s = "";
			if(lists.size()!=0){
				exist = true;
			}
			for(String line:lists){
				response.append(line);
			}
			is.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return exist;
	}

}
