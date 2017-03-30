package com.xmpp.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

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
import org.jivesoftware.smackx.disco.ServiceDiscoveryManager;
import org.jivesoftware.smackx.disco.packet.DiscoverInfo;
import org.jivesoftware.smackx.disco.packet.DiscoverItems;
import org.jivesoftware.smackx.pubsub.PubSubManager;
import org.jivesoftware.smackx.push_notifications.PushNotificationsManager;
import org.jxmpp.jid.DomainBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import com.xmpp.client.custom.EnablePushNotificationsIQ;

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
	
	public boolean sendIQPacket(IQ packet){
		//	connection.sendStanza(packet);
			try {
				IQ responseIQ = connection.createStanzaCollectorAndSend(packet).nextResultOrThrow();
				return responseIQ.getType() 
		        		!= org.jivesoftware.smack.packet.IQ.Type.error;
			} catch (NoResponseException | XMPPErrorException | 
					NotConnectedException | InterruptedException e) {
				System.out.println("failed sending IQ "+ e.getMessage());
			}
		return false;
	}
	
	public boolean enablePush(String jid, String node, HashMap<String, String> publishOptions) 
			throws NoResponseException, XMPPErrorException, NotConnectedException, 
			XmppStringprepException, InterruptedException{
	
		EnablePushNotificationsIQ iq = 
				new EnablePushNotificationsIQ(JidCreate.from(jid), node, publishOptions);	
		IQ responseIQ = connection.createStanzaCollectorAndSend(iq).nextResultOrThrow();
        
		return responseIQ.getType() 
        		!= org.jivesoftware.smack.packet.IQ.Type.error;
		
	}
}
