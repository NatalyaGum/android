package com.epam.mobile.testing.configuration;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

import static io.appium.java_client.remote.AndroidMobileCapabilityType.*;
import static io.appium.java_client.remote.MobileCapabilityType.*;

public class CapabilitiesConfigurator {

    private CapabilitiesConfigurator() {
    }

    public static DesiredCapabilities getLocalCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(UDID, ConfigurationReader.getInstance().udid());
        capabilities.setCapability(AVD, ConfigurationReader.getInstance().localDeviceName());
        capabilities.setCapability(APP_PACKAGE, ConfigurationReader.getInstance().appPackage());
        capabilities.setCapability(APP_ACTIVITY, ConfigurationReader.getInstance().appActivity());
        capabilities.setCapability(APP, new File(ConfigurationReader.getInstance().appPath()).getAbsolutePath());
        return capabilities;
    }
}
