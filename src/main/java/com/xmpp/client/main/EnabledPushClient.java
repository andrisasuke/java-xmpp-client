package com.xmpp.client.main;

import java.util.HashMap;

import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jxmpp.stringprep.XmppStringprepException;

import com.xmpp.client.XmppManager;

public class EnabledPushClient {

	private static final String HOST = "localhost";
	private static final String USER = "user5";

	public static void main(String[] args) {
		
		XmppManager xmppManager = Main.connectAndLogin(HOST, USER);

		HashMap<String, String> publishOptions = new HashMap<>();
		publishOptions.put("service", "apns");
		publishOptions.put("device_id", "your_apn_token");

		try {
			boolean value = xmppManager.enablePush("pubsub@localhost", "test-node-user5", null);
			System.out.println("enable result : "+value);
			
		} catch (NoResponseException | XMPPErrorException | NotConnectedException | XmppStringprepException
				| InterruptedException e) {
			e.printStackTrace();
		}

		boolean running = true;

		while (running) {
			// forever loop if no errors
		}
	}


}
