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
package org.jboss.rusheye.suite.utils;

import static java.lang.reflect.Modifier.isFinal;
import static java.lang.reflect.Modifier.isPrivate;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

import org.apache.commons.lang.ArrayUtils;
import org.jboss.rusheye.suite.annotations.Nullify;

public final class NullingProxy {

    private NullingProxy() {
    }

    public static <T> T handle(T instance, Class<? extends Annotation> nullified) throws InstantiationException,
        IllegalAccessException {

        Class<?> originalClass = (Class<?>) instance.getClass();

        if (instance instanceof ProxyObject) {
            originalClass = (Class<?>) originalClass.getSuperclass();
        }

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setSuperclass(originalClass);

        Class<?> proxyClass = proxyFactory.createClass();

        MethodHandler handler = new NullingHandler(instance, nullified);

        @SuppressWarnings("unchecked")
        T proxyInstance = (T) proxyClass.newInstance();
        ((ProxyObject) proxyInstance).setHandler(handler);

        return proxyInstance;
    }

    public static class NullingHandler implements InvocationHandler, MethodHandler {
        Object object;
        Class<? extends Annotation> nullified;

        public NullingHandler(Object object, Class<? extends Annotation> nullified) {
            this.object = object;
            this.nullified = nullified;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Nullify nullify = method.getAnnotation(Nullify.class);

            if (nullify != null) {
                if (ArrayUtils.contains(nullify.value(), nullified)) {
                    return null;
                }
            }

            Object result = method.invoke(object, args);

            if (result == null) {
                return result;
            } else if (method.getReturnType().isInterface()) {
                return Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { method.getReturnType() },
                    new NullingHandler(result, nullified));
            } else if (canBeProxied(result)) {
                return NullingProxy.handle(result, nullified);
            } else {
                return result;
            }
        }

        private boolean canBeProxied(Object object) {
            Class<?> type = object.getClass();
            int mod = type.getModifiers();
            return !(isFinal(mod) || isPrivate(mod) || !hasNoParametricConstructor(type));
        }

        private boolean hasNoParametricConstructor(Class<?> type) {
            try {
                type.getConstructor();
                return true;
            } catch (SecurityException e) {
                throw new IllegalStateException(e);
            } catch (NoSuchMethodException e) {
                return false;
            }
        }

        @Override
        public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
            return invoke(self, thisMethod, args);
        }
    }
}
