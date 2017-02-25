package tech.srwaggon.lings;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(getHandler(), "/").withSockJS();
  }

  @Bean
  public WebSocketHandler getHandler() {
    return new TextWebSocketHandler() {
      @Override
      protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Thread.sleep(1000);
        TextMessage oi = new TextMessage("I'm a real boy!");
        session.sendMessage(oi);
      }
    };
  }

  @Bean
  public WebSocketStompClient foo() {
    WebSocketClient webSocketClient = new StandardWebSocketClient();
    WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
    stompClient.setMessageConverter(new StringMessageConverter());
    String url = "ws://127.0.0.1:8080";
    StompSessionHandler sessionHandler = bar();
    stompClient.connect(url, sessionHandler);
    return stompClient;
  }

  @Bean
  public StompSessionHandlerAdapter bar() {
    return new StompSessionHandlerAdapter() {
      @Override
      public void afterConnected(StompSession session, StompHeaders connectedHeaders) {


        session.subscribe("/topic/foo", new StompFrameHandler() {

          @Override
          public Type getPayloadType(StompHeaders headers) {
            return String.class;
          }

          @Override
          public void handleFrame(StompHeaders headers, Object payload) {
            System.out.println(payload);
          }

        });


        session.send("/topic/foo", "payload");
      }
    };
  }
}
