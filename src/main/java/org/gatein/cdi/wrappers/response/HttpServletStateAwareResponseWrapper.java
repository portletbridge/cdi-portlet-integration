package org.gatein.cdi.wrappers.response;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.StateAwareResponse;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.Map;

/**
 * @author <a href="http://community.jboss.org/people/kenfinni">Ken Finnigan</a>
 */
public class HttpServletStateAwareResponseWrapper extends HttpServletPortletResponseWrapper implements StateAwareResponse {

    private StateAwareResponse stateAwareResponse;

    public HttpServletStateAwareResponseWrapper(StateAwareResponse response) {
        super(response);
        stateAwareResponse = response;
    }

    @Override
    public void setWindowState(WindowState windowState) throws WindowStateException {
        stateAwareResponse.setWindowState(windowState);
    }

    @Override
    public void setPortletMode(PortletMode portletMode) throws PortletModeException {
        stateAwareResponse.setPortletMode(portletMode);
    }

    @Override
    public void setRenderParameters(Map<String, String[]> parameters) {
        stateAwareResponse.setRenderParameters(parameters);
    }

    @Override
    public void setRenderParameter(String key, String value) {
        stateAwareResponse.setRenderParameter(key, value);
    }

    @Override
    public void setRenderParameter(String key, String[] values) {
        stateAwareResponse.setRenderParameter(key, values);
    }

    @Override
    public void setEvent(QName name, Serializable value) {
        stateAwareResponse.setEvent(name, value);
    }

    @Override
    public void setEvent(String name, Serializable value) {
        stateAwareResponse.setEvent(name, value);
    }

    @Override
    public Map<String, String[]> getRenderParameterMap() {
        return stateAwareResponse.getRenderParameterMap();
    }

    @Override
    public PortletMode getPortletMode() {
        return stateAwareResponse.getPortletMode();
    }

    @Override
    public WindowState getWindowState() {
        return stateAwareResponse.getWindowState();
    }

    @Override
    public void removePublicRenderParameter(String name) {
        stateAwareResponse.removePublicRenderParameter(name);
    }
}
