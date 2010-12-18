package org.jboss.lupic.parser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

import org.jboss.lupic.suite.Configuration;
import org.jboss.lupic.suite.Mask;
import org.jboss.lupic.suite.Perception;

public class ConfigurationCompiler extends Configuration {

    private final static Configuration DEFAULT_CONFIGURATION = new DefaultConfiguration();

    private Deque<Configuration> customConfigurations = new LinkedList<Configuration>();

    {
        customConfigurations.push(DEFAULT_CONFIGURATION);
    }
    
    public void pushConfiguration(Configuration configuration) {
        customConfigurations.push(configuration);
    }

    @Override
    public Perception getPerception() {
        return new PerceptionCompiler().getCompiledPerception();
    }

    @Override
    public void setPerception(Perception value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Mask> getMasks() {
        List<Mask> masks = new LinkedList<Mask>();
        for (Configuration configuration : customConfigurations) {
            if (configuration.getMasks() != null && !configuration.getMasks().isEmpty()) {
                masks.addAll(configuration.getMasks());
            }
        }
        return masks;
    }

    public class PerceptionCompiler implements MethodHandler {

        public Perception getCompiledPerception() {
            ProxyFactory f = new ProxyFactory();
            f.setSuperclass(Perception.class);
            Class<?> c = f.createClass();
            Perception perception;
            try {
                perception = (Perception) c.newInstance();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
            ((ProxyObject) perception).setHandler(this);
            return perception;
        }

        @Override
        public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
            final String methodName = thisMethod.getName();
            
            if (methodName.startsWith("get")) {
                for (Configuration configuration : customConfigurations) {
                    Perception perception = configuration.getPerception();
                    if (perception != null) {
                        Object result;
                        try {
                            result = thisMethod.invoke(perception, args);
                        } catch (InvocationTargetException e) {
                            throw e.getTargetException();
                        }
                        if (result != null) {
                            return result;
                        }
                    }
                }
                return null;
            }

            throw new UnsupportedOperationException();
        }
    }
}
