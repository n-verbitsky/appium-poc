import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import pages.LoggedInView;
import pages.LoginView;
import pages.MainView;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;

public abstract class BaseTest {
    private AppiumDriver driver;
    private PlatformType platform;
    private final String appiumUrl = "http://localhost:4723/wd/hub";

    protected MainView mainView;
    protected LoginView loginView;
    protected LoggedInView loggedInView;

    public BaseTest() {
        String cliArg = System.getProperty("platformType");
        if (cliArg == null) {
            cliArg = "ios";
        }
        if (cliArg.equalsIgnoreCase(PlatformType.ANDROID.name())) {
            platform = PlatformType.ANDROID;
        } else {
            platform = PlatformType.IOS;
        }
    }

    private File getAppFile(String app) throws URISyntaxException {
        URL resource = getClass()
                .getClassLoader()
                .getResource("apps/" + app);
        return Paths
                .get(Objects.requireNonNull(resource).toURI())
                .toFile();
    }

    @Before
    public void setUp() throws MalformedURLException, URISyntaxException {
        URL serverUrl = new URL(appiumUrl);
        File app = getAppFile("TheApp-v1.2.1.app.zip");
        if (platform == PlatformType.ANDROID) {
            app = getAppFile("TheApp-v1.2.1.apk");
        }
        DriverFactory driverFactory = new DriverFactory(platform, app, serverUrl);
        driver = driverFactory.getDriver();
        driver.manage().window().maximize();

        mainView = new MainView(driver);
        loginView = new LoginView(driver);
        loggedInView = new LoggedInView(driver);
    }

    @After
    public void tearDown() {
        try {
            driver.quit();
        } catch (Exception ignore) {}
    }
}