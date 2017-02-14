package tech.srwaggon.lings.net.message;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

import lombok.Data;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ActionMessage.class, name = "action"),
    @JsonSubTypes.Type(value = FoodAppearedMessage.class, name = "food"),
    @JsonSubTypes.Type(value = IdMessage.class, name = "id"),
    @JsonSubTypes.Type(value = MapMessage.class, name = "map")
})
public abstract class Message {
  public abstract String getType();
}
