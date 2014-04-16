package org.bittwit.webflow;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.webflow.context.servlet.DefaultFlowUrlHandler;
import org.springframework.webflow.core.collection.AttributeMap;

public class FlexibleContextPathFlowUrlHandler extends DefaultFlowUrlHandler {

    @Value("${webflow.redirect.useContextPath}")
    private boolean useContextPathOnRedirect = true;

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
