package com.big.web.b2b_big2.util;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.cxf.binding.soap.interceptor.SoapHeaderInterceptor;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.log4j.Logger;

/*
 * kombinasi:
 * http://stackoverflow.com/questions/17081841/cxf-ininterceptor-not-firing
 * dan
 * http://chrisdail.com/2008/03/31/apache-cxf-with-http-basic-authentication/
 * 
 */
/*
 * http://stackoverflow.com/questions/10888501/how-to-use-spring-autowired-in-a-custom-cxf-interceptor
 * You can't mix @InInterceptors (a CXF annotation) and @Component (a Spring annotation). That will create two separate instances of your interceptor: the one whose dependencies are getting injected by Spring, and one created by CXF
 */
public class BasicAuthAuthorizationInterceptor extends SoapHeaderInterceptor {

	// private Map<String, String> users;
	
	protected Logger log = Logger.getLogger(getClass());

	@Override
	public void handleMessage(Message message) throws Fault {
		AuthorizationPolicy policy = message.get(AuthorizationPolicy.class);

		// If the policy is not set, the user did not specify credentials.
		// 401 is sent to the client to indicate that authentication is
		// required.
		if (policy == null) {
			sendErrorResponse(message, HttpURLConnection.HTTP_UNAUTHORIZED);
			return;
		}

		String username = policy.getUserName();
		String password = policy.getPassword();

		System.out
		.println("**** GET THIS LINE TO CONSOLE TO SEE IF INTERCEPTOR IS FIRING!!!(" + username +"," + password + ")");

		// CHECK USERNAME AND PASSWORD
		if (!checkLogin(username, password)) {
			System.out
					.println("handleMessage: Invalid username or password for user: "
							+ policy.getUserName());
			sendErrorResponse(message, HttpURLConnection.HTTP_FORBIDDEN);
		}
	}

	private boolean checkLogin(String username, String password) {
		
		if (username.equals("admin") && password.equals("admin")) {
			return true;
		}
		return false;
	}

	private void sendErrorResponse(Message message, int responseCode) {
		Message outMessage = getOutMessage(message);
		outMessage.put(Message.RESPONSE_CODE, responseCode);

		// Set the response headers
		@SuppressWarnings("unchecked")
		Map<String, List<String>> responseHeaders = (Map<String, List<String>>) message
				.get(Message.PROTOCOL_HEADERS);

		if (responseHeaders != null) {
			responseHeaders.put("WWW-Authenticate",
					Arrays.asList(new String[] { "Basic realm=realm" }));
			responseHeaders.put("Content-Length",
					Arrays.asList(new String[] { "0" }));
		}
		message.getInterceptorChain().abort();
		try {
			getConduit(message).prepare(outMessage);
			close(outMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private Message getOutMessage(Message inMessage) {
		Exchange exchange = inMessage.getExchange();
		Message outMessage = exchange.getOutMessage();
		if (outMessage == null) {
			Endpoint endpoint = exchange.get(Endpoint.class);
			outMessage = endpoint.getBinding().createMessage();
			exchange.setOutMessage(outMessage);
		}
		outMessage.putAll(inMessage);
		return outMessage;
	}

	private Conduit getConduit(Message inMessage) throws IOException {
		Exchange exchange = inMessage.getExchange();
		EndpointReferenceType target = exchange
				.get(EndpointReferenceType.class);
		Conduit conduit = exchange.getDestination().getBackChannel(inMessage,
				null, target);
		exchange.setConduit(conduit);
		return conduit;
	}

	private void close(Message outMessage) throws IOException {
		OutputStream os = outMessage.getContent(OutputStream.class);
		os.flush();
		os.close();
	}
}
