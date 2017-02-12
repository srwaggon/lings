package tech.srwaggon.lings.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Connection {
  private boolean connected = false;
  private Socket socket;
  private Scanner in = null;
  private PrintWriter out = null;

  private final Logger logger = LoggerFactory.getLogger(Connection.class);

  public Connection(String ip, int port) {
    try {
      socket = new Socket(ip, port);
      in = new Scanner(socket.getInputStream());
      out = new PrintWriter(socket.getOutputStream(), true);
      connected = true;
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  public Connection(Socket socket) {
    try {
      this.socket = socket;
      in = new Scanner(socket.getInputStream());
      out = new PrintWriter(socket.getOutputStream(), true);
      connected = true;
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  @Override
  public void finalize() {
    try {
      disconnect();
      super.finalize();
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }

  public void send(String msg) {
    out.println(msg);
    out.flush();
  }

  public boolean hasNext() {
    return in.hasNext();
  }

  public boolean hasLine() {
    return in.hasNextLine();
  }

  public String read() {
    return in.next();
  }

  public String readLine() {
    return in.nextLine();
  }


  public void disconnect() {
    try {
      logger.info("Disconnecting from " + this + ".");
      if (in != null) {
        in.close();
      }
      if (out != null) {
        out.close();
      }
      if (socket != null) {
        socket.close();
      }
      connected = false;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public boolean isConnected() {
    return connected;
  }

  @Override
  public String toString() {
    return socket.getInetAddress().toString();
  }
}