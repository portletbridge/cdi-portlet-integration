package org.gatein.cdi.wrappers.response;

import javax.portlet.ActionResponse;
import java.io.IOException;

/**
 * @author <a href="http://community.jboss.org/people/kenfinni">Ken Finnigan</a>
 */
public class HttpServletActionResponseWrapper extends HttpServletStateAwareResponseWrapper implements ActionResponse {

    private ActionResponse actionResponse;

    public HttpServletActionResponseWrapper(ActionResponse response) {
        super(response);
        actionResponse = response;
    }

    @Override
    public void sendRedirect(String location, String renderUrlParamName) throws IOException {
        actionResponse.sendRedirect(location, renderUrlParamName);
    }
}
