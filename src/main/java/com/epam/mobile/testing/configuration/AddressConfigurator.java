package com.epam.mobile.testing.configuration;


import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Optional;

import static io.appium.java_client.service.local.flags.GeneralServerFlag.LOG_LEVEL;
import static io.appium.java_client.service.local.flags.GeneralServerFlag.SESSION_OVERRIDE;

public class AddressConfigurator {

    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String ERROR_LOG_LEVEL = "error";
    private static final String KILL_NODE_COMMAND = "taskkill /F /IM node.exe";
    private static AppiumDriverLocalService appiumDriverLocalService;

    private AddressConfigurator() {
    }

    public static AppiumDriverLocalService getAppiumDriverLocalService(int port) {
        if (appiumDriverLocalService == null) startService(port);
        return appiumDriverLocalService;
    }

    public static void startService(int port) {
        makePortAvailableIfOccupied(port);
        appiumDriverLocalService = new AppiumServiceBuilder()
                .withIPAddress(ConfigurationReader.getInstance().appiumAddress())
                .usingPort(port)
                .withArgument(SESSION_OVERRIDE)
                .withArgument(LOG_LEVEL, ERROR_LOG_LEVEL)
                .build();
        appiumDriverLocalService.start();
        LOGGER.info("Appium server started on the port{}", port);
    }

    public static void stopService() {
        Optional.ofNullable(appiumDriverLocalService).ifPresent(service -> {
            service.stop();
            LOGGER.info("Appium server stopped");
        });
    }

    private static void makePortAvailableIfOccupied(int port) {
        if (!isPortFree(port)) {
            try {
                Runtime.getRuntime().exec(KILL_NODE_COMMAND);
            } catch (IOException e) {
                LOGGER.error("Couldn't execute runtime command, message: {}", e.getMessage());
            }
        }
    }

    private static boolean isPortFree(int port) {
        boolean isFree = true;
        try (ServerSocket ignored = new ServerSocket(port)) {
            LOGGER.info("Specified port - {} is available and ready to use", port);
        } catch (Exception e) {
            isFree = false;
            LOGGER.warn("Specified port - {} is occupied by some process, process will be terminated", port);
        }
        return isFree;
    }
}
