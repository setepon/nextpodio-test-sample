package com.nextpodio.test.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.seleniumhq.selenium.fluent.FluentBy;
import org.seleniumhq.selenium.fluent.FluentWebElement;
import org.seleniumhq.selenium.fluent.FluentWebElements;

import com.nextpodio.test.BasePage;

public class HomePage extends BasePage {

	public HomePage(WebDriver delegate) {
		super(delegate);
		init();
	}

	public FluentWebElement statusTextArea;
	public FluentWebElement submitButton;
	public FluentWebElement searchInput;
	public FluentWebElement spaceSwitcherButtonDiv;

	private void init() {
		url().shouldMatch("https://nextpodio.dk/home");
		statusTextArea = this.textarea(By.id("status_value"));
		statusTextArea.getAttribute("placeholder").shouldBe(
				"Share something. Use @ to mention individuals.");

		submitButton = input(FluentBy.attribute("value", "Share"));
		spaceSwitcherButtonDiv = div(By.className("spaceswitcher-button"));
		div(By.className("spaceswitcher-results-container")).getCssValue(
				"display").shouldBe("none");

	}

	private void postStatusInternal(final String status, String workspace) {
		new WebDriverWait(delegate, defaultImplicitlyWait)
				.until(ExpectedConditions
						.elementToBeClickable(spaceSwitcherButtonDiv
								.getWebElement()));

		spaceSwitcherButtonDiv.click();
		// wait for workspace list to expand
		new WebDriverWait(delegate, defaultImplicitlyWait)
				.until(ExpectedConditions.presenceOfElementLocated(By
						.className("space_listitemcontent")));

		div(By.className("spaceswitcher-results-container")).getCssValue(
				"display").shouldBe("block");
		div(By.cssSelector(".spaceswitcher-button.active")).isDisplayed()
				.shouldBe(true);

		FluentWebElements workspaces = div(
				By.className("space_listitemcontent")).lis();
		for (FluentWebElement ws : workspaces) {
			if (ws.link().getText().toString().equals(workspace)) {
				ws.click();
				break;
			}
		}

		//wait for the commit action
		new WebDriverWait(delegate, defaultImplicitlyWait)
				.until(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver driver) {
						if (ul(By.className("content")).li()
								.getAttribute("data-ref-title").toString()
								.equals(status))
							return true;
						else
							return false;
					}
				});

		//verify the status posted
		ul(By.className("content")).li().div(By.className("title markdown")).getText()
				.shouldBe(status);
		//verify the workspace posted
		ul(By.className("content")).li().link(By.className("space")).getText()
				.shouldBe(workspace);
	}

	private void selectAtTarget(String target) {
		// wait for list show from hidden
		new WebDriverWait(delegate, defaultImplicitlyWait)
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By
						.cssSelector("div.info")));

		FluentWebElements choices = div(By.className("podio-autocompleter"))
				.lis(By.className("item"));
		for (FluentWebElement choice : choices) {

			String strAt = choice.div(By.className("info")).getText()
					.toString().trim();
			//remove suffix when compare with the target 
			if (choice.div(By.className("info")).has()
					.span(By.className("suffix"))) {
				int endIndex = strAt.lastIndexOf(choice
						.div(By.className("info")).span(By.className("suffix"))
						.getText().toString().trim());
				strAt = strAt.substring(0, endIndex).trim();
			}
			if (strAt.equals(target)) {
				choice.click();
				break;
			}
		}
	}

	public void postStatus(String status, String workspace) {
		if (status != null) {
			String[] parts = status.split(" ");
			for (String part : parts) {
				if (part.startsWith("@")) {
					statusTextArea.sendKeys("@");
					part = part.substring(1);

					selectAtTarget(part);

				} else {
					statusTextArea.sendKeys(part);
				}
				statusTextArea.sendKeys(" ");
			}
			postStatusInternal(status, workspace);
		}
	}

	public void postStatusAtTermWith2Words(String status, String workspace) {
		if (status != null) {
			String[] parts = status.split(" ");
			for (int i = 0; i < parts.length; i++) {
				String part = parts[i];
				if (part.startsWith("@")) {
					statusTextArea.sendKeys("@");

					selectAtTarget(part.substring(1) + " " + parts[++i]);

				} else {
					statusTextArea.sendKeys(part);
				}
				statusTextArea.sendKeys(" ");
			}
			postStatusInternal(status, workspace);
		}
	}
}
