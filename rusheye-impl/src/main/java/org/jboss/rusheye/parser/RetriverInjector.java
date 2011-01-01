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

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.Unmarshaller;

import org.jboss.rusheye.exception.ParsingException;
import org.jboss.rusheye.retriever.MaskRetriever;
import org.jboss.rusheye.retriever.PatternRetriever;
import org.jboss.rusheye.retriever.Retriever;
import org.jboss.rusheye.retriever.SampleRetriever;
import org.jboss.rusheye.suite.GlobalConfiguration;
import org.jboss.rusheye.suite.Test;

class RetriverInjector extends Unmarshaller.Listener {
    private Parser parser;
    private Map<Class<?>, List<Field>> fieldMap = new HashMap<Class<?>, List<Field>>();

    RetriverInjector(Parser parser) {
        this.parser = parser;
    }

    @Override
    public void afterUnmarshal(Object target, Object parent) {
        final Class<?> targetClass = target.getClass();

        List<Field> fields;

        if (target instanceof Test) {
            Test test = (Test) target;
            afterUnmarshal(test.getSample(), test);
        }

        if (!fieldMap.containsKey(target.getClass())) {
            fields = new LinkedList<Field>();
            fieldMap.put(targetClass, fields);
            for (Field field : targetClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(Resource.class)) {
                    fields.add(field);
                }
            }
        } else {
            fields = fieldMap.get(targetClass);
        }

        for (Field field : fields) {
            boolean accessible = field.isAccessible();
            if (!accessible) {
                field.setAccessible(true);
            }
            Retriever retriever = getRetriever(field.getType());
            try {
                field.set(target, retriever);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
            if (!accessible) {
                field.setAccessible(accessible);
            }
        }
    }

    private Retriever getRetriever(Class<?> type) {
        GlobalConfiguration globalConfiguration = this.parser.getHandler().getVisualSuite().getGlobalConfiguration();

        if (type == MaskRetriever.class) {
            return globalConfiguration.getMaskRetriever();
        } else if (type == PatternRetriever.class) {
            return globalConfiguration.getPatternRetriever();
        } else if (type == SampleRetriever.class) {
            return globalConfiguration.getSampleRetriever();
        }

        throw new ParsingException();
    }
}