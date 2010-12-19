package org.jboss.lupic.suite;

import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ImageSource")
@XmlSeeAlso({ Mask.class, Pattern.class })
public abstract class ImageSource extends Properties {

    protected String source;

    @XmlTransient
    FutureTask<BufferedImage> future = new FutureTask<BufferedImage>(new Callable<BufferedImage>() {
        public BufferedImage call() throws Exception {
            return retrieve();
        };
    });

    @XmlAttribute
    public String getSource() {
        return source;
    }

    public void setSource(String value) {
        this.source = value;
    }

    public void run() {
        future.run();
    }

    public abstract BufferedImage retrieve() throws Exception;

    public BufferedImage get() throws ExecutionException, InterruptedException {
        return future.get();
    }
}
