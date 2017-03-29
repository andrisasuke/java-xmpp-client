package com.xmpp.client.main;

import java.io.IOException;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import com.xmpp.client.XmppManager;

public class ClientLogin {

	private static final String HOST = "localhost";
	private static final String USER = "user3";
	
	public static void main(String[] args) {
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
		
		boolean running = true;
		
		while(running){
			// forever loop if no errors
		}
		
	}
	
}
