package org.jboss.lupic.suite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "Configuration", propOrder = { "perception", "masks" })
@XmlSeeAlso({ GlobalConfiguration.class, Test.class })
public abstract class Configuration {

    protected Perception perception;
    protected List<Mask> masks;

    public Perception getPerception() {
        return perception;
    }

    public void setPerception(Perception value) {
        this.perception = value;
    }

    @XmlElement(name = "mask")
    public List<Mask> getMasks() {
        if (masks == null) {
            masks = new ArrayList<Mask>();
        }
        return this.masks;
    }

    /*
     * logic
     */
    @XmlTransient
    public final Collection<Mask> getSelectiveAlphaMasks() {
        return Collections2.filter(getMasks(), new Predicate<Mask>() {
            @Override
            public boolean apply(Mask mask) {
                return MaskType.SELECTIVE_ALPHA.equals(mask.getType());
            }
        });
    }

    @XmlTransient
    public final Collection<Mask> getIgnoreBitmapMasks() {
        return Collections2.filter(getMasks(), new Predicate<Mask>() {
            @Override
            public boolean apply(Mask mask) {
                return MaskType.IGNORE_BITMAP.equals(mask.getType());
            }
        });
    }
}
