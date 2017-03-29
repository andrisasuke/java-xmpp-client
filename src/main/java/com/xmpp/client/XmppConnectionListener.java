package com.xmpp.client;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.filter.PresenceTypeFilter;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jxmpp.jid.Jid;
import org.jivesoftware.smack.packet.Presence.Type;

public class XmppConnectionListener implements ConnectionListener {

	@Override
	public void connected(XMPPConnection connection) {
		System.out.println("connected");
	}

	@Override
	public void authenticated(XMPPConnection connection, boolean resumed) {
		connection.addAsyncStanzaListener(new StanzaListener() {
			
			@Override
			public void processStanza(Stanza packet) throws NotConnectedException, InterruptedException {
				if (packet instanceof Presence) {
                    Presence presence = (Presence) packet;
                    Jid jid = presence.getFrom();
                    System.out.println( "chat invite status changed by user: : "
                            + jid + " calling listner");
                    System.out.println( "presence: " + presence.getFrom()
                            + "; type: " + presence.getType() + "; to: "
                            + presence.getTo() + "; " + presence.toXML());

                    if (presence.getType().equals(Presence.Type.subscribe)) {

                    	System.out.println( "subscribe");
                    	Presence p = new Presence(Type.subscribed);
                    	p.setTo(jid);
                    	connection.sendStanza(p);

                    } else if (presence.getType().equals(Presence.Type.unsubscribe)) {

                    	System.out.println( "unsubscribe");
                    } else {
                    	System.out.println( "Other type of presence packet received");
                    }

                }
			}
		}, PresenceTypeFilter.SUBSCRIBE);
	}

	@Override
	public void connectionClosed() {
		System.out.println("connectionClosed");
	}

	@Override
	public void connectionClosedOnError(Exception e) {
		System.out.println("connectionClosed error :"+e.getMessage());
	}

	@Override
	public void reconnectionSuccessful() {
		System.out.println("reconnectionSuccessful");
	}

	@Override
	public void reconnectingIn(int seconds) {
		System.out.println("reconnecting in :"+seconds);
	}

	@Override
	public void reconnectionFailed(Exception e) {
		System.out.println("reconnectionFailed error :"+e.getMessage());
	}
	
}
