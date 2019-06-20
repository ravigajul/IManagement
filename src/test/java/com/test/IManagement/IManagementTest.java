package com.test.IManagement;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IManagementTest {

	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
		String currentWorkingDirectory = System.getProperty("user.dir");
		String currentUser = System.getProperty("user.name");

		// Loading Chrome Local Profile
		String pathToChrome = currentWorkingDirectory + "\\chromedriver_win32\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", pathToChrome);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("user-data-dir=C:/Users/" + currentUser + "/AppData/Local/Google/Chrome/User Data");
		options.addArguments("--start-maximized");
		ChromeDriver driver = new ChromeDriver(options);

		// loading the browser
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://broadcomprd.service-now.com");
		driver.findElement(By.id("okta-signin-submit")).click();

		// Click the view/Run
		WebElement viewRun = (new WebDriverWait(driver, 30)).until(ExpectedConditions
				.presenceOfElementLocated(By.cssSelector("#\\31 166104fc611227b00bb1bc49f4845f4 > div > div")));
		viewRun.click();

		// Clicking Atmadeep Report
		driver.switchTo().frame(0);
		WebElement atmadeepReport = (new WebDriverWait(driver, 20)).until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"report-list\"]/tbody/tr[1]/td[4]/a")));
		atmadeepReport.click();

		// Changing the status if No Records found is not displayed
		boolean isdisplayed;
		try {
			isdisplayed = driver.findElement(By.cssSelector("table tbody.list2_body tr.list2_no_records"))
					.isDisplayed();
		} catch (Exception e) {
			System.out.println("element not found");
			isdisplayed = false;
		}

		if (!isdisplayed) {
			WebElement incidentLink = (new WebDriverWait(driver, 10)).until(
					ExpectedConditions.presenceOfElementLocated(By.cssSelector("td.vt a[href^=\"incident.do?\"]")));

			List<WebElement> webElementsList = driver.findElements(By.cssSelector("td.vt a[href^=\"incident.do?\"]"));

			System.out.println(webElementsList.size());
			System.out.println(webElementsList);
			int numberOfIncidents = webElementsList.size();
			for (int i = 0; i < webElementsList.size(); i++) {

				webElementsList.get(0).click();
				WebElement wait = (new WebDriverWait(driver, 10)).until(ExpectedConditions
						.presenceOfElementLocated(By.cssSelector("select[aria-labelledby=\"label.incident.state\"]")));

				Select status = new Select(
						driver.findElement(By.cssSelector("select[aria-labelledby=\"label.incident.state\"]")));
				status.selectByVisibleText("In Progress");

				driver.findElement(By.id("activity-stream-comments-textarea")).sendKeys("We are looking into this");
				driver.findElement(By
						.cssSelector("button#sysverb_update.form_action_button.header.action_context.btn.btn-default"))
						.click();
				numberOfIncidents--;
				if (numberOfIncidents >= 1) {
					incidentLink = (new WebDriverWait(driver, 10)).until(ExpectedConditions
							.presenceOfElementLocated(By.cssSelector("td.vt a[href^=\"incident.do?\"]")));
				}
			}

		} else {
			System.out.println("No items for change");
		}
	}

}
