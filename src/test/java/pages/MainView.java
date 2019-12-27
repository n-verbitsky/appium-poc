package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.WithTimeout;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class MainView {
    @WithTimeout(time = 5000)
    @AndroidFindBy(accessibility = "Login Screen")
    private WebElement navToLoginBtn;
    public MainView(AppiumDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    public void navToLogin() {
        navToLoginBtn.click();
    }
}