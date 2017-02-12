package tech.srwaggon.lings.net.message;

public class MoveMessage {

  private final int id;
  private final int x;
  private final int y;

  public MoveMessage(int id, int x, int y) {
    this.id = id;
    this.x = x;
    this.y = y;
  }

  public String convert() {
    return String.format("move id %d x %d y %d", id, x, y);
  }
}
