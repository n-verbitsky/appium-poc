import org.junit.Assert;
import org.junit.Test;

public class LoginTest extends BaseTest {

    @Test
    public void testLogin() {
        final String username = "alice";
        final String password = "mypassword";

        mainView.navToLogin();
        loginView.login(username, password);
        String loggedInUsername = loggedInView.getLoggedInUsername();
        Assert.assertEquals(loggedInUsername, username);
    }
}