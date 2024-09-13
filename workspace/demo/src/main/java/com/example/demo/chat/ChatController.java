package com.example.demo.chat;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint(value = "/chat/{userid}")
public class ChatController {
	private static Set<Session> clients = Collections.synchronizedSet(new HashSet<>());
	private static Map<Session, String> clients_id = Collections.synchronizedMap(new HashMap<>());
	
	@OnMessage
	public void onMessage(String message, Session session) throws IOException{
		synchronized (clients) {
			for(Session client : clients) {
				if(!client.equals(session)) {
					System.out.println("Send '"+message+"' To "+clients_id.get(client));
					client.getBasicRemote().sendText(message);
				}
			}
		}
	}
	@OnOpen
	public void onOpen(Session session, @PathParam(value="userid")String userid) throws IOException {
		System.out.println("접속 : "+session+" / 아이디 : "+userid);
		synchronized (clients) {
			for(Session client : clients) {
				client.getBasicRemote().sendText("in:"+userid);
			}
		}
		clients.add(session);
		clients_id.put(session, userid);
	}
	@OnClose
	public void onClose(Session session) throws IOException {
		System.out.println("종료 : "+session+" / 아이디 : "+clients_id.get(session));
		synchronized (clients) {
			for(Session client : clients) {
				if(!client.equals(session)) {
					client.getBasicRemote().sendText("out:"+clients_id.get(session));
				}
			}
		}
	}
}








