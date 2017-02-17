package tech.srwaggon.lings.net;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
public class PlayerJoinedEvent {
  private Connection connection;
}
