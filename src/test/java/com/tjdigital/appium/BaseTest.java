package com.tjdigital.appium;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.PropertyResourceBundle;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.ServerArgument;

/**
 * @author Tristan Zhou on 21/01/2018.
 */
public class BaseTest {

    private static final String DEVICE_NAME = "deviceName";
    private static final String AVD_NAME = "avdName";
    private static final String ANDROID_APP_PATH = "androidAppPath";
    private static final String APP_PACKAGE = "appPackage";
    private static final String APP_ACTIVITY = "appActivity";

    AppiumDriver<WebElement> driver;
    private static AppiumDriverLocalService service;
    private static PropertyResourceBundle resource;

    @Before
    public void setUp() throws Exception {
        resource = new PropertyResourceBundle(BaseTest.class.getClassLoader().getResourceAsStream("config.properties"));

        service = this.buildAPI25Service();
        service.start();

        if (service == null || !service.isRunning()) {
            throw new AppiumServerHasNotBeenStartedLocallyException("An appium server node is not started!");
        }
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, resource.getString(DEVICE_NAME));
        capabilities.setCapability(APP_PACKAGE, resource.getString(APP_PACKAGE));
        capabilities.setCapability(APP_ACTIVITY, resource.getString(APP_ACTIVITY));

        File classpathRoot = new File(System.getProperty("user.dir"));
        File app = new File(classpathRoot, resource.getString(ANDROID_APP_PATH));
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());

        driver = new AndroidDriver<>(service.getUrl(), capabilities);
    }

    private AppiumDriverLocalService buildAPI25Service() {
        return AppiumDriverLocalService.buildService(new AppiumServiceBuilder().withArgument(new ServerArgument() {
            public String getArgument() {
                return "--avd";
            }
        }, resource.getString(AVD_NAME)));
    }

    @After
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
        if (service != null) {
            service.stop();
        }
    }

}
