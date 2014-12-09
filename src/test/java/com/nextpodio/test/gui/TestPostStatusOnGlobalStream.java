package com.nextpodio.test.gui;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.nextpodio.test.BaseGuiTestLocal;
import com.nextpodio.test.ConfigUtil;
import com.nextpodio.test.WebDriverFactory;
import com.nextpodio.test.page.HomePage;
import com.nextpodio.test.page.LoginPage;

public class TestPostStatusOnGlobalStream extends BaseGuiTestLocal {
	HomePage homePage;

	@BeforeClass
	public void init() {
		super.init();
		driver.get(ConfigUtil.INSTANCE.contextRootUrl());

		new LoginPage(driver) {
			{
				this.login(ConfigUtil.INSTANCE.username(),
						ConfigUtil.INSTANCE.password());
			}
		};

		homePage = new HomePage(driver);

	}

	@Parameters({ "status", "workspace" })
	@Test
	public void testPostStatus(String status, String workspace) {
	
		homePage.postStatus(status, workspace);
	}

	@Parameters({ "status", "workspace" })
	@Test
	public void testPostStatusAtTermWith2Words(String status, String workspace) {
		
		homePage.postStatusAtTermWith2Words(status, workspace);
	}
}
