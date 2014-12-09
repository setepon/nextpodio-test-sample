package com.nextpodio.test;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.seleniumhq.selenium.fluent.FluentWebDriver;

public class BasePage extends FluentWebDriver {

	public static final int defaultImplicitlyWait = 20;

	public BasePage(WebDriver delegate) {
		super(delegate);
	}

	public void waitForPageLoad() {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript(
						"return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(delegate, 30);
		wait.until(pageLoadCondition);
	}

	public boolean isAlertPresent() {
		try {
			delegate.switchTo().alert();
			return true;
		}// try
		catch (Exception e) {
			return false;
		}// catch
	}

	public void acceptAlertIfAny() {
		if (isAlertPresent()) {
			delegate.switchTo().alert();
			delegate.switchTo().alert().accept();
			delegate.switchTo().defaultContent();
		}
	}

	public void refreshPage() {
		delegate.navigate().refresh();
	}

	public void removeCSS(WebElement element) {
		String js = "arguments[0].className='';";
		((JavascriptExecutor) delegate).executeScript(js, element);
	}

	public void updateOpacitytoNonZero(WebElement element) {
		String js = "arguments[0].style.opacity='1'; arguments[0].style.filter='alpha(opacity=90)';";
		((JavascriptExecutor) delegate).executeScript(js, element);
	}

	public void removeHiddenStyle(WebElement element) {
		String js = "arguments[0].style.visibility='';";
		((JavascriptExecutor) delegate).executeScript(js, element);
	}

	public void maximizeWindow() {
		((WebDriver) delegate).manage().window().maximize();
	}

}
