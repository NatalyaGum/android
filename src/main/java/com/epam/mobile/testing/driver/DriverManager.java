package com.epam.mobile.testing.driver;

import com.epam.mobile.testing.configuration.AddressConfigurator;
import com.epam.mobile.testing.configuration.CapabilitiesConfigurator;
import com.epam.mobile.testing.configuration.ConfigurationReader;
import com.epam.mobile.testing.configuration.EnvironmentType;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;


public class DriverManager {

    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final EnvironmentType ENVIRONMENT_TYPE = EnvironmentType.valueOf(ConfigurationReader.getInstance().env().toUpperCase());
    private static AppiumDriver<MobileElement> driver;
    protected static final int WAIT_TIMEOUT_SECONDS = 120;

    public DriverManager() {
    }

    public static AppiumDriver<MobileElement> getDriver() {
        if (driver == null) {
            driver = createDriver();
        }
        return driver;
    }

    private static AppiumDriver<MobileElement> createDriver() {
        switch (ENVIRONMENT_TYPE) {
            case LOCAL:
                driver = new AndroidDriver<>(AddressConfigurator.getAppiumDriverLocalService(ConfigurationReader.getInstance().appiumPort()),
                        CapabilitiesConfigurator.getLocalCapabilities());
                driver.manage().timeouts().implicitlyWait(WAIT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
                break;
            default:
                throw new IllegalArgumentException(format("Unexpected environment value: %s", ENVIRONMENT_TYPE));
        }
        LOGGER.info("Driver is created!");
        LOGGER.info("Environment type is {}", ENVIRONMENT_TYPE);
        return driver;
    }

    public static void closeDriver() {
        Optional.ofNullable(getDriver()).ifPresent(driverInstance -> {
            driverInstance.quit();
            driver = null;
            LOGGER.info("Driver is closed!");
        });
    }

    public static void closeAppium() {
        AddressConfigurator.stopService();
    }

    public static void closeEmulator() {
        try {
            Runtime.getRuntime().exec(format("adb -s %s emu kill", ConfigurationReader.getInstance().udid()));
            LOGGER.info("AVD is closed!");
        } catch (IOException e) {
            LOGGER.info("AVD was not closed, message:{}", e.getMessage());
        }
    }
}
