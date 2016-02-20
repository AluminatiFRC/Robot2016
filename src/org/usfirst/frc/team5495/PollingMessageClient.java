package org.usfirst.frc.team5495;
import java.util.HashMap;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * If there is no connection, messages are ignored. Does not work with wildcard topics yet.
 * @author shsrobotics
 *
 */
public class PollingMessageClient implements MqttCallback{
	private String brokerAddress;
	private MqttClient client;
	private MemoryPersistence persistance; // needed for mqtt
	private ConnectionState state = ConnectionState.DISCONNECTED;
	private HashMap<String, String> messages = new HashMap<>();
	private String[] subscriptions;
	private JSONParser parser = new JSONParser();
	
	enum ConnectionState{
		DISCONNECTED, CONNECTING, CONNECTED
	}
	
	public PollingMessageClient(String brokerAddress, String... subs) {
		persistance = new MemoryPersistence();
		this.brokerAddress = brokerAddress;
		this.subscriptions = subs;
		
		for (String sub : subscriptions){
			messages.put(sub, "{}");
		}
	}

	public void connect() {
		if (state != ConnectionState.DISCONNECTED) {
			return;
		}
		state = ConnectionState.CONNECTING;
		new Thread(() -> {
			while (client == null || !client.isConnected()) {
				try {
					client = new MqttClient(brokerAddress, UUID.randomUUID().toString().substring(0, 20), persistance);
					client.setCallback(this);
					MqttConnectOptions connOpts = new MqttConnectOptions();
					connOpts.setCleanSession(true);
					connOpts.setConnectionTimeout(1);
					
					System.out.println("[MQTT] Connecting to broker: " + brokerAddress);
					client.connect(connOpts);
					state = ConnectionState.CONNECTED;
					System.out.println("[MQTT] Connected to client sucsessfully");

					publish("testing", "RoboRIO connected to MQTT");
					
					client.subscribe(subscriptions);
				} catch (MqttException e) {
					System.err.println("[MQTT] MqttException, error connecting. Trying again");
					
					try {
						Thread.sleep(1000);
					} catch (Exception e1) {
					}
				}
			}
		}).start();
	}

	public void publish (String topic, String message) {
		if (client != null) {
			try {
				client.publish(topic, new MqttMessage(message.getBytes()));
			} catch (MqttException e) {
				System.err.println("[MQTT] Failed to publish message: "+ topic + " "+ message);
			}
		} else {
			System.err.println("[MQTT] Client not found, unable to publish message.");
		}
		
	}
	
	@Override
	public void connectionLost(Throwable ex) {
		System.err.println("[MQTT] Connection lost");
		state = ConnectionState.DISCONNECTED;
		connect();
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {	
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("MQTT: "+ topic + ":" + message);
		messages.put(topic, new String(message.getPayload()));
	}
	
	public String getMessage(String topic){
		return messages.get(topic);
	}
	
	public JSONObject getJsonObject(String topic){
    	JSONObject obj;
    	try {
			obj = (JSONObject) parser.parse(messages.get(topic));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
    	
    	return obj;
	}
}
