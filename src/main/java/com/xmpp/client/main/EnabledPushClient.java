package com.xmpp.client.main;

import java.io.IOException;
import java.util.HashMap;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jxmpp.stringprep.XmppStringprepException;

import com.xmpp.client.XmppManager;

public class EnabledPushClient {

	private static final String HOST = "localhost";
	private static final String USER = "user4";
	
	public static void main(String[] args) {
		XmppManager xmppManager = connectAndLogin();
		
		HashMap<String, String> publishOptions = new HashMap<>();
        publishOptions.put("service", "apns");
        publishOptions.put("device", "ASklkldksldks");
		
        try {
			xmppManager.enablePush("pubsub.localhost", "test-andri", null);
		} catch (NoResponseException | XMPPErrorException | NotConnectedException | XmppStringprepException
				| InterruptedException e) {
			e.printStackTrace();
		}
		
		boolean running = true;
		
		while(running){
			// forever loop if no errors
		}
	}

	public static XmppManager connectAndLogin(){
		XmppManager xmppManager = new XmppManager(HOST, 5222);
		try {
			xmppManager.init();
		} catch (SmackException | IOException | XMPPException | InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			xmppManager.login(USER, USER);
		} catch (XMPPException | SmackException | IOException | InterruptedException e) {
			e.printStackTrace();
			System.exit(0);
		}
	
		return xmppManager;
	}
	
}
