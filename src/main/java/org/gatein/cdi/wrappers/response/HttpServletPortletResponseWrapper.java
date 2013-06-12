package org.gatein.cdi.wrappers.response;

import javax.portlet.PortletResponse;
import javax.portlet.filter.PortletResponseWrapper;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Locale;

/**
 * @author <a href="http://community.jboss.org/people/kenfinni">Ken Finnigan</a>
 */
public abstract class HttpServletPortletResponseWrapper extends PortletResponseWrapper implements HttpServletResponse {

    private PortletResponse response;
    private HttpServletResponseWrapper servletResponse = null;

    public HttpServletPortletResponseWrapper(PortletResponse response) {
        super(response);
        this.response = response;

        try {
            Class clazz = Class.forName("org.gatein.pc.portlet.impl.jsr168.api.PortletResponseImpl");
            Method method = clazz.getMethod("getRealResponse");
            Object result = method.invoke(response);
            servletResponse = (HttpServletResponseWrapper) result;
        } catch (Exception e) {
            // Ignore. Just means we're not running in GateIn Portlet Container
        }
    }

    @Override
    public void addCookie(Cookie cookie) {
        if (null != servletResponse) {
            servletResponse.addCookie(cookie);
        } else {
            response.addProperty(cookie);
        }
    }

    @Override
    public boolean containsHeader(String name) {
        if (null != servletResponse) {
            return servletResponse.containsHeader(name);
        }
        return false;
    }

    @Override
    public String encodeRedirectURL(String url) {
        if (null != servletResponse) {
            return servletResponse.encodeRedirectURL(url);
        }
        return null;
    }

    @Override
    public String encodeUrl(String url) {
        if (null != servletResponse) {
            return servletResponse.encodeUrl(url);
        } else {
            return response.encodeURL(url);
        }
    }

    @Override
    public String encodeRedirectUrl(String url) {
        if (null != servletResponse) {
            return servletResponse.encodeRedirectURL(url);
        }
        return null;
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
        if (null != servletResponse) {
            servletResponse.sendError(sc, msg);
        }
    }

    @Override
    public void sendError(int sc) throws IOException {
        if (null != servletResponse) {
            servletResponse.sendError(sc);
        }
    }

    @Override
    public void sendRedirect(String location) throws IOException {
        if (null != servletResponse) {
            servletResponse.sendRedirect(location);
        }
    }

    @Override
    public void setDateHeader(String name, long date) {
        if (null != servletResponse) {
            servletResponse.setDateHeader(name, date);
        }
    }

    @Override
    public void addDateHeader(String name, long date) {
        if (null != servletResponse) {
            servletResponse.addDateHeader(name, date);
        }
    }

    @Override
    public void setHeader(String name, String value) {
        if (null != servletResponse) {
            servletResponse.setHeader(name, value);
        }
    }

    @Override
    public void addHeader(String name, String value) {
        if (null != servletResponse) {
            servletResponse.addHeader(name, value);
        }
    }

    @Override
    public void setIntHeader(String name, int value) {
        if (null != servletResponse) {
            servletResponse.setIntHeader(name, value);
        }
    }

    @Override
    public void addIntHeader(String name, int value) {
        if (null != servletResponse) {
            servletResponse.addIntHeader(name, value);
        }
    }

    @Override
    public void setStatus(int sc) {
        if (null != servletResponse) {
            servletResponse.setStatus(sc);
        }
    }

    @Override
    public void setStatus(int sc, String sm) {
        if (null != servletResponse) {
            servletResponse.setStatus(sc, sm);
        }
    }

    @Override
    public int getStatus() {
        if (null != servletResponse) {
            return servletResponse.getStatus();
        }
        return 0;
    }

    @Override
    public String getHeader(String name) {
        if (null != servletResponse) {
            return servletResponse.getHeader(name);
        }
        return null;
    }

    @Override
    public Collection<String> getHeaders(String name) {
        if (null != servletResponse) {
            return servletResponse.getHeaders(name);
        }
        return null;
    }

    @Override
    public Collection<String> getHeaderNames() {
        if (null != servletResponse) {
            return servletResponse.getHeaderNames();
        }
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        if (null != servletResponse) {
            return servletResponse.getCharacterEncoding();
        }
        return null;
    }

    @Override
    public String getContentType() {
        if (null != servletResponse) {
            return servletResponse.getContentType();
        }
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCharacterEncoding(String charset) {
        if (null != servletResponse) {
            servletResponse.setCharacterEncoding(charset);
        }
    }

    @Override
    public void setContentLength(int len) {
        if (null != servletResponse) {
            servletResponse.setContentLength(len);
        }
    }

    @Override
    public void setContentType(String type) {
        if (null != servletResponse) {
            servletResponse.setContentType(type);
        }
    }

    @Override
    public void setBufferSize(int size) {
        if (null != servletResponse) {
            servletResponse.setBufferSize(size);
        }
    }

    @Override
    public int getBufferSize() {
        if (null != servletResponse) {
            return servletResponse.getBufferSize();
        }
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {
        if (null != servletResponse) {
            servletResponse.flushBuffer();
        }
    }

    @Override
    public void resetBuffer() {
        if (null != servletResponse) {
            servletResponse.resetBuffer();
        }
    }

    @Override
    public boolean isCommitted() {
        if (null != servletResponse) {
            return servletResponse.isCommitted();
        }
        return false;
    }

    @Override
    public void reset() {
        if (null != servletResponse) {
            servletResponse.reset();
        }
    }

    @Override
    public void setLocale(Locale loc) {
        if (null != servletResponse) {
            servletResponse.setLocale(loc);
        }
    }

    @Override
    public Locale getLocale() {
        if (null != servletResponse) {
            return servletResponse.getLocale();
        }
        return null;
    }
}
