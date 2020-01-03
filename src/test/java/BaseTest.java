import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import com.saucelabs.saucerest.SauceREST;
import static com.saucelabs.saucerest.DataCenter.EU;
import io.appium.java_client.AppiumDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import pages.LoggedInView;
import pages.LoginView;
import pages.MainView;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;

public abstract class BaseTest  implements SauceOnDemandSessionIdProvider {
    public static String username = System.getenv("SAUCE_USERNAME");
    public static String accessKey = System.getenv("SAUCE_ACCESS_KEY");
    public static String localAppiumUrl = "http://localhost:4723/wd/hub";
    public static String sauceServer = "@ondemand.eu-central-1.saucelabs.com:80/wd/hub";

    private AppiumDriver driver;
    private PlatformType platform;
    private ServerType server;
    private String sessionId;

    protected MainView mainView;
    protected LoginView loginView;
    protected LoggedInView loggedInView;

    private SauceREST sauceAPI = new SauceREST(username, accessKey, EU);
    private SauceOnDemandAuthentication auth = new SauceOnDemandAuthentication(username, accessKey);

    @Rule
    public SauceOnDemandTestWatcher watcher = new SauceOnDemandTestWatcher(this, auth);

    @Rule
    public TestName name = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };


    public BaseTest() {
        setPlatformType();
        setServerType();
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    private File getAppFile(String app) throws URISyntaxException {
        URL resource = getClass()
                .getClassLoader()
                .getResource("apps/" + app);
        return Paths
                .get(Objects.requireNonNull(resource).toURI())
                .toFile();
    }

    private void setPlatformType() {
        String platformArg = System.getProperty("platformType");
        if (platformArg == null) {
            platformArg = "ios";
        }
        if (platformArg.equalsIgnoreCase(PlatformType.ANDROID.name())) {
            platform = PlatformType.ANDROID;
        } else {
            platform = PlatformType.IOS;
        }
    }

    private void setServerType() {
        String serverArg = System.getProperty("serverType");
        if (serverArg == null) {
            serverArg = "local";
        }
        if (serverArg.equalsIgnoreCase(ServerType.SAUCE.name())) {
            if (username == null || accessKey == null) {
                System.out.println("Username and access key were not set; running locally");
                server = ServerType.LOCAL;
                return;
            }
            server = ServerType.SAUCE;
        } else {
            server = ServerType.LOCAL;
        }
    }

    @Before
    public void setUp() throws IOException, URISyntaxException {
        File app = getAppFile("TheApp-v1.2.1.app.zip");
        if (platform == PlatformType.ANDROID) {
            app = getAppFile("TheApp-v1.2.1.apk");
        }
        String appStr = app.getAbsolutePath();
        URL serverUrl = new URL(localAppiumUrl);

        if (server == ServerType.SAUCE) {
            serverUrl = new URL("http://" + username + ":" + accessKey + sauceServer);
            sauceAPI.uploadFile(app);
            appStr = "sauce-storage:" + app.getName();
        }

        DriverFactory driverFactory = new DriverFactory(platform, server, appStr, serverUrl, name.getMethodName());
        driver = driverFactory.getDriver();
        sessionId = driver.getSessionId().toString();

        mainView = new MainView(driver);
        loginView = new LoginView(driver);
        loggedInView = new LoggedInView(driver);
    }

    @After
    public void tearDown() {
        try {
            driver.quit();
        } catch (Exception ignore) {
        }
    }
}