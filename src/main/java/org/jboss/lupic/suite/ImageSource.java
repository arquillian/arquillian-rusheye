package org.jboss.lupic.suite;

import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import org.w3c.dom.Element;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImageSource", propOrder = { "any" })
@XmlSeeAlso({ Mask.class, Pattern.class })
public abstract class ImageSource extends Properties {

    // @XmlAnyElement(lax = true)
    // protected List<Element> any;
    @XmlAttribute
    protected String source;

    FutureTask<BufferedImage> future = new FutureTask<BufferedImage>(new Callable<BufferedImage>() {
        public BufferedImage call() throws Exception {
            return retrieve();
        };
    });

    /*
     * accessors
     */
    // public List<Element> getAny() {
    // if (any == null) {
    // any = new ArrayList<Element>();
    // }
    // return this.any;
    // }

    public String getSource() {
        return source;
    }

    public void setSource(String value) {
        this.source = value;
    }

    /*
     * logic
     */
    public String getProperty(final String key) {
        return Collections2.filter(any, new Predicate<Element>() {
            @Override
            public boolean apply(Element element) {
                return element.getLocalName().equals(key);
            }
        }).iterator().next().getTextContent();
    }

    public void run() {
        future.run();
    }

    public abstract BufferedImage retrieve() throws Exception;

    public BufferedImage get() throws ExecutionException, InterruptedException {
        return future.get();
    }
}
