package tech.srwaggon.lings.entity;

import com.google.common.eventbus.EventBus;

import org.codehaus.jackson.annotate.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import tech.srwaggon.lings.game.Game;
import tech.srwaggon.lings.net.message.action.EatMessage;
import tech.srwaggon.lings.net.message.action.MoveMessage;
import tech.srwaggon.lings.world.Tile;

public class Agent extends Entity {

  private static int ids = 0;
  @Getter
  private final int id;
  @Setter
  private Game game;
  @Setter
  private EventBus eventBus;
  @Getter
  @Setter
  private int energy;

  public Agent() {
    this.id = ids++;
  }

  public Agent(int id) {
    this.id = id;
  }

  public static int getIds() {
    return ids;
  }

  public void eat() {
    energy += getTile().consume() ? 1 : 0;
    eventBus.post(EatMessage.builder().id(id).build());
  }

  @JsonIgnore
  public Tile getTile() {
    return this.game.getWorld().getTile(x, y);
  }

  public void move(int x, int y) {
    if (isValidMove(x, y)) {
    int xx = x % game.getWorld().getNumColumns();
    int yy = y % game.getWorld().getNumRows();
      actuallyMove(xx, yy);
    }
  }

  private boolean isValidMove(int x, int y) {
    int dx = Math.abs(this.x - x);
    int dy = Math.abs(this.y - y);
    return dx + dy <= 1;
  }

  private void actuallyMove(int x, int y) {
    getTile().occupy(null);
    setX(x);
    setY(y);
    getTile().occupy(this);
    eventBus.post(MoveMessage.builder().id(id).x(x).y(y).build());
  }

  public String getType() {
    return "agent";
  }
}
