package com.shubhangi;

import static org.testng.Assert.assertEquals;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginForm {

	WebDriver driver = null;

	@BeforeTest
	public void openBrowser() {

		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();

	}

	@Test(dataProvider = "testdata")
	public void form(String firstName, String lastName, String email, String gender, String mobile, String subject,
			String hobby, String imagePath, String address, String state, String city, String birthdate)
			throws InterruptedException, AWTException {
		driver.get("http://demoqa.com/automation-practice-form");

		driver.manage().window().maximize();

		String actualTitle = driver.getTitle();
		String expectedTitle = "ToolsQA";
		assertEquals(actualTitle, expectedTitle);
     //firstname
		driver.findElement(By.id("firstName")).sendKeys(firstName);
		Thread.sleep(2000);
	//Lastname	
		driver.findElement(By.id("lastName")).sendKeys(lastName);
		Thread.sleep(2000);
	//email	
		driver.findElement(By.id("userEmail")).sendKeys(email);
		Thread.sleep(2000);
	//Gender	
		if (gender.equalsIgnoreCase("female")) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("document.getElementById('gender-radio-2').click();");
		} else if (gender.equalsIgnoreCase("male")) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("document.getElementById('gender-radio-1').click();");

		} else {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("document.getElementById('gender-radio-3').click();");
		}
		Thread.sleep(2000);
	//Mobile number
		driver.findElement(By.id("userNumber")).sendKeys(mobile);
		Thread.sleep(2000);
    //Date of Birth
		Actions actions = new Actions(driver);
		WebElement elementLocator = driver.findElement(By.id("dateOfBirthInput"));
		elementLocator.click();
		
		
		
		Robot   rbt= new Robot();
		
		rbt.keyPress(KeyEvent.VK_CONTROL);
		rbt.keyPress(KeyEvent.VK_A);

		rbt.keyRelease(KeyEvent.VK_CONTROL);
		rbt.keyRelease(KeyEvent.VK_A);
		//actions.keyDown(elementLocator, Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();*/
		
		
		
		
		
		Thread.sleep(3000);
		actions.sendKeys(birthdate).build().perform();
		actions.sendKeys(Keys.ENTER).build().perform();
    //subject
		driver.findElement(By.id("subjectsContainer")).click();

		actions.sendKeys(subject).build().perform();
		actions.sendKeys(Keys.ENTER).build().perform();
		Thread.sleep(2000);
    //Hobbies
		if (hobby.equalsIgnoreCase("Music")) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("document.getElementById('hobbies-checkbox-3').click();");
		}

		else if (hobby.equalsIgnoreCase("Reading")) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("document.getElementById('hobbies-checkbox-2').click();");
		}

		else if (hobby.equalsIgnoreCase("Sports")) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("document.getElementById('hobbies-checkbox-1').click();");
		}
  //Upload file
ClassLoader classLoader = this.getClass().getClassLoader();

String path=classLoader.getResource(imagePath).getFile();
String path1 = path.substring(1);
System.out.println(path);
		
		driver.findElement(By.id("uploadPicture")).sendKeys(path1);
		Thread.sleep(2000);
  //Address
		driver.findElement(By.id("currentAddress")).sendKeys(address);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@id=\"state\"]")).click();
		actions.sendKeys(state).build().perform();
		actions.sendKeys(Keys.ENTER).build().perform();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@id=\"city\"]")).click();
		
		actions.sendKeys(city).build().perform();
		actions.sendKeys(Keys.ENTER).build().perform();
		Thread.sleep(2000);
		driver.findElement(By.id("submit")).click();
		
   //Assertion for Name
		String actualname = driver
				.findElement(By.xpath(
						"(//table[@class=\"table table-dark table-striped table-bordered table-hover\"]//tr//td)[2]"))
				.getText();
		System.out.println(actualname);
		String expectedName = firstName + " " + lastName;
		System.out.println("expectedName" + " " + expectedName);
		assertEquals(actualname, expectedName);
		  //Assertion for Email
		String actualemail = driver
				.findElement(By.xpath(
						"(//table[@class=\"table table-dark table-striped table-bordered table-hover\"]//td)[4]"))
				.getText();

		String expectedemail = email;

		assertEquals(actualemail, expectedemail);
		
	//  //Assertion for MobileNumber
		String actualMobileNumber = driver
				.findElement(By.xpath(
						"(//table[@class=\"table table-dark table-striped table-bordered table-hover\"]//td)[8]"))
				.getText();

		String expectedMobileNumber = mobile;

		assertEquals(actualMobileNumber, expectedMobileNumber);

		Thread.sleep(1000);

		driver.findElement(By.id("closeLargeModal")).click();
	}

	@AfterTest
	public void close() {
		driver.quit();
		System.out.println("test completed successfully");
	}

	@DataProvider(name = "testdata")
	public Object[][] TestDataFeed() {

		ClassLoader classLoader = this.getClass().getClassLoader();
		ReadExcelFile config = new ReadExcelFile(classLoader.getResource("testData/TC1.xlsx").getFile());

		int rows = config.getRowCount(0);

		Object[][] credentials = new Object[rows][12];

		for (int i = 0; i < rows; i++) {
			credentials[i][0] = config.getData(0, i, 0);
			credentials[i][1] = config.getData(0, i, 1);
			credentials[i][2] = config.getData(0, i, 2);
			credentials[i][3] = config.getData(0, i, 3);
			credentials[i][4] = config.getData(0, i, 4);
			credentials[i][5] = config.getData(0, i, 5);
			credentials[i][6] = config.getData(0, i, 6);
			credentials[i][7] = config.getData(0, i, 7);
			credentials[i][8] = config.getData(0, i, 8);
			credentials[i][9] = config.getData(0, i, 9);
			credentials[i][10] = config.getData(0, i, 10);
			credentials[i][11] = config.getData(0, i, 11);
		}

		return credentials;
	}

}