# CDI Portlet Integration

## What is this?

This library contains one Portlet Filter that wraps Portlet Request objects with HttpServletRequest so that CDI will work within a JSF portlet that uses a JSF Bridge. It also contains
a second filter that wraps both Portlet Request and Response objects, for when you need CDI to operate on the request and response objects.

## Configuration

To enable this integration for your JSF portlet, simply add the following filter definition into your portlet.xml:

    <filter>
        <filter-name>PortletCDIFilter</filter-name>
        <filter-class>org.gatein.cdi.PortletCDIFilter</filter-class>
        <lifecycle>ACTION_PHASE</lifecycle>
        <lifecycle>EVENT_PHASE</lifecycle>
        <lifecycle>RENDER_PHASE</lifecycle>
        <lifecycle>RESOURCE_PHASE</lifecycle>
    </filter>
    <filter-mapping>
        <filter-name>PortletCDIFilter</filter-name>
        <portlet-name>[Name of your portlet as defined in portlet-name]</portlet-name>
    </filter-mapping>

The above example uses the filter that wraps Portlet Request objects only. If you want to use the filter that wraps Portlet response objects as well, change the *filter-class* tag content with
`org.gatein.cdi.PortletCDIResponseFilter`.

## What is supported

The following CDI Scopes are currently supported within a JSF portlet using this library:
* `@ApplicationScoped`
* `@SessionScoped`
* `@ConversationScoped` for long running conversations

### @ConversationScoped (transient conversations)

Transient conversations are not supported within a JSF portlet as they are initialized at the commencement of the `ActionRequest` and `RenderRequest`, so any beans that had data saved during `ActionRequest` will be reset when `RenderRequest` commences.

### @RequestScoped

`@RequestScoped` is not supported within a JSF portlet as the concept of a `ServletRequest` (for JSF) and a `PortletRequest` have very different lifecycles.  This results in any `@RequestScoped` beans being initialized at the commencement of any `PortletRequest`, with no way to retain data between them.
