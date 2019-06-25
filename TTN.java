package com.qa.tatoctestng;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;	
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TTN {
	WebDriver driver;
	 String b1,b2;
	 WebElement x,y;
	 String MainWindow;
	/*
		@Test
		public void open() {
			String url = "http://10.0.1.86/tatoc";
			System.setProperty("webdriver.chrome.driver", "/home/qainfotech/Downloads/chromedriver");
			driver = new ChromeDriver();
			driver.get(url);
			System.out.println("open");
			// driver.manage().window().maximize();
		}
	
		@Test(dependsOnMethods = "open")
		public void checkTitle() {
			  
		     String expectedTitle = "Welcome - T.A.T.O.C"; String actualTitle =
			 driver.getTitle(); System.out.println("checkTitle");
			 Assert.assertEquals(actualTitle, expectedTitle, "galat hai");
			 System.out.println("checkTitle");
	InterruptedException
		}@test
		
	*/
	 @Test(priority=-001)
		public void Test01Open() throws IOException {
		   
		 Properties p=new Properties();
		 //     For windows  
		 //FileInputStream objfile=new FileInputStream(System.getProperty("user.dir") + "\\firstfile.properties");
		 //For Ubunto
		 FileInputStream objfile=new FileInputStream(System.getProperty("user.dir") + "/firstfile.properties");

		 p.load(objfile);	 
			System.setProperty("webdriver.chrome.driver", "/home/qainfotech/Downloads/chromedriver");
			 driver = new ChromeDriver();
			driver.get(p.getProperty("url"));
			driver.manage().window().maximize();
		}
	    @Test(priority=2)
		public void Test02click() {
	    	driver.findElement(By.linkText("Basic Course")).click();
			driver.findElement(By.cssSelector("div.greenbox[onclick=\"passthru();\"]")).click();
			driver.switchTo().frame("main");
			 b1 = driver.findElement(By.id("answer")).getAttribute("class");
		}
	
	    @Test(priority=3)
		public void Test03boxcolourmatched() {
	    
	
			do {
				driver.findElement(By.linkText("Repaint Box 2")).click();
				driver.switchTo().frame("child");
				b2 = driver.findElement(By.id("answer")).getAttribute("class");
				//driver.switchTo().frame("main");
				driver.switchTo().parentFrame();
				// System.out.println(b2);
	
			} while (!(b1.equals(b2)));
			driver.findElement(By.linkText("Proceed")).click();
		}
		
		@Test(priority=4)
		public void Test04dragdrop() {
			
			 x = driver.findElement(By.cssSelector("div.ui-draggable"));
			 y = driver.findElement(By.cssSelector("div#dropbox"));
			 Actions builder = new Actions(driver);
				builder.dragAndDrop(x, y).build().perform();
				driver.findElement(By.linkText("Proceed")).click();
				driver.findElement(By.linkText("Launch Popup Window")).click();
	
		}
	    
		
		@Test (priority=5)
		public void Test05TabSwitching() {
			MainWindow = driver.getWindowHandle();
			// System.out.println(MainWindow);
			Set<String> s1 = driver.getWindowHandles();
			Iterator<String> i1 = s1.iterator();
	
			while (i1.hasNext()) {
				String ChildWindow = i1.next();
				if (!MainWindow.equalsIgnoreCase(ChildWindow)) {
					driver.switchTo().window(ChildWindow);
					/*
					 * b2 = driver.getTitle(); System.out.println(b2);
					 * System.out.println(ChildWindow);
					 */
				}
			}
	
		}
		 
		
		
		
		@Test (priority=6)
		public void Test06SubmitData() throws InterruptedException, DocumentException {
			//read xml file (first.xml)
			//For Windows :-
			//File inputFile = new File(System.getProperty("user.dir") +"\\first.xml");
			//For Ubunto :- 
			//File inputFile = new File("/home/qainfotech/eclipse-workspace/tatoctestng/first.xml");or
			File inputFile = new File(System.getProperty("user.dir") +"/first.xml");
			                          
	        SAXReader saxReader = new SAXReader();
	        Document document = saxReader.read(inputFile);
	        String str = document.selectSingleNode("//menu/name").getText();
			driver.findElement(By.id("name")).sendKeys(str);
			

			Thread.sleep(1000);
			driver.findElement(By.id("submit")).click();
			Thread.sleep(1000);
			driver.switchTo().window(MainWindow);
			driver.findElement(By.linkText("Proceed")).click();
			Thread.sleep(1000);
			
			
		}
		@Test(priority =7)
		public void Cookie() throws InterruptedException 
		{
			driver.findElement(By.linkText("Generate Token")).click();
			Thread.sleep(1000);
			String token = driver.findElement(By.xpath("//*[@id=\"token\"]")).getText();
			token = token.substring(7);
			System.out.println(token);
	
			  Cookie name=new Cookie("my cookies",token); 
			  driver.manage().addCookie(name);
			  Cookie n = driver.manage().getCookieNamed("my cookies");
			  System.out.println(n);
			  /*Set<Cookie> cookiesList = driver.manage().getCookies(); 
			  Iterator <Cookie> i =cookiesList.iterator();
			  System.out.println(i.next());
			  */
			  /*for(Cookie getcookies:cookiesList) { 
				  System.out.println(getcookies);
		          }
	*/
		
	     }
		@AfterTest
		public void Dlose()
		{
			x =driver.findElement(By.cssSelector("a[onclick=\"gonext();\"]"));
			
			x.click();
			driver.close();
		}
		
}
