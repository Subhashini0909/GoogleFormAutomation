package demo.wrappers;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wrappers {
    private static final int TIMEOUT_SECONDS = 15;

    // Wrapper for navigating to a URL
    public static void navigateTo(ChromeDriver driver, String url) {
        driver.get(url);
        System.out.println("Navigated to: " + url);
    }

    // Wrapper for typing into an input field (waits until it is visible)
    public static void typeText(ChromeDriver driver, By locator, String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.sendKeys(text);
    }

    // Wrapper for clicking an element (waits until it is clickable)
    public static void clickElement(ChromeDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    // Wrapper for reading the text of an element (waits until it is visible)
    public static String getText(ChromeDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }
}