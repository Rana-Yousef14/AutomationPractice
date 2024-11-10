import java.time.Duration;
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

	@Test(priority = 1)
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

	@Test(priority = 2)
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

	@Test(priority = 3)
	public void selectTag() {
		WebElement mySelectElement = driver.findElement(By.id("dropdown-class-example"));
		Select selctor = new Select(mySelectElement);
		int randomIndex = rand.nextInt(1, 4);

		// selctor.selectByVisibleText("API");
		// selctor.deselectByValue("option2");
		selctor.selectByIndex(randomIndex);
	}

	@Test(priority = 1, enabled = false)
	public void switchToAlert() throws InterruptedException {
		driver.findElement(By.id("alertbtn")).click();
		Thread.sleep(5000);
		System.out.println(driver.switchTo().alert().getText());
		driver.switchTo().alert().accept();
	}

	@Test(priority = 2, enabled = false)
	public void tableData() throws InterruptedException {
		driver.get("https://codenboxautomationlab.com/practice/");
		Thread.sleep(2000);
		List<WebElement> tdTags = driver.findElement(By.id("product")).findElements(By.tagName("td"));
		for (int i = 1; i < tdTags.size(); i += 3) {
			System.out.println(tdTags.get(i).getText());
		}
	}

	@Test(priority = 3, enabled = false)
	public void hidebutton() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.id("hide-textbox")).click();
		boolean actualResult = driver.findElement(By.id("displayed-text")).isDisplayed();
		boolean expectedResult = false;
		Assert.assertEquals(actualResult, expectedResult);
	}

	@Test(priority = 4, enabled = false)
	public void mouseHover() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,1700)");
		js.executeScript("console.log(\"hi+@@@@@@@@@@@@@@\")");
		Actions action = new Actions(driver);
		WebElement target = driver.findElement(By.id("mousehover"));
		action.moveToElement(target).perform();
		Thread.sleep(1000);
		driver.findElement(By.linkText("Top")).click();
	}

	@Test(priority = 5, enabled = false)
	public void iframe() throws InterruptedException {
		// driver.switchTo().frame("iframe-name");
		WebElement target = driver.findElement(By.xpath("//*[@id=\"courses-iframe\"]"));
		driver.switchTo().frame(target);
		System.out.println(driver.findElement(By.xpath("//*[@id=\"ct_list_single-5e9f920\"]/div[2]")).getText());
	}
}
