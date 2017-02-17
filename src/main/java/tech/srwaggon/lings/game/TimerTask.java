package tech.srwaggon.lings.game;

public abstract class TimerTask {

  public abstract void tick();

  public boolean isExpired() {
    return false;
  }
}
