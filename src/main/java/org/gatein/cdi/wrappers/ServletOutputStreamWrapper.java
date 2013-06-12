package org.gatein.cdi.wrappers;

import javax.portlet.MimeResponse;
import javax.servlet.ServletOutputStream;
import java.io.IOException;

/**
 * @author <a href="http://community.jboss.org/people/kenfinni">Ken Finnigan</a>
 */
public class ServletOutputStreamWrapper extends ServletOutputStream {
    private MimeResponse mimeResponse;

    public ServletOutputStreamWrapper(MimeResponse response) {
        mimeResponse = response;
    }

    @Override
    public void write(int b) throws IOException {
        mimeResponse.getWriter().write(b);
    }
}
