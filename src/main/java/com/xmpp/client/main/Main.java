package com.xmpp.client.main;

import java.io.IOException;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import com.xmpp.client.XmppManager;

public class Main {

	public static XmppManager connectAndLogin(String host, String user) {
		XmppManager xmppManager = new XmppManager(host, 5222);
		try {
			xmppManager.init();
		} catch (SmackException | IOException | XMPPException | InterruptedException e) {
			e.printStackTrace();
		}

		try {
			xmppManager.login(user, user);
		} catch (XMPPException | SmackException | IOException | InterruptedException e) {
			e.printStackTrace();
			System.exit(0);
		}

		return xmppManager;
	}
	
}
