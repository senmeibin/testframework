package com.bpms.core;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

import com.bpms.core.utils.FileUtils;





public class AppContextLoaderListener extends ContextLoaderListener {
	public static String ROOTPATH;
	@Override
    public void contextInitialized(ServletContextEvent event){
    	ServletContext context = event.getServletContext();
    	ROOTPATH = context.getRealPath("/");
    	
    	updateLogXml();
    	super.contextInitialized(event);

    } 
	@Override
    public void contextDestroyed(javax.servlet.ServletContextEvent event){

        super.contextDestroyed(event);
 	
    }	
	
	
    private void updateLogXml() {
		try {    	
	    	File file = new File(ROOTPATH + "/WEB-INF/classes/logback.xml");
	    	String xml = FileUtils.getFileContentFromCharset(file,"UTF-8");
			if(xml.contains("%LOG_PATH%")) {
		    	xml = replace(xml,"%LOG_PATH%/", ROOTPATH + "/WEB-INF/work/");
		    	FileUtils.saveFile2Charset(file, "UTF-8", xml);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}    	
    }	
	private static String replace(String s, String s1, String s2) {
		StringBuilder stringbuffer = new StringBuilder("");
		int i = 0;
		for (int j = 0; (j = s.indexOf(s1, i)) >= 0;) {
			if (j != i)
				stringbuffer.append(s.substring(i, j));
			stringbuffer.append(s2);
			i = j + s1.length();
		}

		stringbuffer.append(s.substring(i));
		return stringbuffer.toString();
	}    
}
