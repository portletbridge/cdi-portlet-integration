package org.gatein.cdi.wrappers.response;

import javax.portlet.PortletMode;
import javax.portlet.RenderResponse;
import java.util.Collection;

/**
 * @author <a href="http://community.jboss.org/people/kenfinni">Ken Finnigan</a>
 */
public class HttpServletRenderResponseWrapper extends HttpServletMimeResponseWrapper implements RenderResponse {

    RenderResponse renderResponse;

    public HttpServletRenderResponseWrapper(RenderResponse response) {
        super(response);
        renderResponse = response;
    }

    @Override
    public void setTitle(String title) {
        renderResponse.setTitle(title);
    }

    @Override
    public void setNextPossiblePortletModes(Collection<PortletMode> portletModes) {
        renderResponse.setNextPossiblePortletModes(portletModes);
    }
}
