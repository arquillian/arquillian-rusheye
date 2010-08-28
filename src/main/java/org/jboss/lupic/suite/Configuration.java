package org.jboss.lupic.suite;

import java.util.LinkedHashSet;
import java.util.Set;

public class Configuration {
    Perception perception = new Perception();
    Set<Mask> ignoreBitmapMasks = new LinkedHashSet<Mask>();
    Set<Mask> selectiveAlphaMasks = new LinkedHashSet<Mask>();

    public Perception getPerception() {
        return perception;
    }

    public void setPerception(Perception perception) {
        this.perception = perception;
    }

    public Set<Mask> getIgnoreBitmapMasks() {
        return ignoreBitmapMasks;
    }

    public void setIgnoreBitmapMasks(Set<Mask> ignoreBitmapMasks) {
        this.ignoreBitmapMasks = ignoreBitmapMasks;
    }

    public Set<Mask> getSelectiveAlphaMasks() {
        return selectiveAlphaMasks;
    }

    public void setSelectiveAlphaMasks(Set<Mask> selectiveAlphaMasks) {
        this.selectiveAlphaMasks = selectiveAlphaMasks;
    }

    public void setDefaultValuesForUnset() {
        perception.setDefaultValuesForUnset();
    }

    public void setValuesFromParent(Configuration parent) {
        perception.setValuesFromParent(parent.perception);
        ignoreBitmapMasks.addAll(parent.ignoreBitmapMasks);
        selectiveAlphaMasks.addAll(parent.selectiveAlphaMasks);
    }
}
