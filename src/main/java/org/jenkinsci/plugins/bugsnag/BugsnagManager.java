package org.jenkinsci.plugins.bugsnag;

import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

import com.bugsnag.Client;
import com.bugsnag.ExceptionHandler;

public class BugsnagManager {
    private static final Logger LOGGER = Logger.getLogger(BugsnagManager.class
            .getName());

    private static BugsnagManager instance = null;

    private Client bugsnag = null;

    private BugsnagManager() {
        super();
    }

    public static BugsnagManager getInstance() {
        if (instance == null) {
            instance = new BugsnagManager();
        }
        return instance;
    }

    public void register(String apiKey) {
        if (this.bugsnag != null) {
            ExceptionHandler.remove();
            LOGGER.info("Bugsnag exception handler has unregistered. ("
                    + apiKey + ")");
        }

        if (StringUtils.isBlank(apiKey)) {
            return;
        }

        this.bugsnag = new Client(apiKey);
        LOGGER.info("Bugsnag exception handler has registered. (" + apiKey
                + ")");
    }

}
