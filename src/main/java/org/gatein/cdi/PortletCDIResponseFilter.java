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
package org.gatein.cdi;

import org.gatein.cdi.wrappers.request.HttpServletActionRequestWrapper;
import org.gatein.cdi.wrappers.request.HttpServletEventRequestWrapper;
import org.gatein.cdi.wrappers.request.HttpServletRenderRequestWrapper;
import org.gatein.cdi.wrappers.request.HttpServletResourceRequestWrapper;
import org.gatein.cdi.wrappers.response.HttpServletActionResponseWrapper;
import org.gatein.cdi.wrappers.response.HttpServletEventResponseWrapper;
import org.gatein.cdi.wrappers.response.HttpServletRenderResponseWrapper;
import org.gatein.cdi.wrappers.response.HttpServletResourceResponseWrapper;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.EventFilter;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.RenderFilter;
import javax.portlet.filter.ResourceFilter;
import java.io.IOException;

/**
 * Filter that implements doFilter() from {@link javax.portlet.filter.ActionFilter}, {@link javax.portlet.filter.EventFilter}, {@link javax.portlet.filter.ResourceFilter}, and
 * {@link javax.portlet.filter.RenderFilter} to wrap the respective {@link javax.portlet.PortletRequest} with the appropriate wrapper class that implements
 * {@link javax.servlet.http.HttpServletRequest}.
 *
 * This enables CDI implementations to not be concerned with specific handling for {@link javax.portlet.PortletRequest} objects instead of
 * the usual {@link javax.servlet.http.HttpServletRequest}.
 *
 * @author <a href="http://community.jboss.org/people/kenfinni">Ken Finnigan</a>
 */
public class PortletCDIResponseFilter implements ActionFilter, EventFilter, ResourceFilter, RenderFilter {

    /**
     * @see javax.portlet.filter.PortletFilter#init(javax.portlet.filter.FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfig) throws PortletException {
    }

    /**
     * @see javax.portlet.filter.PortletFilter#destroy()
     */
    @Override
    public void destroy() {
    }

    /**
     * @see javax.portlet.filter.ActionFilter#doFilter(javax.portlet.ActionRequest, javax.portlet.ActionResponse, javax.portlet.filter.FilterChain)
     */
    @Override
    public void doFilter(ActionRequest request, ActionResponse response, FilterChain chain) throws IOException,
            PortletException {
        chain.doFilter(new HttpServletActionRequestWrapper(request), new HttpServletActionResponseWrapper(response));
    }

    /**
     * @see javax.portlet.filter.EventFilter#doFilter(javax.portlet.EventRequest, javax.portlet.EventResponse, javax.portlet.filter.FilterChain)
     */
    @Override
    public void doFilter(EventRequest request, EventResponse response, FilterChain chain) throws IOException, PortletException {
        chain.doFilter(new HttpServletEventRequestWrapper(request), new HttpServletEventResponseWrapper(response));
    }

    /**
     * @see javax.portlet.filter.RenderFilter#doFilter(javax.portlet.RenderRequest, javax.portlet.RenderResponse, javax.portlet.filter.FilterChain)
     */
    @Override
    public void doFilter(RenderRequest request, RenderResponse response, FilterChain chain) throws IOException,
            PortletException {
        chain.doFilter(new HttpServletRenderRequestWrapper(request), new HttpServletRenderResponseWrapper(response));
    }

    /**
     * @see javax.portlet.filter.ResourceFilter#doFilter(javax.portlet.ResourceRequest, javax.portlet.ResourceResponse, javax.portlet.filter.FilterChain)
     */
    @Override
    public void doFilter(ResourceRequest request, ResourceResponse response, FilterChain chain) throws IOException,
            PortletException {
        chain.doFilter(new HttpServletResourceRequestWrapper(request), new HttpServletResourceResponseWrapper(response));
    }
}
