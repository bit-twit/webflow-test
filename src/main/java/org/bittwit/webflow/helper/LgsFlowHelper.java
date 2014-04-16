package org.bittwit.webflow.helper;

import javax.servlet.http.HttpServletRequest;

public class LgsFlowHelper {

	public String getSubFlowIdByCampaign (HttpServletRequest request) {
		String subFlowId = null;
		String campaignUrl = request.getPathInfo();

//		TODO: this should come from MaMMI
		if ("/rms-campaign".equals(campaignUrl)) {
			subFlowId = "rms";
		}
		else if ("/gws-campaign".equals(campaignUrl)) {
			subFlowId = "gws";
		}

		System.out.println("subFlowId: " + subFlowId);

		return subFlowId;
	}
}
