import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import java.io.File;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverFactory {
    private PlatformType platformType;
    private File app;
    private URL serverUrl;

    public DriverFactory(PlatformType platformType, File app, URL serverUrl) {
        this.platformType = platformType;
        this.app = app;
        this.serverUrl = serverUrl;
    }

    public AppiumDriver getDriver() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("app", app);

        if (platformType == PlatformType.ANDROID) {
            caps.setCapability("platformName", "Android");
            caps.setCapability("platformVersion", "9");
            caps.setCapability("deviceName", "Mi A2");
            caps.setCapability("noReset", true);
            caps.setCapability("automationName", "UiAutomator2");
            return new AndroidDriver(serverUrl, caps);
        } else {
            caps.setCapability("platformName", "iOS");
            caps.setCapability("platformVersion", "11.2");
            caps.setCapability("deviceName", "iPhone 7");
            return  new IOSDriver(serverUrl, caps);
        }
    }
}
