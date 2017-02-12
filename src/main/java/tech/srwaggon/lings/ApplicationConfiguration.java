package tech.srwaggon.lings;

import com.google.common.eventbus.EventBus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

  private final Logger logger = LoggerFactory.getLogger(ApplicationConfiguration.class);
  @Bean
  public EventBus getEventBus() {
    return new EventBus() {
      @Override
      public void post(Object event) {
        super.post(event);
        logger.debug("Posting: " + event);
      }
    };
  }
}
