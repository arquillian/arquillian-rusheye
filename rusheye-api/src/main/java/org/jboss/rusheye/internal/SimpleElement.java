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
package org.jboss.rusheye.internal;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;

/**
 * The simple element containing localName and textContext for purposes of {@link org.jboss.rusheye.suite.Properties}
 * implementation.
 * 
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class SimpleElement implements Element {

    private String localName;
    private String textContent;

    public SimpleElement(String localName) {
        this.localName = localName;
    }

    public String getNodeName() {

        return null;
    }

    public String getNodeValue() throws DOMException {

        return null;
    }

    public void setNodeValue(String nodeValue) throws DOMException {

    }

    public short getNodeType() {

        return 0;
    }

    public Node getParentNode() {

        return null;
    }

    public NodeList getChildNodes() {

        return null;
    }

    public Node getFirstChild() {

        return null;
    }

    public Node getLastChild() {

        return null;
    }

    public Node getPreviousSibling() {

        return null;
    }

    public Node getNextSibling() {

        return null;
    }

    public NamedNodeMap getAttributes() {

        return null;
    }

    public Document getOwnerDocument() {

        return null;
    }

    public Node insertBefore(Node newChild, Node refChild) throws DOMException {

        return null;
    }

    public Node replaceChild(Node newChild, Node oldChild) throws DOMException {

        return null;
    }

    public Node removeChild(Node oldChild) throws DOMException {

        return null;
    }

    public Node appendChild(Node newChild) throws DOMException {

        return null;
    }

    public boolean hasChildNodes() {

        return false;
    }

    public Node cloneNode(boolean deep) {

        return null;
    }

    public void normalize() {

    }

    public boolean isSupported(String feature, String version) {

        return false;
    }

    public String getNamespaceURI() {

        return null;
    }

    public String getPrefix() {

        return null;
    }

    public void setPrefix(String prefix) throws DOMException {

    }

    public String getLocalName() {
        return localName;
    }

    public boolean hasAttributes() {

        return false;
    }

    public String getBaseURI() {

        return null;
    }

    public short compareDocumentPosition(Node other) throws DOMException {

        return 0;
    }

    public String getTextContent() throws DOMException {
        return textContent;
    }

    public void setTextContent(String textContent) throws DOMException {
        this.textContent = textContent;
    }

    public boolean isSameNode(Node other) {

        return false;
    }

    public String lookupPrefix(String namespaceURI) {

        return null;
    }

    public boolean isDefaultNamespace(String namespaceURI) {

        return false;
    }

    public String lookupNamespaceURI(String prefix) {

        return null;
    }

    public boolean isEqualNode(Node arg) {

        return false;
    }

    public Object getFeature(String feature, String version) {

        return null;
    }

    public Object setUserData(String key, Object data, UserDataHandler handler) {

        return null;
    }

    public Object getUserData(String key) {

        return null;
    }

    public String getTagName() {

        return null;
    }

    public String getAttribute(String name) {

        return null;
    }

    public void setAttribute(String name, String value) throws DOMException {

    }

    public void removeAttribute(String name) throws DOMException {

    }

    public Attr getAttributeNode(String name) {

        return null;
    }

    public Attr setAttributeNode(Attr newAttr) throws DOMException {

        return null;
    }

    public Attr removeAttributeNode(Attr oldAttr) throws DOMException {

        return null;
    }

    public NodeList getElementsByTagName(String name) {

        return null;
    }

    public String getAttributeNS(String namespaceURI, String localName) throws DOMException {

        return null;
    }

    public void setAttributeNS(String namespaceURI, String qualifiedName, String value) throws DOMException {

    }

    public void removeAttributeNS(String namespaceURI, String localName) throws DOMException {

    }

    public Attr getAttributeNodeNS(String namespaceURI, String localName) throws DOMException {

        return null;
    }

    public Attr setAttributeNodeNS(Attr newAttr) throws DOMException {

        return null;
    }

    public NodeList getElementsByTagNameNS(String namespaceURI, String localName) throws DOMException {

        return null;
    }

    public boolean hasAttribute(String name) {

        return false;
    }

    public boolean hasAttributeNS(String namespaceURI, String localName) throws DOMException {

        return false;
    }

    public TypeInfo getSchemaTypeInfo() {

        return null;
    }

    public void setIdAttribute(String name, boolean isId) throws DOMException {

    }

    public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) throws DOMException {

    }

    public void setIdAttributeNode(Attr idAttr, boolean isId) throws DOMException {

    }
}
