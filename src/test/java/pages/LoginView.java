package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.WithTimeout;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.temporal.ChronoUnit;

public class LoginView {
    @WithTimeout(time = 5, chronoUnit = ChronoUnit.SECONDS)
    @FindBy(xpath = "//android.widget.EditText[@content-desc=\"username\"]")
    private WebElement usernameField;

    @FindBy(xpath = "//android.widget.EditText[@content-desc=\"password\"]")
    private WebElement passwordField;

    @AndroidFindBy(accessibility = "loginBtn")
    private WebElement loginBtn;

    public LoginView(AppiumDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
}
    public void login(String username, String password) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginBtn.click();
    }
}