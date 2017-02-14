package tech.srwaggon.lings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

import tech.srwaggon.lings.net.server.GameServer;

@SpringBootApplication
public class Main {

  public static void main(String[] args) throws IOException {
    ConfigurableApplicationContext context = SpringApplication.run(Main.class);

    GameServer gameServer = context.getBean(GameServer.class);
    gameServer.runOnline();
  }
}

