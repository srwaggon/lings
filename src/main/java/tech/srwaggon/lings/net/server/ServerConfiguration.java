package tech.srwaggon.lings.net.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.ServerSocket;

@Configuration
public class ServerConfiguration {

  @Bean
  public ServerSocket getServerSocket() throws IOException {
    return new ServerSocket(31337);
  }
}
