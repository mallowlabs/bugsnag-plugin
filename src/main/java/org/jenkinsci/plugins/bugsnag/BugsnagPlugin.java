package org.jenkinsci.plugins.bugsnag;

import hudson.Extension;
import hudson.ExtensionPoint;
import hudson.init.InitMilestone;
import hudson.init.Initializer;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.model.Hudson;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

public class BugsnagPlugin implements Describable<BugsnagPlugin>,
        ExtensionPoint {

    public Descriptor<BugsnagPlugin> getDescriptor() {
        return (BugsnagPluginDescriptor) Hudson.getInstance()
                .getDescriptorOrDie(getClass());
    }

    @Initializer(before = InitMilestone.COMPLETED)
    public static void init() {
        BugsnagPluginDescriptor desc = Hudson.getInstance()
                .getDescriptorByType(BugsnagPluginDescriptor.class);
        if (desc != null) {
            BugsnagManager.getInstance().register(desc.getApiKey());
        }
    }

    @Extension
    public static class BugsnagPluginDescriptor extends
            Descriptor<BugsnagPlugin> {

        private String apiKey;

        public BugsnagPluginDescriptor() {
            this(StringUtils.EMPTY);
        }

        @DataBoundConstructor
        public BugsnagPluginDescriptor(String apiKey) {
            this.apiKey = apiKey;
            load();
        }

        public String getApiKey() {
            return this.apiKey;
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject json)
                throws hudson.model.Descriptor.FormException {
            this.apiKey = json.getString("apiKey");
            BugsnagManager.getInstance().register(apiKey);
            save();
            return super.configure(req, json);
        }

        @Override
        public String getDisplayName() {
            return "Bugsnag";
        }
    }

}
