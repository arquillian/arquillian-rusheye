package org.jboss.rusheye.arquillian;

import org.jboss.arquillian.core.spi.LoadableExtension;
import org.jboss.rusheye.arquillian.configuration.RusheyeConfigurator;
import org.jboss.rusheye.arquillian.observer.CrawlObserver;
import org.jboss.rusheye.arquillian.observer.ParseObserver;

/**
 *
 * @author jhuska
 */


public class RusheyeExtension implements LoadableExtension {

    @Override
    public void register(ExtensionBuilder builder) {
        builder.observer(RusheyeConfigurator.class);
        builder.observer(CrawlObserver.class);
        builder.observer(ParseObserver.class);
    }
    
}