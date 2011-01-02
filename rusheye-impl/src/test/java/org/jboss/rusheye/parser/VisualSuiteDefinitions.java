/**
 * JBoss, Home of Professional Open Source
 * Copyright ${year}, Red Hat, Inc. and individual contributors
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
import org.jboss.rusheye.RushEye;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public final class VisualSuiteDefinitions {

    public static final Namespace RUSHEYE_NS = new Namespace("", RushEye.NAMESPACE_VISUAL_SUITE);

    public static final QName VISUAL_SUITE = new QName("visual-suite", RUSHEYE_NS);
    public static final QName GLOBAL_CONFIGURATION = new QName("global-configuration", RUSHEYE_NS);
    public static final QName PATTERN_RETRIEVER = new QName("pattern-retriever", RUSHEYE_NS);
    public static final QName MASK_RETRIEVER = new QName("mask-retriever", RUSHEYE_NS);
    public static final QName SAMPLE_RETRIEVER = new QName("sample-retriever", RUSHEYE_NS);
    public static final QName PERCEPTION = new QName("perception", RUSHEYE_NS);
    public static final QName MASKS = new QName("masks", RUSHEYE_NS);
    public static final QName TEST = new QName("test", RUSHEYE_NS);
    public static final QName PATTERN = new QName("pattern", RUSHEYE_NS);

    public static final QName ONE_PIXEL_TRESHOLD = new QName("one-pixel-treshold", RUSHEYE_NS);
    public static final QName GLOBAL_DIFFERENCE_TRESHOLD = new QName("global-difference-treshold", RUSHEYE_NS);
    public static final QName GLOBAL_DIFFERENCE_PIXEL_AMOUNT = new QName("global-difference-pixel-amount", RUSHEYE_NS);

    public static final QName MASK = new QName("mask", RUSHEYE_NS);

    public static final QName LISTENER = new QName("listener", RUSHEYE_NS);

    private VisualSuiteDefinitions() {
    }
}
