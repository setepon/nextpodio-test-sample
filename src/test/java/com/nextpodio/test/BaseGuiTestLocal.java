package com.nextpodio.test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

public class BaseGuiTestLocal {

	protected WebDriver driver;

	@BeforeClass
	public void init() {
		driver = WebDriverFactory.chromeDriver();

	}

	protected void setImplicitlyWait(long timeout) {
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
	}

	protected void windowMaximize() {
		driver.manage().window().maximize();
	}

	@AfterClass
	public void quit() {
		driver.quit();
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) throws IOException {
		
		if (!result.isSuccess()) {
			if (driver != null) {
				Reporter.setCurrentTestResult(result);
				File f = ((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.FILE);
				File outputDir = new File(result.getTestContext()
						.getOutputDirectory());

				File saved = new File(outputDir.getParent(), result.getName()
						+ ".png");

				FileUtils.copyFile(f, saved);

				Reporter.log("screenshot for " + result.getName() + " url="
						+ driver.getCurrentUrl()
						+ " <img width=\"70%\" height=\"70%\" src=\""
						+ saved.getAbsolutePath() + "\">");
			}
		}
	}

}
