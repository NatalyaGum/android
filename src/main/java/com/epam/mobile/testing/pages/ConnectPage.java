package com.epam.mobile.testing.pages;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ConnectPage extends BasePage {

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@resource-id='onBoarding-btn-skip']")
    private MobileElement skipBtn;
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@resource-id='login-btn-signUp']")
    private MobileElement signInBtn;
    @AndroidFindBy(xpath = "//android.widget.EditText[@resource-id='username']")
    private MobileElement userNameInput;

    @AndroidFindBy(xpath = "//android.widget.Button[@resource-id='kc-login-next']")
    private MobileElement continueBtn;

    @AndroidFindBy(xpath = "//android.widget.Button[@resource-id='idSIButton9']")
    private MobileElement nextBtn;

    @AndroidFindBy(xpath = "//android.widget.EditText[@resource-id='i0118']")
    private MobileElement passwordInput;

    @AndroidFindBy(xpath = "//android.view.View[@resource-id='passwordError']")
    private MobileElement passwordError;

    public ConnectPage skipIntro() {
        skipBtn.click();
        return this;
    }

    public ConnectPage signIn() {
        signInBtn.click();
        return this;
    }

    public ConnectPage continueToSignin() {
        continueBtn.click();
        return this;
    }

    public ConnectPage goAhead() {
        nextBtn.click();
        return this;
    }

    public ConnectPage enterEmail(String email) {
        userNameInput.sendKeys(email);
        return this;
    }

    public ConnectPage enterPassword(String password) {
        passwordInput.sendKeys(password);
        return this;
    }

    public boolean checkPasswordError() {
        return passwordError.isDisplayed();
    }
}
