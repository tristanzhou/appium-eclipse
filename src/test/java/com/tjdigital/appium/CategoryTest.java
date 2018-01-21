package com.tjdigital.appium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.functions.ExpectedCondition;

/**
 * @author Tristan Zhou on 21/01/2018.
 */
public class CategoryTest extends BaseTest {

    @Test
    public void addCategory() {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.findElement(By.xpath(".//*[contains(@resource-id,'tv_ah_setting')]")).click();
        driver.findElement(By.xpath(".//*[contains(@resource-id,'ll_as_category')]")).click();
        driver.findElement(By.xpath(".//*[contains(@resource-id,'tv_ac_add')]")).click();

        driver.findElement(By.xpath(".//*[contains(@resource-id,'et_aac_category_name')]")).sendKeys("1/2/3");
        driver.findElement(By.xpath(".//*[contains(@resource-id,'et_aac_green_minute')]")).sendKeys("1");
        driver.findElement(By.xpath(".//*[contains(@resource-id,'et_aac_yellow_minute')]")).sendKeys("2");
        driver.findElement(By.xpath(".//*[contains(@resource-id,'et_aac_red_minute')]")).sendKeys("3");
        driver.findElement(By.xpath(".//*[contains(@resource-id,'btn_aac_save')]")).click();

        (new WebDriverWait(driver, 5)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                WebElement element = driver.findElement(By.xpath(".//*[@class='android.widget.TextView'][@text='1/2/3']"));
                return element.isDisplayed();
            }
        });
    }

}
