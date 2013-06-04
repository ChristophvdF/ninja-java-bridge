package com.ninjablocks.client.driver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class RPCCall {
	
	private static ObjectMapper JSON = new ObjectMapper();

	private String context;
	private String method;
	private ArrayNode args;
	
	protected RPCCall() {
		// For Jackson to use...
	}
	
	public RPCCall(String context, String method, ArrayNode args) {
		this.context = context;
		this.method = method;
		this.args = args;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static RPCCall emit(String context, String event, Object... payload) {
		List args = new ArrayList();
		args.add(event);
		args.addAll(Arrays.asList(payload));
		return new RPCCall(context, "emit", JSON.convertValue(args, ArrayNode.class));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static RPCCall register(NinjaDevice device) {
		List args = new ArrayList();
		args.add("register");
		args.add(device);
		return new RPCCall(null, "emit", JSON.convertValue(args, ArrayNode.class));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static RPCCall on(String context, String event, Object... payload) {
		List args = new ArrayList();
		args.add(event);
		args.addAll(Arrays.asList(payload));
		return new RPCCall(context, "on", JSON.convertValue(args, ArrayNode.class));
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public static ObjectMapper getJSON() {
		return JSON;
	}

	public static void setJSON(ObjectMapper jSON) {
		JSON = jSON;
	}

	public ArrayNode getArgs() {
		return args;
	}

	public void setArgs(ArrayNode args) {
		this.args = args;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

}
