package tech.srwaggon.lings;

import com.google.common.eventbus.EventBus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;

import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ApplicationConfiguration {

  private final Logger logger = LoggerFactory.getLogger(ApplicationConfiguration.class);

  @Bean(name = "gameEventBus")
  public EventBus getGameEventBus() {
    return new EventBus() {
      @Override
      public void post(Object event) {
        super.post(event);
        logger.debug("Posting: " + event);
      }
    };
  }

  @Bean
  public ExecutorService getExecutorService() {
    return Executors.newFixedThreadPool(4);
  }

}
