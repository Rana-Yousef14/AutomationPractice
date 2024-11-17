import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.checkerframework.checker.units.qual.s;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.idealized.Javascript;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;

public class MyTestCases {

	WebDriver driver = new ChromeDriver();
	String websiteUrl = "https://codenboxautomationlab.com/practice/";
	Random rand = new Random();

	@BeforeTest
	public void setUp() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get(websiteUrl);
		driver.manage().window().maximize();

	}

	@Test(priority = 1, enabled = false)
	public void radioButtons() {
		// select one random radio button
		WebElement divForRadio = driver.findElement(By.id("radio-btn-example"));
		List<WebElement> inputRadio = divForRadio.findElements(By.tagName("input"));
		int randomIndex = rand.nextInt(inputRadio.size());
		WebElement selectedInput = inputRadio.get(randomIndex);
		selectedInput.click();
		boolean actaulResult = selectedInput.isSelected();
		boolean expectedResult = true;
		Assert.assertEquals(actaulResult, expectedResult);
	}

	@Test(priority = 2, enabled = false)
	public void dynamicDropDown() throws InterruptedException {
		String[] myRandomTwoCharacter = { "AB", "EA", "GH", "IJ", "KL", "MO", "OP" };
		int randomIndex = rand.nextInt(myRandomTwoCharacter.length);
		String myInputData = myRandomTwoCharacter[randomIndex];
		WebElement autocompleteInput = driver.findElement(By.id("autocomplete"));
		autocompleteInput.sendKeys(myInputData);
		Thread.sleep(1000);
		autocompleteInput.sendKeys(Keys.chord(Keys.ARROW_DOWN, Keys.ENTER));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		String dataInsidMyInput = (String) js.executeScript("return arguments[0].value", autocompleteInput);
		String updatedData = dataInsidMyInput.toLowerCase();
		boolean actualResult = updatedData.contains(myInputData.toLowerCase());
		Assert.assertEquals(actualResult, true);
	}

	@Test(priority = 3, enabled = false)
	public void selectTag() {
		WebElement mySelectElement = driver.findElement(By.id("dropdown-class-example"));
		Select selctor = new Select(mySelectElement);
		int randomIndex = rand.nextInt(1, 4);

		// selctor.selectByVisibleText("API");
		// selctor.deselectByValue("option2");
		selctor.selectByIndex(randomIndex);
	}

	@Test(priority = 4, enabled = false)
	public void checkBox() {
		WebElement divForCheckBox = driver.findElement(By.id("checkbox-example"));
		List<WebElement> optionsForTheCheckBoox = divForCheckBox.findElements(By.xpath("//input[@type='checkbox']"));

		for (int i = 0; i < optionsForTheCheckBoox.size(); i++) {
			optionsForTheCheckBoox.get(i).click();
		}
	}

	@Test(priority = 5, enabled = false)
	public void switchWindows() throws InterruptedException {
		WebElement openWindowButton = driver.findElement(By.id("openwindow"));
		openWindowButton.click();
		Thread.sleep(1000);
		// List<String> windowHandles = new ArrayList<>(String);
		List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(windowHandles.get(1));
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"menu-item-9675\"]/a/span[1]")).click();

		// get back to the main webdriver the first chrome browswe
		driver.switchTo().window(windowHandles.get(0));

		Thread.sleep(3000);
		WebElement divForCheckBox = driver.findElement(By.id("checkbox-example"));
		List<WebElement> optionsForTheCheckBoox = divForCheckBox.findElements(By.xpath("//input[@type='checkbox']"));

		for (int i = 0; i < optionsForTheCheckBoox.size(); i++) {
			optionsForTheCheckBoox.get(i).click();
		}
	}

	@Test(priority = 6, enabled = false)
	public void switchTabs() throws InterruptedException {
		driver.findElement(By.id("opentab")).click();
		List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(windowHandles.get(1));
		Thread.sleep(3000);
		String actualTitle = driver.getTitle();
		String expectedTitle = "Codenbox AutomationLab - YouTube";
		Assert.assertEquals(actualTitle, expectedTitle, expectedTitle);
	}

	@Test(priority = 7, enabled = false)
	public void switchToAlert() throws InterruptedException {
		// alert normal
		driver.findElement(By.id("alertbtn")).click();
		driver.switchTo().alert().accept();

		// to dismiss the confirm alert ( to cancel it )
		// driver.switchTo().alert().dismiss();

		driver.findElement(By.id("name")).sendKeys("Rana");
		// confirm alert
		driver.findElement(By.id("confirmbtn")).click();
		Thread.sleep(2000);

//		boolean actualResult = driver.switchTo().alert().getText().contains("Rana");
//	    boolean expectedResult = true;

		String actualResult = driver.switchTo().alert().getText();
		String expectedResult = "Hello Rana, Are you sure you want to confirm?";
		Assert.assertEquals(actualResult, expectedResult);
		// to accept the alert ( and send the information for example my name that
		// inside the box)
		driver.switchTo().alert().accept();
	}

	@Test(priority = 8, enabled = false)
	public void tableData() throws InterruptedException {
		WebElement theTable = driver.findElement(By.id("product"));
		String myString = theTable.findElements(By.tagName("td")).get(0).getText();
		driver.findElement(By.id("name")).sendKeys(myString);

		List<WebElement> tdTags = theTable.findElements(By.tagName("td"));
		for (int i = 1; i < tdTags.size(); i += 3) {
			System.out.println(tdTags.get(i).getText());
		}
	}

	@Test(priority = 9, enabled = false)
	public void elementDisplayed() throws InterruptedException {
		// hide element
		WebElement hideButton = driver.findElement(By.id("hide-textbox"));
		hideButton.click();
		WebElement thElementThatWeWantToHide = driver.findElement(By.id("displayed-text"));
		boolean actualResult = thElementThatWeWantToHide.isDisplayed();
		boolean expectedResult = false;
		Assert.assertEquals(actualResult, expectedResult);

		// show element
		Thread.sleep(3000);
		WebElement showButton = driver.findElement(By.id("show-textbox"));
		showButton.click();
		boolean actualResult2 = thElementThatWeWantToHide.isDisplayed();
		boolean expectedResult2 = true;
		Assert.assertEquals(actualResult2, expectedResult2);
	}

	@Test(priority = 10, enabled = false)
	public void enabled_Displayed() {
		WebElement disableButton = driver.findElement(By.id("disabled-button"));
		disableButton.click();
		WebElement theElementThatWeNeedToDisable = driver.findElement(By.id("enabled-example-input"));
		boolean actualResult = theElementThatWeNeedToDisable.isEnabled();
		boolean expectedResult = false;
		Assert.assertEquals(actualResult, expectedResult);

		WebElement enableButton = driver.findElement(By.id("enabled-button"));
		enableButton.click();
		boolean actualResult2 = theElementThatWeNeedToDisable.isEnabled();
		boolean expectedResult2 = true;
		Assert.assertEquals(actualResult2, expectedResult2);

	}

	@Test(priority = 11, enabled = false)
	public void mouseHover() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,1750)");
		// js.executeScript("console.log(\"hi+@@@@@@@@@@@@@@\")");
		Actions action = new Actions(driver);
		WebElement theTarget = driver.findElement(By.id("mousehover"));
		action.moveToElement(theTarget).perform();
		Thread.sleep(1000);
		// driver.findElement(By.linkText("Top")).click();
		driver.findElement(By.linkText("Reload")).click();
		// the same ----- driver.navigate().refresh();

	}

	@Test(priority = 11, enabled = false)
	public void calender() throws InterruptedException {
		WebElement calenderBooking = driver.findElement(By.linkText("Booking Calendar"));
		calenderBooking.click();
		List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(windowHandles.get(1));
		Thread.sleep(3000);

		// this is the correct solution - the problem is when you try this you will get
		// error says stale element not found ( the develope is not using react or any
		// modern framework )
		// the modern Framework applies the SPA single page application

//		List<WebElement> availableDate = driver.findElements(By.xpath("//a[@href='javascript:void(0)']"));
//		System.out.println(availableDate.size());

//		for (int i = 1; i < availableDate.size(); i++) {
//			availableDate.get(i).click();
//			Thread.sleep(1000);
//		}

		// this is not a good solution because i have to repeat the code as much as
		// avaliable times are there

//		AvailableDate.get(1).click();
//		AvailableDate.get(2).click();
//		AvailableDate.get(3).click();

		for (int i = 1; i < 25; i++) {
			List<WebElement> AvailableDate = driver.findElements(By.xpath("//a[@href='javascript:void(0)']"));

			AvailableDate.get(i).click();
			Thread.sleep(1000);
		}

	}

	@Test(priority = 12)
	public void iFrame() throws InterruptedException {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,2400)");

		WebElement myFrame = driver.findElement(By.id("courses-iframe"));

		Thread.sleep(3000);
		driver.switchTo().frame(myFrame);
		js.executeScript("window.scrollTo(0,4500)");

		String myText = driver.findElement(By.xpath("//*[@id=\"ct_heading-1b594e8\"]/div/h3/span")).getText();

		System.out.println(myText);

		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"ct_button-20c391b5\"]/a/span[2]")).click();
		Thread.sleep(5000);

		driver.findElement(By.xpath("//*[@id=\"ct-pagetitle\"]/div/ul/li[1]/a")).click();
	}
}
