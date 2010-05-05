package org.scribe.eq;

import org.scribe.encoders.URL;
import org.scribe.http.Request;
import org.scribe.oauth.CallType;

public class GoogleEqualizer extends DefaultEqualizer {
	// I'll leave it it you to find a better way to handle this...
	// Pass the properties into the Equalizers?
	
	private static String SCOPE = "http://www.google.com/m8/feeds/";
	

	public String tuneStringToSign(Request request, String toSign, CallType type) {

		if(type.equals(CallType.REQUEST_TOKEN)) {
			toSign += URL.percentEncode("&scope=" + URL.percentEncode(SCOPE));
		}
		return toSign;
	}
	
	public void tuneRequest(Request request, CallType type) {
		if(type.equals(CallType.REQUEST_TOKEN)) {
			request.addPayload("scope=" + URL.percentEncode(SCOPE));
		}
	}
}