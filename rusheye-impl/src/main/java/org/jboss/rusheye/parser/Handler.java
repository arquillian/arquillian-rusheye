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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedHashSet;
import java.util.Set;

import org.jboss.rusheye.parser.listener.ParserListener;
import org.jboss.rusheye.suite.VisualSuite;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class Handler {

    private Context context = new ListeningContext();
    private VisualSuite visualSuite;
    private Set<ParserListener> parserListeners;

    public Handler(Set<ParserListener> parserListeners) {
        this.parserListeners = parserListeners;
    }

    private class ListeningContext extends Context implements InvocationHandler {

        ParserListener wrappedListener = (ParserListener) Proxy.newProxyInstance(Handler.this.getClass()
            .getClassLoader(), new Class<?>[] { ParserListener.class }, this);

        @Override
        public ParserListener invokeListeners() {
            return wrappedListener;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Set<ParserListener> listeners = new LinkedHashSet<ParserListener>(parserListeners);
            for (ParserListener listener : listeners) {
                Method wrappedMethod = listener.getClass().getMethod(method.getName(), method.getParameterTypes());
                try {
                    wrappedMethod.invoke(listener, args);
                } catch (InvocationTargetException e) {
                    if (e.getCause() instanceof RuntimeException) {
                        throw (RuntimeException) e.getCause();
                    } else {
                        throw new RuntimeException(e.getCause());
                    }
                } catch (Exception e) {
                    throw new IllegalStateException("unexpected invocation exception: " + e.getMessage(), e);
                }
            }

            return null;
        }
    }

    public VisualSuite getVisualSuite() {
        return visualSuite;
    }

    public void setVisualSuite(VisualSuite visualSuite) {
        this.visualSuite = visualSuite;
    }

    Context getContext() {
        return context;
    }
}
