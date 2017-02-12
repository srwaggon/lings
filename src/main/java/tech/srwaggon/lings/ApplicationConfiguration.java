package tech.srwaggon.lings;

import com.google.common.eventbus.EventBus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

  @Bean
  public EventBus getEventBus() {
    return new EventBus() {
      @Override
      public void post(Object event) {
        super.post(event);
        System.out.println("Posting: " + event);
      }
    };
  }
}
