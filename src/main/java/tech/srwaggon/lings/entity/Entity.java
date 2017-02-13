package tech.srwaggon.lings.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Entity {
  int x = 0;
  int y = 0;
}
