package org.gatein.cdi.wrappers.response;

import javax.portlet.PortletURL;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceURL;

/**
 * @author <a href="http://community.jboss.org/people/kenfinni">Ken Finnigan</a>
 */
public class HttpServletResourceResponseWrapper extends HttpServletMimeResponseWrapper implements ResourceResponse {

    private ResourceResponse resourceResponse;

    public HttpServletResourceResponseWrapper(ResourceResponse response) {
        super(response);
        resourceResponse = response;
    }

    @Override
    public PortletURL createRenderURL() {
        return resourceResponse.createRenderURL();
    }

    @Override
    public PortletURL createActionURL() {
        return resourceResponse.createActionURL();
    }

    @Override
    public ResourceURL createResourceURL() {
        return resourceResponse.createResourceURL();
    }
}
