package com.nextpodio.test.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.seleniumhq.selenium.fluent.FluentWebElement;

import bsh.StringUtil;

import com.nextpodio.test.BasePage;

public class LoginPage extends BasePage {

	public FluentWebElement emailInput;
	public FluentWebElement passwordInput;
	public FluentWebElement loginButton;
	public FluentWebElement rememberMeCheckBox;

	private final String errorWrongPassword = "Sorry, wrong password. Have you forgotten your password?";
	private final String errorNonExistUserEmail = "Sorry, we didn't recognise that email address. Are you sure you didn't use a different one?";

	public LoginPage(WebDriver delegate) {
		super(delegate);
		init();
	}

	private void init() {
		emailInput = input(By.id("email"));
		emailInput.isDisplayed().shouldBe(true);

		passwordInput = input(By.id("password"));
		passwordInput.isDisplayed().shouldBe(true);

		loginButton = button(By.name("commit"));
		loginButton.isDisplayed().shouldBe(true);

		rememberMeCheckBox = input(By.id("remember_me"));
		rememberMeCheckBox.isSelected().shouldBe(true);
	}

	public void login(String email, String password) {
		emailInput.clearField();
		passwordInput.clearField();

		emailInput.sendKeys(email);
		passwordInput.sendKeys(password);
		loginButton.click();
	}

	public void verifyWrongPassword(String email) {
		ul(By.className("warning")).getText().shouldBe(errorWrongPassword);
		ul(By.className("warning")).link().getAttribute("href").toString()
				.equals(String.format("/user/recover?email=%s", email));
	}

	public void verifyNonExistUserEmail() {
		ul(By.className("warning")).getText().shouldBe(errorNonExistUserEmail);
	}

}
