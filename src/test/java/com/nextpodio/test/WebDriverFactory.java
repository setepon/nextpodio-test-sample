package com.nextpodio.test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebDriverFactory {
	
	public static WebDriver chromeDriver()
	{
		String filePathChromeDriver = ConfigUtil.INSTANCE
				.getChromeWebDriverPath();

		System.setProperty("webdriver.chrome.driver", filePathChromeDriver);
		
		WebDriver driver= new ChromeDriver();
		driver.manage().timeouts()
		.implicitlyWait(5, TimeUnit.SECONDS);
		
		return driver;
	}
}
