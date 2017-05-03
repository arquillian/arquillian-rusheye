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
package org.arquillian.rusheye.internal;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Satisfies filtering of collection.
 *
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public final class FilterCollection {

    private FilterCollection() {
    }

    /**
     * Returns the collection with the same elements like given collection but only those, which returns true for
     * application of given predicate.
     *
     * @param <E>
     *     the element type of the collection
     * @param collection
     *     the collection of elements
     * @param predicate
     *     the predicate to filter by
     *
     * @return the collection with only those elements from given collection, which returns true for application of
     * given predicate
     */
    public static <E> Collection<E> filter(Collection<E> collection, Predicate<E> predicate) {
        return new FilteredCollection<E>(collection, predicate);
    }

    /**
     * Collection filtered by predicate.
     *
     * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
     * @version $Revision$
     */
    private static class FilteredCollection<E> extends AbstractCollection<E> {
        private Collection<E> originalCollection;
        private Predicate<E> predicate;

        public FilteredCollection(Collection<E> originalCollection, Predicate<E> predicate) {
            this.originalCollection = originalCollection;
            this.predicate = predicate;
        }

        @Override
        public Iterator<E> iterator() {
            return new FilteredIterator<E>(originalCollection.iterator(), predicate);
        }

        @Override
        public int size() {
            int count = 0;
            for (@SuppressWarnings("unused")
                E element : this) {
                count++;
            }
            return count;
        }
    }

    /**
     * Iterator filtering by predicate.
     *
     * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
     * @version $Revision$
     */
    private static class FilteredIterator<E> implements Iterator<E> {

        private Iterator<E> originalIterator;
        private Predicate<E> predicate;
        private E next;

        public FilteredIterator(Iterator<E> originalIterator, Predicate<E> predicate) {
            this.originalIterator = originalIterator;
            this.predicate = predicate;
        }

        public boolean hasNext() {
            if (next != null) {
                return true;
            }
            while (originalIterator.hasNext()) {
                next = originalIterator.next();
                if (predicate.apply(next)) {
                    return true;
                } else {
                    next = null;
                }
            }
            return false;
        }

        public E next() {
            if (hasNext()) {
                E result = next;
                next = null;
                return result;
            } else {
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
