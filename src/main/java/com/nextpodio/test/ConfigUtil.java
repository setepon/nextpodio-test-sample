package com.nextpodio.test;

import java.io.File;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.SystemUtils;

public enum ConfigUtil {
	INSTANCE;
	
	private final String userName = "userName";
	private final String password = "password";

	private final String contextRootUrl = "contextRoot";
	private final String testConfigFile = "test-config.properties";
	
	private final String macChromeDriverEx="chromedriver";
	private final String winChromeDriverEx="chromedriver.exe";
   
    
    public String getChromeWebDriverPath()
    {
    	File chromeDriverDir=new File(ConfigUtil.class.getClassLoader().getResource("webdriver").getPath());
    	if(SystemUtils.IS_OS_MAC)
    	{
    		return chromeDriverDir.getAbsolutePath()+File.separator+macChromeDriverEx;
    	}
    	//windows platform is default one
    	else
    		return chromeDriverDir.getAbsolutePath()+File.separator+winChromeDriverEx;
    	
    }

	
	public String username() {
		return INSTANCE.getTestConfig().getString(userName);
	}

	public String password() {
		return INSTANCE.getTestConfig().getString(password);
	}

	

	public String contextRootUrl() {
		return INSTANCE.getTestConfig().getString(contextRootUrl);
	}

	

	private Configuration getTestConfig() {
		Configuration testConfig;
		try {
			testConfig = new PropertiesConfiguration(testConfigFile);
		} catch (ConfigurationException e) {
			throw new RuntimeException("Failed to get properties due to " + e);
		}
		return testConfig;
	}


}