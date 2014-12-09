package com.nextpodio.test;

import java.io.File;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.TestException;

public class BrowserConfigBean {
	private String remoteAddress;
	private List<DesiredCapabilities> configuration;

	public static final String seleniumGridConfig = "browserConfig.json";
	private static BrowserConfigBean testConfig = null;

	private BrowserConfigBean() {

	}

	public static BrowserConfigBean getInstance() {
		synchronized (BrowserConfigBean.class) {
			if (testConfig == null) {
				testConfig = new BrowserConfigBean();
				readConfiguration();
			}

		}
		return testConfig;
	}

	private static void readConfiguration() {
		ObjectMapper mapper = new ObjectMapper();
		File configFile = null;
		try {
			configFile = new File(BrowserConfigBean.class.getClassLoader().getResource(
					seleniumGridConfig).getPath());
			testConfig = mapper.readValue(configFile, BrowserConfigBean.class);
		} catch (Exception e) {
			throw new TestException(
					"Failed to read configuration of selieumn-grid, please check your test settings:\n"
							+ e.getMessage());
		}
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public List<DesiredCapabilities> getConfiguration() {
		return configuration;
	}

	public void setConfiguration(List<DesiredCapabilities> configuration) {
		this.configuration = configuration;
	}
}
