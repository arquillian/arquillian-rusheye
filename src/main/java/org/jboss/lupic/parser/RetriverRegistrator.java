package org.jboss.lupic.parser;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.Unmarshaller;

import org.jboss.lupic.exception.ParserError;
import org.jboss.lupic.retriever.MaskRetriever;
import org.jboss.lupic.retriever.PatternRetriever;
import org.jboss.lupic.retriever.Retriever;
import org.jboss.lupic.retriever.sample.SampleRetriever;
import org.jboss.lupic.suite.GlobalConfiguration;

class RetriverRegistrator extends Unmarshaller.Listener {

    /**
     * 
     */
    private Parser parser;

    /**
     * @param parser
     */
    RetriverRegistrator(Parser parser) {
        this.parser = parser;
    }

    Map<Class<?>, List<Field>> fieldMap = new HashMap<Class<?>, List<Field>>();

    @Override
    public void afterUnmarshal(Object target, Object parent) {
        final Class<?> targetClass = target.getClass();

        List<Field> fields;

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
        GlobalConfiguration globalConfiguration = this.parser.handler.getVisualSuite().getGlobalConfiguration();

        if (type == MaskRetriever.class) {
            return globalConfiguration.getMaskRetriever();
        } else if (type == PatternRetriever.class) {
            return globalConfiguration.getPatternRetriever();
        } else if (type == SampleRetriever.class) {
            return globalConfiguration.getSampleRetriever();
        }

        throw new ParserError();
    }

}