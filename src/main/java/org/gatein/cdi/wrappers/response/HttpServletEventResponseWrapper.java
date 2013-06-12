package org.gatein.cdi.wrappers.response;

import javax.portlet.EventRequest;
import javax.portlet.EventResponse;

/**
 * @author <a href="http://community.jboss.org/people/kenfinni">Ken Finnigan</a>
 */
public class HttpServletEventResponseWrapper extends HttpServletStateAwareResponseWrapper implements EventResponse {

    private EventResponse eventResponse;

    public HttpServletEventResponseWrapper(EventResponse response) {
        super(response);
        eventResponse = response;
    }

    @Override
    public void setRenderParameters(EventRequest request) {
        eventResponse.setRenderParameters(request);
    }
}
