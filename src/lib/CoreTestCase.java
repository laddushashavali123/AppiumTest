package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class CoreTestCase extends TestCase {
    private final static String platform = System.getenv("PLATFORM");
    private final static String PLATFORM_IOS = "ios";
    private final static String PLATFORM_ANDROID = "android";

    protected AppiumDriver driver;
    private static String appiumUrl = "http://127.0.0.1:4723/wd/hub";

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        DesiredCapabilities capabilities = this.getCapabilitiesByPlatformEnv();
        driver = getDriverByPlatformEnv(capabilities);
        this.rotateScreenPortrait();
    }

    @Override
    protected void tearDown() throws Exception {
        driver.quit();
        super.tearDown();
    }

    protected void rotateScreenPortrait() {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    protected void rotateScreenLandscape() {
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    protected void sendAppToBackground(int seconds) {
        driver.runAppInBackground(seconds);
    }

    protected void hideKeyboard() {
        driver.hideKeyboard();
    }

    protected void resetApp() {
        driver.resetApp();
    }

    private DesiredCapabilities getCapabilitiesByPlatformEnv() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        System.out.println("Platform: " + platform.toUpperCase());
        if(platform.equals(PLATFORM_ANDROID)) {
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("deviceName", "AndroidTestDevice");
            capabilities.setCapability("platformVersion", "8.0");
            capabilities.setCapability("automationName", "Appium");
            capabilities.setCapability("appPackage", "org.wikipedia");
            capabilities.setCapability("appActivity", ".main.MainActivity");
            capabilities.setCapability("app", "/Users/Christina/Desktop/JavaAppiumAutomation/apks/org.wikipedia.apk");
        } else if(platform.equals(PLATFORM_IOS)) {
            capabilities.setCapability("platformName", "iOS");
            capabilities.setCapability("deviceName", "iPhone SE");
            capabilities.setCapability("platformVersion", "11.3");
            capabilities.setCapability("app", "/Users/Christina/Desktop/JavaAppiumAutomation/apks/Wikipedia.app");
        } else {
            throw new Exception("Unable to launch platform from env variable. Platform value: " + platform);
        }

        return capabilities;
    }

    private AppiumDriver getDriverByPlatformEnv(DesiredCapabilities capabilities) throws Exception {
        if(platform.equals(PLATFORM_ANDROID)) {
            driver = new AndroidDriver(new URL(appiumUrl), capabilities);
        } else if(platform.equals(PLATFORM_IOS)) {
            driver = new IOSDriver(new URL(appiumUrl), capabilities);
        } else {
            throw new Exception("Unable to initiate appium driver for env variable. Platform value: " + platform);
        }
        return driver;
    }

}
