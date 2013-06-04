package com.ninjablocks.client.driver.test;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.ninjablocks.client.driver.BaseNinjaDriver;
import com.ninjablocks.client.driver.JsonEventHandler;
import com.ninjablocks.client.driver.NinjaClient;
import com.ninjablocks.client.driver.NinjaDevice;
import com.ninjablocks.client.driver.NinjaDriver;

public class ElliotsTestDriver extends BaseNinjaDriver implements NinjaDriver {
	
	public ElliotsTestDriver(NinjaClient app) {
		app.on("client::up", new JsonEventHandler() {

			public void handle(String event, ArrayNode args) {
				System.err.println("Client has started!");
				final NinjaDevice d = new ElliotsTestDevice();
				register(d);
				
				new Thread(new Runnable() {

					public void run() {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						Map<String, Object> s = new HashMap<String, Object>();
						s.put("hello", "world");
						s.put("SOMETHING", 123);
						
						d.emit("data", s);
					}
					
				}).run();
			}
			
		});
	}
	
}
