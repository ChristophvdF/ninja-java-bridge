package com.ninjablocks.client.driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NodeBridge {
	
	private static ObjectMapper JSON = new ObjectMapper();
	
	private static List<JsonEventHandler> handlers = new ArrayList<JsonEventHandler>();
	private static Map<String, NinjaDevice> devices = new HashMap<String, NinjaDevice>();
	
	public static void main(String... a) {
		out("\"ready\"");
	}
	
	static {
		
		new Thread(new Runnable() {
			public void run() {
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				while (true) {
					try {
						in(in.readLine());
					} catch (IOException e) {
						e.printStackTrace();
						System.exit(1);
					}
				}
			}
		}).start();
		
	}
	
	private static void in(String json) throws JsonParseException, JsonMappingException, IOException {
		RPCCall call = JSON.readValue(json, RPCCall.class);
		
		if (call.getMethod().equals("on")) {
			int handler = call.getArgs().remove(0).asInt();
			String event = call.getArgs().remove(0).asText();
			handlers.get(handler).handle(event, call.getArgs());
		} else if (call.getMethod().equals("init")) {
			try {
				Class<?> clazz = Class.forName(call.getArgs().get(0).asText());
				Constructor<?> constructor = clazz.getConstructor(NinjaClient.class);
				Object driver = constructor.newInstance(NinjaClient.instance);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		} else {
			System.err.println("Unknown RPC method : " + call.getMethod());
		}
	}
	
	private static synchronized void out(String json) {
		System.out.println(json);
	}
	
	private static void out(RPCCall rpc) {
		try {
			out(JSON.writer().writeValueAsString(rpc));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static void on(String context, String event, JsonEventHandler handler) {
		handlers.add(handler);
		out(RPCCall.on(context, event, handlers.size()-1));
	}
	
	public static void register(NinjaDevice device) {
		devices.put(device.getGuid(), device);
		out(RPCCall.register(device));
	}

	public static void emit(String context, String event, Object... payload) {
		out(RPCCall.emit(context, event, payload));
	}

}
