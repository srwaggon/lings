package tech.srwaggon.lings.net.message;

import lombok.Data;

@Data
public abstract class Message {
  private int id;
  public abstract String getType();
}
