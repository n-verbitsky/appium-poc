package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.WithTimeout;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.temporal.ChronoUnit;

public class LoggedInView {
    @WithTimeout(time = 5, chronoUnit = ChronoUnit.SECONDS)
    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text, 'You are logged in as')]")
    private WebElement loggedInMsg;
    public LoggedInView(AppiumDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    public String getLoggedInUsername() {
        String text = loggedInMsg.getText();
        return text.replaceAll(".*You are logged in as ([^ ]+).*", "$1");
    }
}