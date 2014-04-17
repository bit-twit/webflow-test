package org.bittwit.webflow;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.webflow.context.servlet.DefaultFlowUrlHandler;
import org.springframework.webflow.core.collection.AttributeMap;

public class FlexibleContextPathFlowUrlHandler extends DefaultFlowUrlHandler {

    @Value("${webflow.include.context.path.on.redirect}")
    private boolean useContextPathOnRedirect = true;
    @Value("${webflow.main.flow.id}")
    private String mainFlowId;

    @Override
	public String getFlowId(HttpServletRequest request) {
		/*String pathInfo = request.getPathInfo();
		if (pathInfo != null) {
			return pathInfo.substring(1);
		} else {
			String servletPath = request.getServletPath();
			if (StringUtils.hasText(servletPath)) {
				int dotIndex = servletPath.lastIndexOf('.');
				if (dotIndex != -1) {
					return servletPath.substring(1, dotIndex);
				} else {
					return servletPath.substring(1);
				}
			} else {
				String contextPath = request.getContextPath();
				if (StringUtils.hasText(contextPath)) {
					return request.getContextPath().substring(1);
				} else {
					return null;
				}
			}
		}*/
    	System.out.println("returning lgs main flow------------ " + this.mainFlowId);
    	return this.mainFlowId;
	}

    @Override
    public String createFlowDefinitionUrl(String flowId, AttributeMap input, HttpServletRequest request) {
        return cleanUrl(super.createFlowDefinitionUrl(flowId, input, request), request);
    }

    @Override
    public String createFlowExecutionUrl(String flowId, String flowExecutionKey, HttpServletRequest request) {
        return cleanUrl(super.createFlowExecutionUrl(flowId, flowExecutionKey, request), request);
    }

    protected String cleanUrl(String url, HttpServletRequest request) {
        String finalUrl = url;
        if (!this.useContextPathOnRedirect) {
            System.out.println("WARN: Removing contextPath on redirect.");
            String pattern = request.getContextPath();
            finalUrl = url.replaceFirst(pattern, "");
        }
        return finalUrl;
    }

}
