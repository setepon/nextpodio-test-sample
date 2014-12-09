package com.nextpodio.test.gui;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.nextpodio.test.BaseGuiTestLocal;
import com.nextpodio.test.ConfigUtil;
import com.nextpodio.test.page.HomePage;
import com.nextpodio.test.page.LoginPage;

public class TestLogin extends BaseGuiTestLocal{
  @Parameters({ "username", "password" })
  @Test
  public void testLoginWithCorrectCredential(String username, String password) {
	 
	  driver.get(ConfigUtil.INSTANCE.contextRootUrl());
	  
	  LoginPage loginPage=new LoginPage(driver);
	  loginPage.login(username, password);
	  
	  new HomePage(driver){
		  {
			  
		  }
	  };
  }
  
  @Parameters({ "username", "password" })
  @Test
  public void testLoginWithWrongPassword(String username, String password) {
	 
	  driver.get(ConfigUtil.INSTANCE.contextRootUrl());
	  
	  LoginPage loginPage=new LoginPage(driver);
	  loginPage.login(username, password);
	  
	  loginPage.verifyWrongPassword(username);
  }
  
  @Parameters({ "username", "password" })
  @Test
  public void testLoginWithWrongUserName(String username, String password) {
	 
	  driver.get(ConfigUtil.INSTANCE.contextRootUrl());
	  
	  LoginPage loginPage=new LoginPage(driver);
	  loginPage.login(username, password);
	  
	  loginPage.verifyNonExistUserEmail();
  }
  
}
