/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
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

import javax.portlet.ClientDataRequest;

/**
 * @author <a href="http://community.jboss.org/people/kenfinni">Ken Finnigan</a>
 */
public final class PortletFilterUtil {
    public static final String ENABLE_MULTIPART_WRAPPING_PARAMETER_NAME = "org.gatein.cdi.ENABLE_MULTIPART_REQUEST_WRAPPING";

    private PortletFilterUtil() {
    }

    public static boolean isMultipartRequest(ClientDataRequest request) {
        return request.getContentType() != null ? request.getContentType().startsWith("multipart/") : false;
    }
}
