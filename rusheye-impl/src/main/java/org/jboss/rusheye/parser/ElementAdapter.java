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

    @Override
    public String getNodeName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getNodeValue() throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setNodeValue(String nodeValue) throws DOMException {
        // TODO Auto-generated method stub

    }

    @Override
    public short getNodeType() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Node getParentNode() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NodeList getChildNodes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Node getFirstChild() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Node getLastChild() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Node getPreviousSibling() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Node getNextSibling() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NamedNodeMap getAttributes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Document getOwnerDocument() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Node insertBefore(Node newChild, Node refChild) throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Node removeChild(Node oldChild) throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Node appendChild(Node newChild) throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasChildNodes() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Node cloneNode(boolean deep) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void normalize() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isSupported(String feature, String version) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getNamespaceURI() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPrefix() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setPrefix(String prefix) throws DOMException {
        // TODO Auto-generated method stub

    }

    @Override
    public String getLocalName() {
        return localName;
    }

    @Override
    public boolean hasAttributes() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getBaseURI() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public short compareDocumentPosition(Node other) throws DOMException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getTextContent() throws DOMException {
        return textContent;
    }

    @Override
    public void setTextContent(String textContent) throws DOMException {
        this.textContent = textContent;
    }

    @Override
    public boolean isSameNode(Node other) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String lookupPrefix(String namespaceURI) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isDefaultNamespace(String namespaceURI) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String lookupNamespaceURI(String prefix) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isEqualNode(Node arg) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Object getFeature(String feature, String version) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object setUserData(String key, Object data, UserDataHandler handler) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getUserData(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getTagName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getAttribute(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setAttribute(String name, String value) throws DOMException {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeAttribute(String name) throws DOMException {
        // TODO Auto-generated method stub

    }

    @Override
    public Attr getAttributeNode(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Attr setAttributeNode(Attr newAttr) throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Attr removeAttributeNode(Attr oldAttr) throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NodeList getElementsByTagName(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getAttributeNS(String namespaceURI, String localName) throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setAttributeNS(String namespaceURI, String qualifiedName, String value) throws DOMException {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeAttributeNS(String namespaceURI, String localName) throws DOMException {
        // TODO Auto-generated method stub

    }

    @Override
    public Attr getAttributeNodeNS(String namespaceURI, String localName) throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Attr setAttributeNodeNS(Attr newAttr) throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NodeList getElementsByTagNameNS(String namespaceURI, String localName) throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasAttribute(String name) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasAttributeNS(String namespaceURI, String localName) throws DOMException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public TypeInfo getSchemaTypeInfo() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setIdAttribute(String name, boolean isId) throws DOMException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) throws DOMException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setIdAttributeNode(Attr idAttr, boolean isId) throws DOMException {
        // TODO Auto-generated method stub

    }

}
