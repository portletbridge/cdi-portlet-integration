package org.gatein.cdi.wrappers.response;

import org.gatein.cdi.wrappers.ServletOutputStreamWrapper;

import javax.portlet.CacheControl;
import javax.portlet.MimeResponse;
import javax.portlet.PortletURL;
import javax.portlet.ResourceURL;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author <a href="http://community.jboss.org/people/kenfinni">Ken Finnigan</a>
 */
public class HttpServletMimeResponseWrapper extends HttpServletPortletResponseWrapper implements MimeResponse {

    private MimeResponse mimeResponse;

    public HttpServletMimeResponseWrapper(MimeResponse response) {
        super(response);
        mimeResponse = response;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new ServletOutputStreamWrapper(mimeResponse);
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return mimeResponse.getWriter();
    }

    @Override
    public OutputStream getPortletOutputStream() throws IOException {
        return mimeResponse.getPortletOutputStream();
    }

    @Override
    public PortletURL createRenderURL() {
        return mimeResponse.createRenderURL();
    }

    @Override
    public PortletURL createActionURL() {
        return mimeResponse.createActionURL();
    }

    @Override
    public ResourceURL createResourceURL() {
        return mimeResponse.createResourceURL();
    }

    @Override
    public CacheControl getCacheControl() {
        return mimeResponse.getCacheControl();
    }
}
