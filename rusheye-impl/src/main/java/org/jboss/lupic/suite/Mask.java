package org.jboss.lupic.suite;

import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jboss.lupic.retriever.MaskRetriever;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "Mask")
public class Mask extends ImageSource {

    protected String id;
    protected MaskType type;
    protected VerticalAlign verticalAlign;
    protected HorizontalAlign horizontalAlign;

    @Resource
    @XmlTransient
    public MaskRetriever maskRetriever;

    /*
     * accessors
     */
    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "Name")
    public String getId() {
        return id;
    }

    public void setId(String value) {
        this.id = value;
    }

    @XmlAttribute(required = true)
    public MaskType getType() {
        return type;
    }

    public void setType(MaskType value) {
        this.type = value;
    }

    @XmlAttribute(name = "vertical-align")
    public VerticalAlign getVerticalAlign() {
        return verticalAlign;
    }

    public void setVerticalAlign(VerticalAlign value) {
        this.verticalAlign = value;
    }

    @XmlAttribute(name = "horizontal-align")
    public HorizontalAlign getHorizontalAlign() {
        return horizontalAlign;
    }

    public void setHorizontalAlign(HorizontalAlign value) {
        this.horizontalAlign = value;
    }

    /*
     * logic
     */
    @Override
    public BufferedImage retrieve() throws Exception {
        return maskRetriever.retrieve(source, this);
    }

    private BufferedImage getMask() {
        try {
            return get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isPixelMasked(BufferedImage pattern, int x, int y) {
        final BufferedImage mask = getMask();

        int patternWidth = pattern.getWidth();
        int patternHeight = pattern.getHeight();
        int maskWidth = mask.getWidth();
        int maskHeight = mask.getHeight();

        int maskX = this.horizontalAlign == HorizontalAlign.LEFT ? x : x - (patternWidth - maskWidth);
        int maskY = this.verticalAlign == VerticalAlign.TOP ? y : y - (patternHeight - maskHeight);

        // we are outside the mask
        if (maskX < 0 || maskX >= maskWidth || maskY < 0 || maskY >= maskHeight) {
            return false;
        }

        return (mask.getRGB(maskX, maskY) & 0xff000000) != 0;
    }

}
