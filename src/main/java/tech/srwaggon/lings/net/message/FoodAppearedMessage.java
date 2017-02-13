package tech.srwaggon.lings.net.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodAppearedMessage extends Message {
  private final String type = "food";
  private int x;
  private int y;
}
