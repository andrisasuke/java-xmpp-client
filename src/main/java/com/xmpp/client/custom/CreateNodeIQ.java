package com.xmpp.client.custom;

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.util.XmlStringBuilder;

public class CreateNodeIQ extends IQ {

	private String node;
	
	public CreateNodeIQ(String node) {
		super("pubsub", "http://jabber.org/protocol/pubsub");
		this.node = node;
        this.setType(Type.set);
	}

	@Override
	protected IQChildElementXmlStringBuilder getIQChildElementBuilder(IQChildElementXmlStringBuilder xml) {
		xml.rightAngleBracket();
		
		XmlStringBuilder createNode = new XmlStringBuilder();
		createNode.halfOpenElement("create");
		createNode.attribute("node", node);
		createNode.attribute("type", "push");
		createNode.closeEmptyElement();
		xml.append(createNode);
		
		return xml;
	}

}
