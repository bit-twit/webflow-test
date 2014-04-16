package org.bittwit.webflow.helper;

import javax.servlet.http.HttpServletRequest;

public class LgsFlowHelper {

	public String getSubFlowIdByCampaign (HttpServletRequest request) {
		String subFlowId = null;
		String campaignUrl = request.getRequestURI().replace(request.getContextPath(), "");

//		TODO: this should come from MaMMI
		if (campaignUrl.contains("/rms-campaign")) {
			subFlowId = "rms";
		}
		else if (campaignUrl.contains("/gws-campaign")) {
			subFlowId = "gws";
		}

		System.out.println("subFlowId: " + subFlowId);

		return subFlowId;
	}
}
