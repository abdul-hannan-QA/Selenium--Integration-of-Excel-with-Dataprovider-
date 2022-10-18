package dataprovider;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



import io.github.bonigarcia.wdm.WebDriverManager;


public class readingexcel {


	public static WebDriver 	driver;


	@BeforeSuite
	public static WebDriver  setup() {



		WebDriverManager.chromedriver().setup();

		//headless browser
		ChromeOptions option=new ChromeOptions();
		option.addArguments("window-size=1400,800");
		option.addArguments("headless");

		driver = new ChromeDriver();
		return driver;


	}

	@Test(dataProvider = "loginData")

	public void loginTest(String Email,String pwd,String exp) throws Exception {

		//System.out.println(Email+pwd+exp);
				driver.get("https://salespype.pypepro.com/user/login");
				driver.manage().window().maximize();
				Thread.sleep(2000);
				WebElement txtEmail=driver.findElement(By.xpath("//input[@id='email']"));
				txtEmail.clear();
				txtEmail.sendKeys(Email);
				
				Thread.sleep(2000);
				WebElement txtpdw=driver.findElement(By.xpath("//input[@id='password']"));
				txtpdw.clear();
				txtpdw.sendKeys(pwd);
				
				
				Thread.sleep(2000);
				driver.findElement(By.xpath("//button[contains(@type,'submit')]")).click();
				Thread.sleep(2000);
			
			String exp_title= "Pypepro";
//				//Thread.sleep(1000);
				String actual_title= driver.getTitle();
				
				 
				if (exp.equals("Valid")) {
					
					
					if(exp_title.equals(actual_title)) {
						
					
						driver.findElement(By.xpath("//h5[@class='txt_ds_top_bar']")).click();
						
						WebElement element1 = driver.findElement(By.xpath("//a[contains(normalize-space(),'log out')]"));
						JavascriptExecutor js1 = (JavascriptExecutor)driver;
						js1.executeScript("arguments[0].click()", element1);
						Assert.assertTrue(true);
						System.out.println("pass");
					}
					else
					{
						Assert.assertTrue(false);
						System.out.println("fail");
					}
				}
				else if (exp.equals("InValid"))
				{
					if(exp_title.equals(actual_title)) {
						
//						driver.findElement(By.xpath("//i[text()='expand_more']")).click();
//						driver.findElement(By.linkText("log out")).click();
						
						driver.findElement(By.xpath("//h5[@class='txt_ds_top_bar']")).click();
					    //Thread.sleep(1000);
						WebElement element2 = driver.findElement(By.xpath("//a[contains(normalize-space(),'log out')]"));
						JavascriptExecutor js2 = (JavascriptExecutor)driver;
						js2.executeScript("arguments[0].click()", element2);
						
						
						Assert.assertTrue(false);
					}
					else
					{
						Assert.assertTrue(true);
					}
					
				}
				
				
			

			}
	
	@DataProvider(name="loginData")
	public String [] []  getData() throws IOException {
		/*
		String loginData[][]= {
				{"hannan@orangetoolz.com","qwerty","Valid"},
				{"abc@gmail.com","qwerty","InValid"},
				{"hannan@orangetoolz.com","4353535","InValid"}


		}; */

		///*
		//get the data from excel
		String path=".\\datafiles\\data.xlsx";
		XLUtility xlutil=new XLUtility(path);

		int totalrows=xlutil.getRowCount("Sheet1");
		int totalcols=xlutil.getCellCount("Sheet1",1);	

		String loginData[][]=new String[totalrows][totalcols];


		for(int i=1;i<=totalrows;i++) //1
		{
			for(int j=0;j<totalcols;j++) //0
			{
				loginData[i-1][j]=xlutil.getCellData("Sheet1", i, j);
			}

		}


//*/
		return loginData;
	}





	@AfterSuite
	public static  void teardown() {

		driver.close();
	}


}









