package tech.srwaggon.lings.world;

import com.google.common.collect.Maps;

import java.util.Map;

public class World {

  private Map<Integer, WorldMap> world = Maps.newHashMap();

  public WorldMap get(int id) {
    return world.get(id);
  }

  public void add(WorldMap map) {
    int id = world.size();
    map.setId(id);
    this.world.put(id, map);
  }

  public void tick() {
    for (WorldMap map :
        world.values()) {
      map.tick();
    }
  }
}
