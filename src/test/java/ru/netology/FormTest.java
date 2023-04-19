package ru.netology;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormTest {
    private WebDriver driver;
    private static ChromeOptions options;

    @BeforeAll
    static void setUpAll() {
        options = new ChromeOptions();
        options.addArguments("--headless");
        System.setProperty("webdriver.chrome.driver", "driver/linux/chromedriver");
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void testPositiveAllInput() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Дмитрий Евдокимов");
        elements.get(1).sendKeys("+79270000000");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void testNegativeNameEmpty() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(1).sendKeys("+79270000000");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void testNegativePhoneEmpty() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Дмитрий Евдокимов");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] span.input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void testNegativeAgreementEmpty() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Дмитрий Евдокимов");
        elements.get(1).sendKeys("+79270000000");
        driver.findElement(By.className("button")).click();
//        String text = driver.findElement(By.className("checkbox__text")).getCssValue("Color");
        String text = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());
    }
}
