package org.jboss.rusheye.parser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

import org.jboss.rusheye.suite.Configuration;
import org.jboss.rusheye.suite.Mask;
import org.jboss.rusheye.suite.Perception;

public class ConfigurationCompiler extends Configuration {

    private static final Configuration DEFAULT_CONFIGURATION = new DefaultConfiguration();

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

    @SuppressWarnings("unchecked")
    public static <T extends Configuration> T wrap(final T configuration, final Configuration... configurations) {
        ProxyFactory f = new ProxyFactory();
        f.setSuperclass(configuration.getClass());
        Class<?> c = f.createClass();
        T proxy;
        try {
            proxy = (T) c.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        ((ProxyObject) proxy).setHandler(new MethodHandler() {

            Object instance = configuration;
            ConfigurationCompiler configurationCompiler = new ConfigurationCompiler();

            {
                for (Configuration conf : configurations) {
                    configurationCompiler.pushConfiguration(conf);
                }
                configurationCompiler.pushConfiguration(configuration);
            }

            @Override
            public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
                final Class<?> declaringClass = thisMethod.getDeclaringClass();

                if (declaringClass == Configuration.class) {
                    Method wrappedMethod = ConfigurationCompiler.class.getMethod(thisMethod.getName(),
                        thisMethod.getParameterTypes());
                    return wrappedMethod.invoke(configurationCompiler, args);
                }

                return thisMethod.invoke(instance, args);
            }
        });
        return proxy;
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
            final Class<?> declaringClass = thisMethod.getDeclaringClass();

            if (declaringClass == Perception.class) {
                if (methodName.startsWith("getGlobalDifferencePixelAmount")
                    || methodName.startsWith("getGlobalDifferencePercentage")) {
                    Object result = proceed.invoke(self, args);
                    return result;
                } else if (methodName.startsWith("get")) {
                    Object result = evaluate(thisMethod, args);
                    return result;
                }
            }

            throw new UnsupportedOperationException();
        }

        Object evaluate(Method thisMethod, Object[] args) throws Throwable {
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
    }
}
