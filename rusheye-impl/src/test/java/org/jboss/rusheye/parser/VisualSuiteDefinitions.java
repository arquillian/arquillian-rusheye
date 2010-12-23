/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.rusheye.parser;

import org.dom4j.Namespace;
import org.dom4j.QName;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public final class VisualSuiteDefinitions {

    public static final Namespace LUPIC_NS = new Namespace("", "http://www.jboss.org/test/visual-suite");

    public static final QName VISUAL_SUITE = new QName("visual-suite", LUPIC_NS);
    public static final QName GLOBAL_CONFIGURATION = new QName("global-configuration", LUPIC_NS);
    public static final QName PATTERN_RETRIEVER = new QName("pattern-retriever", LUPIC_NS);
    public static final QName MASK_RETRIEVER = new QName("mask-retriever", LUPIC_NS);
    public static final QName SAMPLE_RETRIEVER = new QName("sample-retriever", LUPIC_NS);
    public static final QName PERCEPTION = new QName("perception", LUPIC_NS);
    public static final QName MASKS = new QName("masks", LUPIC_NS);
    public static final QName TEST = new QName("test", LUPIC_NS);
    public static final QName PATTERN = new QName("pattern", LUPIC_NS);

    public static final QName ONE_PIXEL_TRESHOLD = new QName("one-pixel-treshold", LUPIC_NS);
    public static final QName GLOBAL_DIFFERENCE_TRESHOLD = new QName("global-difference-treshold", LUPIC_NS);
    public static final QName GLOBAL_DIFFERENCE_PIXEL_AMOUNT = new QName("global-difference-pixel-amount", LUPIC_NS);

    public static final QName MASK = new QName("mask", LUPIC_NS);

    public static final QName LISTENER = new QName("listener", LUPIC_NS);

    private VisualSuiteDefinitions() {
    }
}
