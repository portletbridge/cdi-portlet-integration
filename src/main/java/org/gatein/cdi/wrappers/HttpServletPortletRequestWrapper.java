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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;

import javax.portlet.ClientDataRequest;
import javax.portlet.PortletRequest;
import javax.portlet.filter.PortletRequestWrapper;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Abstract implementation of {@link PortletRequestWrapper} that implements {@link HttpServletRequest} to support casting
 * in CDI/Weld.
 *
 * @author <a href="http://community.jboss.org/people/kenfinni">Ken Finnigan</a>
 */
public abstract class HttpServletPortletRequestWrapper extends PortletRequestWrapper implements HttpServletRequest {

    private PortletRequest request;
    private HttpServletRequestWrapper servletRequest = null;

    public HttpServletPortletRequestWrapper(PortletRequest request) {
        super(request);
        this.request = request;

        try {
            Class clazz = Class.forName("org.gatein.pc.portlet.impl.jsr168.api.PortletRequestImpl");
            Method method = clazz.getMethod("getRealRequest");
            Object result = method.invoke(request);
            servletRequest = (HttpServletRequestWrapper) result;
        } catch (Exception e) {
            // Ignore. Just means we're not running in GateIn Portlet Container
        }
    }

    /**
     * @see javax.servlet.ServletRequest#getCharacterEncoding()
     */
    @Override
    public String getCharacterEncoding() {
        if (null != servletRequest) {
            return servletRequest.getCharacterEncoding();
        }
        if (request instanceof ClientDataRequest) {
            return ((ClientDataRequest) request).getCharacterEncoding();
        }
        return null;
    }

    /**
     * @see javax.servlet.ServletRequest#setCharacterEncoding(java.lang.String)
     */
    @Override
    public void setCharacterEncoding(String enc) throws UnsupportedEncodingException {
        if (null != servletRequest) {
            servletRequest.setCharacterEncoding(enc);
        }
        if (request instanceof ClientDataRequest) {
            ((ClientDataRequest) request).setCharacterEncoding(enc);
        }
    }

    /**
     * @see javax.servlet.ServletRequest#getContentLength()
     */
    @Override
    public int getContentLength() {
        if (null != servletRequest) {
            return servletRequest.getContentLength();
        }
        if (request instanceof ClientDataRequest) {
            return ((ClientDataRequest) request).getContentLength();
        }
        return 0;
    }

    /**
     * @see javax.servlet.ServletRequest#getContentType()
     */
    @Override
    public String getContentType() {
        if (null != servletRequest) {
            return servletRequest.getContentType();
        }
        if (request instanceof ClientDataRequest) {
            return ((ClientDataRequest) request).getContentType();
        }
        return null;
    }

    /**
     * @see javax.servlet.ServletRequest#getInputStream()
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (null != servletRequest) {
            return servletRequest.getInputStream();
        }
        if (request instanceof ClientDataRequest) {
            return new ServletInputStreamWrapper((ClientDataRequest) request);
        }
        return null;
    }

    /**
     * @see javax.servlet.ServletRequest#getProtocol()
     */
    @Override
    public String getProtocol() {
        return "HTTP/1.1";
    }

    /**
     * @see javax.servlet.ServletRequest#getReader()
     */
    @Override
    public BufferedReader getReader() throws IOException {
        if (null != servletRequest) {
            return servletRequest.getReader();
        }
        if (request instanceof ClientDataRequest) {
            return ((ClientDataRequest) request).getReader();
        }
        return null;
    }

    /**
     * @see javax.servlet.ServletRequest#getRemoteAddr()
     */
    @Override
    public String getRemoteAddr() {
        if (null != servletRequest) {
            return servletRequest.getRemoteAddr();
        }
        return null;
    }

    /**
     * @see javax.servlet.ServletRequest#getRemoteHost()
     */
    @Override
    public String getRemoteHost() {
        if (null != servletRequest) {
            return servletRequest.getRemoteHost();
        }
        return null;
    }

    /**
     * @see javax.servlet.ServletRequest#getRequestDispatcher(java.lang.String)
     */
    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        if (null != servletRequest) {
            return servletRequest.getRequestDispatcher(path);
        }
        return null;
    }

    /**
     * @see javax.servlet.ServletRequest#getRealPath(java.lang.String)
     */
    @Override
    @Deprecated
    public String getRealPath(String path) {
        return null;
    }

    /**
     * @see javax.servlet.ServletRequest#getRemotePort()
     */
    @Override
    public int getRemotePort() {
        if (null != servletRequest) {
            return servletRequest.getRemotePort();
        }
        return 0;
    }

    /**
     * @see javax.servlet.ServletRequest#getLocalName()
     */
    @Override
    public String getLocalName() {
        return request.getServerName();
    }

    /**
     * @see javax.servlet.ServletRequest#getLocalAddr()
     */
    @Override
    public String getLocalAddr() {
        if (null != servletRequest) {
            return servletRequest.getLocalAddr();
        }
        return null;
    }

    /**
     * @see javax.servlet.ServletRequest#getLocalPort()
     */
    @Override
    public int getLocalPort() {
        return request.getServerPort();
    }

    /**
     * @see javax.servlet.http.HttpServletRequest#getDateHeader(java.lang.String)
     */
    @Override
    public long getDateHeader(String name) {
        if (null != servletRequest) {
            return servletRequest.getDateHeader(name);
        }
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
        try {
            String dateString = request.getProperty("If-Modified-Since");
            if (null == dateString || dateString.length() == 0) {
                return -1;
            }

            Date ifModifiedSinceDate = format.parse(dateString);
            return ifModifiedSinceDate.getTime();
        } catch (ParseException ex) {
            return -1;
        }
    }

    /**
     * @see javax.servlet.http.HttpServletRequest#getHeader(java.lang.String)
     */
    @Override
    public String getHeader(String name) {
        return request.getProperty(name);
    }

    /**
     * @see javax.servlet.http.HttpServletRequest#getHeaders(java.lang.String)
     */
    @Override
    public Enumeration getHeaders(String name) {
        return request.getProperties(name);
    }

    /**
     * @see javax.servlet.http.HttpServletRequest#getHeaderNames()
     */
    @Override
    public Enumeration getHeaderNames() {
        return request.getPropertyNames();
    }

    /**
     * @see javax.servlet.http.HttpServletRequest#getIntHeader(java.lang.String)
     */
    @Override
    public int getIntHeader(String name) {
        if (null != servletRequest) {
            return servletRequest.getIntHeader(name);
        }
        return 0;
    }

    /**
     * @see javax.servlet.http.HttpServletRequest#getMethod()
     */
    @Override
    public String getMethod() {
        if (null != servletRequest) {
            return servletRequest.getMethod();
        }
        if (request instanceof ClientDataRequest) {
            return ((ClientDataRequest) request).getMethod();
        }
        return null;
    }

    /**
     * @see javax.servlet.http.HttpServletRequest#getPathInfo()
     */
    @Override
    public String getPathInfo() {
        if (null != servletRequest) {
            return servletRequest.getPathInfo();
        }
        Object obj = request.getAttribute("javax.servlet.include.path_info");
        if (null != obj && obj instanceof String) {
            return (String) obj;
        }
        return null;
    }

    /**
     * @see javax.servlet.http.HttpServletRequest#getPathTranslated()
     */
    @Override
    public String getPathTranslated() {
        if (null != servletRequest) {
            return servletRequest.getPathTranslated();
        }
        return null;
    }

    /**
     * @see javax.servlet.http.HttpServletRequest#getQueryString()
     */
    @Override
    public String getQueryString() {
        if (null != servletRequest) {
            return servletRequest.getQueryString();
        }
        Object obj = request.getAttribute("javax.servlet.include.query_string");
        if (null != obj && obj instanceof String) {
            return (String) obj;
        }
        return null;
    }

    /**
     * @see javax.servlet.http.HttpServletRequest#getRequestURI()
     */
    @Override
    public String getRequestURI() {
        if (null != servletRequest) {
            return servletRequest.getRequestURI();
        }
        Object obj = request.getAttribute("javax.servlet.include.request_uri");
        if (null != obj && obj instanceof String) {
            return (String) obj;
        }
        return null;
    }

    /**
     * @see javax.servlet.http.HttpServletRequest#getRequestURL()
     */
    @Override
    public StringBuffer getRequestURL() {
        if (null != servletRequest) {
            return servletRequest.getRequestURL();
        }
        return null;
    }

    /**
     * @see javax.servlet.http.HttpServletRequest#getServletPath()
     */
    @Override
    public String getServletPath() {
        if (null != servletRequest) {
            return servletRequest.getServletPath();
        }
        Object obj = request.getAttribute("javax.servlet.include.servlet_path");
        if (null != obj && obj instanceof String) {
            return (String) obj;
        }
        return null;
    }

    /**
     * @see javax.servlet.http.HttpServletRequest#getSession(boolean)
     */
    @Override
    public HttpSession getSession(boolean create) {
        if (null != servletRequest) {
            return servletRequest.getSession(create);
        }
        return new PortletSessionWrapper(request.getPortletSession(create));
    }

    /**
     * @see javax.servlet.http.HttpServletRequest#getSession()
     */
    @Override
    public HttpSession getSession() {
        if (null != servletRequest) {
            return servletRequest.getSession();
        }
        return new PortletSessionWrapper(request.getPortletSession());
    }

    /**
     * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromCookie()
     */
    @Override
    public boolean isRequestedSessionIdFromCookie() {
        if (null != servletRequest) {
            return servletRequest.isRequestedSessionIdFromCookie();
        }
        return false;
    }

    /**
     * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromURL()
     */
    @Override
    public boolean isRequestedSessionIdFromURL() {
        if (null != servletRequest) {
            return servletRequest.isRequestedSessionIdFromURL();
        }
        return false;
    }

    /**
     * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromUrl()
     */
    @Override
    public boolean isRequestedSessionIdFromUrl() {
        if (null != servletRequest) {
            return servletRequest.isRequestedSessionIdFromUrl();
        }
        return false;
    }

    @Override
    public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
        if (null != servletRequest) {
            return servletRequest.authenticate(response);
        }
        return false;
    }

    @Override
    public void login(String username, String password) throws ServletException {
        if (null != servletRequest) {
            servletRequest.login(username, password);
        }
    }

    @Override
    public void logout() throws ServletException {
        if (null != servletRequest) {
            servletRequest.logout();
        }
    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        if (null != servletRequest) {
            return servletRequest.getParts();
        }
        return null;
    }

    @Override
    public Part getPart(String name) throws IOException, ServletException {
        if (null != servletRequest) {
            return servletRequest.getPart(name);
        }
        return null;
    }

    @Override
    public ServletContext getServletContext() {
        if (null != servletRequest) {
            return servletRequest.getServletContext();
        }
        return null;
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        if (null != servletRequest) {
            return servletRequest.startAsync();
        }
        return null;
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        if (null != servletRequest) {
            return servletRequest.startAsync(servletRequest, servletResponse);
        }
        return null;
    }

    @Override
    public boolean isAsyncStarted() {
        if (null != servletRequest) {
            return servletRequest.isAsyncStarted();
        }
        return false;
    }

    @Override
    public boolean isAsyncSupported() {
        if (null != servletRequest) {
            return servletRequest.isAsyncSupported();
        }
        return false;
    }

    @Override
    public AsyncContext getAsyncContext() {
        if (null != servletRequest) {
            return servletRequest.getAsyncContext();
        }
        return null;
    }

    @Override
    public DispatcherType getDispatcherType() {
        if (null != servletRequest) {
            return servletRequest.getDispatcherType();
        }
        return null;
    }
}
