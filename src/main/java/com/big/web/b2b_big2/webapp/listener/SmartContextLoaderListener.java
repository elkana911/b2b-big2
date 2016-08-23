package com.big.web.b2b_big2.webapp.listener;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContextEvent;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;

import com.big.web.b2b_big2.util.Utils;

public class SmartContextLoaderListener extends ContextLoaderListener {
    private static final Log log = LogFactory.getLog(SmartContextLoaderListener.class);

    @Override
	public void contextInitialized(ServletContextEvent event) {
		
        //Eric: clean up lucene target dir
        String userDir = Utils.includeTrailingPathDelimiter(System.getProperty("user.dir")) + "target/";

        try {
        	log.info("Cleanup " + userDir);
//			FileUtils.deleteDirectory(new File(userDir));
			FileUtils.cleanDirectory(new File(userDir));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
		super.contextInitialized(event);
		
	}
}
