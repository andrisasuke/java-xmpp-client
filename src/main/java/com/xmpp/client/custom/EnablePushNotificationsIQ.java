package com.xmpp.client.custom;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smackx.pubsub.packet.PubSub;
import org.jivesoftware.smackx.xdata.FormField;
import org.jivesoftware.smackx.xdata.packet.DataForm;
import org.jxmpp.jid.Jid;

public class EnablePushNotificationsIQ extends IQ {

	public static final String ELEMENT = "enable";

    public static final String NAMESPACE = "urn:xmpp:push:0";

    private final Jid jid;
    private final String node;
    private final HashMap<String, String> publishOptions;

    public EnablePushNotificationsIQ(Jid jid, String node, HashMap<String, String> publishOptions) {
        super(ELEMENT, NAMESPACE);
        this.jid = jid;
        this.node = node;
        this.publishOptions = publishOptions;
        this.setType(Type.set);
    }

    public EnablePushNotificationsIQ(Jid jid, String node) {
        this(jid, node, null);
    }

    public Jid getJid() {
        return jid;
    }

    public String getNode() {
        return node;
    }

    public HashMap<String, String> getPublishOptions() {
        return publishOptions;
    }

    @Override
    protected IQChildElementXmlStringBuilder getIQChildElementBuilder(IQChildElementXmlStringBuilder xml) {
 
    	xml.attribute("jid", jid.toString());
        xml.attribute("node", node);
        xml.rightAngleBracket();

        if (publishOptions != null) {
            DataForm dataForm = new DataForm(DataForm.Type.submit);

            FormField formTypeField = new FormField("FORM_TYPE");
            formTypeField.addValue(PubSub.NAMESPACE + "#publish-options");
            dataForm.addField(formTypeField);

            Iterator<Map.Entry<String, String>> publishOptionsIterator = publishOptions.entrySet().iterator();
            while (publishOptionsIterator.hasNext()) {
                Map.Entry<String, String> pairVariableValue = publishOptionsIterator.next();
                FormField field = new FormField(pairVariableValue.getKey());
                field.addValue(pairVariableValue.getValue());
                dataForm.addField(field);
            }

            xml.element(dataForm);
        }

        return xml;
    }
	
}
