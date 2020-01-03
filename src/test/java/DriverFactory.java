import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import java.net.URL;

import org.junit.rules.TestName;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverFactory {
    private PlatformType platformType;
    private ServerType serverType;
    private String app;
    private URL serverUrl;
    private String testName;

    public DriverFactory(PlatformType platformType, ServerType serverType, String appStr, URL serverUrl, String name) {
        this.platformType = platformType;
        this.serverType = serverType;
        this.app = appStr;
        this.serverUrl = serverUrl;
        this.testName = name;
    }

    public AppiumDriver getDriver() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("app", app);

        if (serverType == ServerType.SAUCE) {
            caps.setCapability("appiumVersion", "1.9.1");
            caps.setCapability("name", testName);
        }

        if (platformType == PlatformType.ANDROID) {
            String deviceName = "Android Emulator";
            caps.setCapability("platformName", "Android");
            if (serverType == ServerType.SAUCE) {
                deviceName = "Android GoogleAPI Emulator";
                caps.setCapability("platformVersion", "9.0");
            }
            caps.setCapability("deviceName", deviceName);
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
