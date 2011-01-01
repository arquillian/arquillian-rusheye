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

public class ElementAdapter implements Element {

    private String localName;
    private String textContent;

    public ElementAdapter(String localName) {
        this.localName = localName;
    }

    public String getNodeName() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getNodeValue() throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    public void setNodeValue(String nodeValue) throws DOMException {
        // TODO Auto-generated method stub

    }

    public short getNodeType() {
        // TODO Auto-generated method stub
        return 0;
    }

    public Node getParentNode() {
        // TODO Auto-generated method stub
        return null;
    }

    public NodeList getChildNodes() {
        // TODO Auto-generated method stub
        return null;
    }

    public Node getFirstChild() {
        // TODO Auto-generated method stub
        return null;
    }

    public Node getLastChild() {
        // TODO Auto-generated method stub
        return null;
    }

    public Node getPreviousSibling() {
        // TODO Auto-generated method stub
        return null;
    }

    public Node getNextSibling() {
        // TODO Auto-generated method stub
        return null;
    }

    public NamedNodeMap getAttributes() {
        // TODO Auto-generated method stub
        return null;
    }

    public Document getOwnerDocument() {
        // TODO Auto-generated method stub
        return null;
    }

    public Node insertBefore(Node newChild, Node refChild) throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    public Node removeChild(Node oldChild) throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    public Node appendChild(Node newChild) throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean hasChildNodes() {
        // TODO Auto-generated method stub
        return false;
    }

    public Node cloneNode(boolean deep) {
        // TODO Auto-generated method stub
        return null;
    }

    public void normalize() {
        // TODO Auto-generated method stub
    }

    public boolean isSupported(String feature, String version) {
        // TODO Auto-generated method stub
        return false;
    }

    public String getNamespaceURI() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getPrefix() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setPrefix(String prefix) throws DOMException {
        // TODO Auto-generated method stub

    }

    public String getLocalName() {
        return localName;
    }

    public boolean hasAttributes() {
        // TODO Auto-generated method stub
        return false;
    }

    public String getBaseURI() {
        // TODO Auto-generated method stub
        return null;
    }

    public short compareDocumentPosition(Node other) throws DOMException {
        // TODO Auto-generated method stub
        return 0;
    }

    public String getTextContent() throws DOMException {
        return textContent;
    }

    public void setTextContent(String textContent) throws DOMException {
        this.textContent = textContent;
    }

    public boolean isSameNode(Node other) {
        // TODO Auto-generated method stub
        return false;
    }

    public String lookupPrefix(String namespaceURI) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isDefaultNamespace(String namespaceURI) {
        // TODO Auto-generated method stub
        return false;
    }

    public String lookupNamespaceURI(String prefix) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isEqualNode(Node arg) {
        // TODO Auto-generated method stub
        return false;
    }

    public Object getFeature(String feature, String version) {
        // TODO Auto-generated method stub
        return null;
    }

    public Object setUserData(String key, Object data, UserDataHandler handler) {
        // TODO Auto-generated method stub
        return null;
    }

    public Object getUserData(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getTagName() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getAttribute(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    public void setAttribute(String name, String value) throws DOMException {
        // TODO Auto-generated method stub

    }

    public void removeAttribute(String name) throws DOMException {
        // TODO Auto-generated method stub

    }

    public Attr getAttributeNode(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    public Attr setAttributeNode(Attr newAttr) throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    public Attr removeAttributeNode(Attr oldAttr) throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    public NodeList getElementsByTagName(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getAttributeNS(String namespaceURI, String localName) throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    public void setAttributeNS(String namespaceURI, String qualifiedName, String value) throws DOMException {
        // TODO Auto-generated method stub

    }

    public void removeAttributeNS(String namespaceURI, String localName) throws DOMException {
        // TODO Auto-generated method stub

    }

    public Attr getAttributeNodeNS(String namespaceURI, String localName) throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    public Attr setAttributeNodeNS(Attr newAttr) throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    public NodeList getElementsByTagNameNS(String namespaceURI, String localName) throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean hasAttribute(String name) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean hasAttributeNS(String namespaceURI, String localName) throws DOMException {
        // TODO Auto-generated method stub
        return false;
    }

    public TypeInfo getSchemaTypeInfo() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setIdAttribute(String name, boolean isId) throws DOMException {
        // TODO Auto-generated method stub

    }

    public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) throws DOMException {
        // TODO Auto-generated method stub

    }

    public void setIdAttributeNode(Attr idAttr, boolean isId) throws DOMException {
        // TODO Auto-generated method stub

    }
}
