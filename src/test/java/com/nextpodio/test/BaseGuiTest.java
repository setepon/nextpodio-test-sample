package com.nextpodio.test;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class BaseGuiTest {
	public static ArrayList<Object[]> drivers;

	private static String guiContextRoot = ConfigUtil.INSTANCE.contextRootUrl();

	public static final int defaultImplicitlyWait = 30;

	@BeforeClass
	public void init() {
		WebDriver driver = null;
		drivers = new ArrayList<Object[]>();
		BrowserConfigBean config = BrowserConfigBean.getInstance();
		for (DesiredCapabilities capabilities : config.getConfiguration()) {
			try {

				driver = new RemoteWebDriver(
						new URL(config.getRemoteAddress()), capabilities);
				driver.manage()
						.timeouts()
						.implicitlyWait(defaultImplicitlyWait, TimeUnit.SECONDS);
				
			}

			catch (WebDriverException e) {
				System.err.println(capabilities.getBrowserName()
						+ " failed to  initialize due to " + e.getMessage());
				Reporter.log("Failed to execute tests with "
						+ capabilities.getBrowserName());
			} catch (MalformedURLException e) {
				System.err.println(capabilities.getBrowserName()
						+ " failed to  initialize due to " + e.getMessage());
				Reporter.log("Failed to execute tests with "
						+ capabilities.getBrowserName());
			}
			if (driver != null)
				drivers.add(new Object[] { driver });
		}
		if (drivers.size() == 0)
			throw new TestException(
					"No webDriver is initialized and no test will run. Please check the file @"
							+ BrowserConfigBean.seleniumGridConfig);

	}

	protected void setImplicitlyWait(long timeout) {
		for (Object[] array : drivers)
			for (Object driver : array)
				((WebDriver) driver).manage().timeouts()
						.implicitlyWait(timeout, TimeUnit.SECONDS);
	}

	protected void windowMaximize() {
		for (Object[] array : drivers)
			for (Object driver : array)
				((WebDriver) driver).manage().window().maximize();
	}

	@AfterClass
	public void quit() {
		for (Object[] array : drivers)
			for (Object driver : array)
				((WebDriver) driver).quit();
	}

	

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) throws IOException {
		WebDriver driver = null;
		if (!result.isSuccess()) {
			// RemoteWebDriver driver = (RemoteWebDriver)
			// result.getTestContext().getAttribute("driver");

			for (Object obj : result.getParameters()) {
				if (obj instanceof WebDriver)
					driver = (WebDriver) obj;

			}
			if (driver != null) {
				Reporter.setCurrentTestResult(result);
				File f = ((TakesScreenshot) getSceenShotDriver(driver))
						.getScreenshotAs(OutputType.FILE);
				File outputDir = new File(result.getTestContext()
						.getOutputDirectory());

				File saved;
				if (driver instanceof RemoteWebDriver) {
					saved = new File(outputDir.getParent(), result.getName()
							+ ((RemoteWebDriver) driver).getCapabilities()
									.getBrowserName() + ".png");
				} else {
					saved = new File(outputDir.getParent(), result.getName()
							+ ".png");
				}
				FileUtils.copyFile(f, saved);

				Reporter.log("screenshot for " + result.getName() + " url="
						+ driver.getCurrentUrl()
						+ " <img width=\"70%\" height=\"70%\" src=\""
						+ saved.getAbsolutePath() + "\">");
			}
		}
	}

	private WebDriver getSceenShotDriver(WebDriver driver) {
		if (driver instanceof RemoteWebDriver)
			return new Augmenter().augment(driver);
		else
			return driver;
	}

	@DataProvider(name = "remoteWebDrivers", parallel = true)
	public Iterator<Object[]> drivers() {
		return drivers.iterator();
	}

}