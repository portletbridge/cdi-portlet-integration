/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
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
package org.gatein.cdi.wrappers;

import java.util.Enumeration;

import javax.portlet.PortletSession;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 * @author <a href="http://community.jboss.org/people/kenfinni">Ken Finnigan</a>
 */
public class PortletSessionWrapper implements HttpSession {

    private PortletSession wrapped;

    public PortletSessionWrapper(PortletSession portletSession) {
        wrapped = portletSession;
    }

    public PortletSession getWrapped() {
        return wrapped;
    }

    /**
     * @see javax.servlet.http.HttpSession#getCreationTime()
     */
    @Override
    public long getCreationTime() {
        return getWrapped().getCreationTime();
    }

    /**
     * @see javax.servlet.http.HttpSession#getId()
     */
    @Override
    public String getId() {
        return getWrapped().getId();
    }

    /**
     * @see javax.servlet.http.HttpSession#getLastAccessedTime()
     */
    @Override
    public long getLastAccessedTime() {
        return getWrapped().getLastAccessedTime();
    }

    /**
     * @see javax.servlet.http.HttpSession#getServletContext()
     */
    @Override
    public ServletContext getServletContext() {
        throw new UnsupportedOperationException();
    }

    /**
     * @see javax.servlet.http.HttpSession#setMaxInactiveInterval(int)
     */
    @Override
    public void setMaxInactiveInterval(int interval) {
        getWrapped().setMaxInactiveInterval(interval);
    }

    /**
     * @see javax.servlet.http.HttpSession#getMaxInactiveInterval()
     */
    @Override
    public int getMaxInactiveInterval() {
        return getWrapped().getMaxInactiveInterval();
    }

    /**
     * @see javax.servlet.http.HttpSession#getSessionContext()
     */
    @Override
    @Deprecated
    public HttpSessionContext getSessionContext() {
        throw new UnsupportedOperationException();
    }

    /**
     * @see javax.servlet.http.HttpSession#getAttribute(java.lang.String)
     */
    @Override
    public Object getAttribute(String name) {
        return getWrapped().getAttribute(name, PortletSession.APPLICATION_SCOPE);
    }

    /**
     * @see javax.servlet.http.HttpSession#getValue(java.lang.String)
     */
    @Override
    @Deprecated
    public Object getValue(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * @see javax.servlet.http.HttpSession#getAttributeNames()
     */
    @Override
    public Enumeration<String> getAttributeNames() {
        return getWrapped().getAttributeNames(PortletSession.APPLICATION_SCOPE);
    }

    /**
     * @see javax.servlet.http.HttpSession#getValueNames()
     */
    @Override
    @Deprecated
    public String[] getValueNames() {
        throw new UnsupportedOperationException();
    }

    /**
     * @see javax.servlet.http.HttpSession#setAttribute(java.lang.String, java.lang.Object)
     */
    @Override
    public void setAttribute(String name, Object value) {
        getWrapped().setAttribute(name, value, PortletSession.APPLICATION_SCOPE);
    }

    /**
     * @see javax.servlet.http.HttpSession#putValue(java.lang.String, java.lang.Object)
     */
    @Override
    @Deprecated
    public void putValue(String name, Object value) {
        throw new UnsupportedOperationException();
    }

    /**
     * @see javax.servlet.http.HttpSession#removeAttribute(java.lang.String)
     */
    @Override
    public void removeAttribute(String name) {
        getWrapped().removeAttribute(name, PortletSession.APPLICATION_SCOPE);
    }

    /**
     * @see javax.servlet.http.HttpSession#removeValue(java.lang.String)
     */
    @Override
    @Deprecated
    public void removeValue(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * @see javax.servlet.http.HttpSession#invalidate()
     */
    @Override
    public void invalidate() {
        getWrapped().invalidate();

    }

    /**
     * @see javax.servlet.http.HttpSession#isNew()
     */
    @Override
    public boolean isNew() {
        return getWrapped().isNew();
    }

}
