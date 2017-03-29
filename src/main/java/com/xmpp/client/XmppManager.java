package com.xmpp.client;

import java.io.IOException;
import java.util.HashMap;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.push_notifications.PushNotificationsManager;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

public class XmppManager {

	private String server;
	private int port;

	private XMPPTCPConnectionConfiguration.Builder config;
	private XMPPTCPConnection connection;
	
	private XmppConnectionListener connectionListener;
	
	public XmppManager(String server, int port) {
		this.server = server;
		this.port = port;
		this.connectionListener = new XmppConnectionListener();
	}
	
	public void init() throws SmackException, IOException, XMPPException, InterruptedException{
		System.out.println(String.format("Initializing connection to server %1$s port %2$d", server, port));
		config = XMPPTCPConnectionConfiguration.builder();
		config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        config.setHost(server);
        config.setPort(port);
		config.setSendPresence(true);
		config.setDebuggerEnabled(true);
		config.setXmppDomain(server);
		
		connection = new XMPPTCPConnection(config.build());
		connection.addConnectionListener(connectionListener);
		connection.connect();
	}
	
	public void login(String username, String pass) throws XMPPException, SmackException, 
											IOException, InterruptedException{
		connection.login(username, pass);
		Presence presence = new Presence(Type.available);
		connection.sendStanza(presence);
	}
	
	public void sendIQPacket(IQ packet){
		try {
			connection.sendStanza(packet);
		} catch (NotConnectedException | InterruptedException e) {
			System.out.println("failed sending IQ "+ e.getMessage());
		}
	}
	
	public void enablePush(String jid, String node, HashMap<String, String> publishOptions) 
			throws NoResponseException, XMPPErrorException, NotConnectedException, 
			XmppStringprepException, InterruptedException{
		
		PushNotificationsManager pushNotificationsManager =
				PushNotificationsManager.getInstanceFor(connection);
		if(publishOptions != null && !publishOptions.isEmpty())
			pushNotificationsManager.enable(JidCreate.from(jid), node, publishOptions);
		else pushNotificationsManager.enable(JidCreate.from(jid), node);
		
	}
}
