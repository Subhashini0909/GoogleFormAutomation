package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;

     
    private static final String FORM_URL = "https://docs.google.com/forms/d/e/1FAIpQLSep9LTMntH5YqIXa5nkiPKSs283kdwitBBhXWyZdAS-e4CxBQ/viewform";

    private By question(String title, String inner) {
        return By.xpath("//div[@role='listitem'][.//span[contains(text(),'" + title + "')]]" + inner);
    }

    @Test
    public void testCase01() throws InterruptedException {
        // Step 1: open the google form
        Wrappers.navigateTo(driver, FORM_URL);

        // Step 2: fill the name
        Wrappers.typeText(driver, question("Name", "//*[(self::input or self::textarea) and not(@type='hidden') and not(@aria-hidden='true')]"), "Crio Learner");

        // Step 3: 
        long epoch = Instant.now().getEpochSecond();
        Wrappers.typeText(driver, question("Why are you practicing Automation", "//*[(self::input or self::textarea) and not(@type='hidden') and not(@aria-hidden='true')]"),
                "I want to be the best QA Engineer! " + epoch);

        // Step 4: radio button
        Wrappers.clickElement(driver, By.xpath("//div[@role='radio' and @data-value='3 - 5']"));

        // Step 5: select Java, Selenium and TestNG check-boxes
        for (String skill : new String[] {"Java", "Selenium", "TestNG"}) {
            Wrappers.clickElement(driver, By.xpath("//div[@role='checkbox' and @aria-label='" + skill + "']"));
        }

        // Step 6
        Wrappers.clickElement(driver, question("How should you be addressed", "//div[@role='listbox']"));
        Wrappers.clickElement(driver, By.xpath("(//div[@role='option' and string-length(@data-value)>0])[1]"));

        // Step 7
        String date = LocalDate.now().minusDays(7).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Wrappers.typeText(driver, question("What was the date 7 days ago", "//input[@type='date']"), date);

        // Step 8: time 07:30
        Wrappers.typeText(driver, question("What is the time right now", "//input[@aria-label='Hour']"), "07");
        Wrappers.typeText(driver, question("What is the time right now", "//input[@aria-label='Minute']"), "30");

        // Step 9: submit the form
        Wrappers.clickElement(driver, By.xpath("//span[text()='Submit']"));
        System.out.println("Form submitted");

        // Print the success message 
        Thread.sleep(3000);
        String message = Wrappers.getText(driver, By.xpath("//div[contains(text(),'Thanks for your response')]"));
        System.out.println(message);
    }

   
    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");


        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }
}