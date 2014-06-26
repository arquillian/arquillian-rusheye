package org.jboss.rusheye.arquillian.configuration;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jboss.arquillian.config.descriptor.api.ArquillianDescriptor;
import org.jboss.arquillian.config.descriptor.api.ExtensionDef;
import org.jboss.arquillian.core.api.Event;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.event.suite.BeforeSuite;

/**
 *
 * @author jhuska
 */
public class RusheyeConfigurator {
    
    private static final Logger LOGGER = Logger.getLogger(RusheyeConfigurator.class.getSimpleName());
    private static final String EXTENSION_NAME = "rusheye";
    
    @Inject
    @ApplicationScoped
    private InstanceProducer<RusheyeConfiguration> configuration;
    
    @Inject
    private Event<RusheyeExtensionConfigured> extensionConfiguredEvent;
    
    public void configureExtension(@Observes BeforeSuite event, ArquillianDescriptor descriptor) {
        RusheyeConfiguration conf = new RusheyeConfiguration();

        for (ExtensionDef extension : descriptor.getExtensions()) {
            if (extension.getExtensionName().equals(EXTENSION_NAME)) {
                conf.setConfiguration(extension.getExtensionProperties());
                conf.validate();
                break;
            }
        }

        this.configuration.set(conf);

        if (LOGGER.isLoggable(Level.INFO)) {
            System.out.println("Configuration of Arquillian Rusheye:");
            System.out.println(this.configuration.get().toString());
        }

        extensionConfiguredEvent.fire(new RusheyeExtensionConfigured());
    }

    
}
