package tech.srwaggon.lings.net.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ServerConfiguration {

  @Bean
  public ServerSocket getServerSocket() throws IOException {
    return new ServerSocket(31337);
  }

  @Bean
  public ExecutorService getExecutorService() {
    return Executors.newCachedThreadPool();
  }
}
