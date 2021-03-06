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
package org.gatein.cdi.wrappers.request;

import javax.portlet.ResourceRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Wraps {@link ResourceRequest} for use with {@link HttpServletRequest}.
 *
 * @author <a href="http://community.jboss.org/people/kenfinni">Ken Finnigan</a>
 */
public class HttpServletResourceRequestWrapper extends HttpServletPortletRequestWrapper implements ResourceRequest {

    private ResourceRequest request;

    public HttpServletResourceRequestWrapper(ResourceRequest request) {
        super(request);
        this.request = request;
    }

    @Override
    public InputStream getPortletInputStream() throws IOException {
        return request.getPortletInputStream();
    }

    @Override
    public String getETag() {
        return request.getETag();
    }

    @Override
    public String getResourceID() {
        return request.getResourceID();
    }

    @Override
    public Map<String, String[]> getPrivateRenderParameterMap() {
        return request.getPrivateRenderParameterMap();
    }

    @Override
    public String getCacheability() {
        return request.getCacheability();
    }
}
