package com.liuy202;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

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
			URL u = uri.toURL();
			is = u.openStream();
			DataInputStream dis = new DataInputStream(new BufferedInputStream(is));
			String s = "";
			while ((s+=dis.readUTF())!= null) {	
				exist = true;
	         }
			response = new StringBuilder(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			 try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return exist;
	}

}
