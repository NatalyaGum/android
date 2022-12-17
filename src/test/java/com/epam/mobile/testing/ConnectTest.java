package com.epam.mobile.testing;

import com.epam.mobile.testing.pages.ConnectPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ConnectTest extends BaseTest {
    public static final String EMAIL = "natalya_gumen@epam.com";
    public static final String PASSWORD = "123";

    @Test
    public void SigninErrorTest() throws InterruptedException {
        ConnectPage page = new ConnectPage();
        Boolean checkError = page.skipIntro()
                .signIn()
                .enterEmail(EMAIL)
                .continueToSignin()
                .enterPassword(PASSWORD)
                .goAhead()
                .checkPasswordError();
        Assert.assertTrue(checkError);
    }
}
