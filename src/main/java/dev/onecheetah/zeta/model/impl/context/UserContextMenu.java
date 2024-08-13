package dev.onecheetah.zeta.model.impl.context;

import dev.onecheetah.zeta.model.base.Interaction;
import lombok.EqualsAndHashCode;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;

@EqualsAndHashCode(callSuper = true)
public abstract class UserContextMenu extends ContextMenu implements
    Interaction<UserContextInteractionEvent> {

  public UserContextMenu(Property property) {
    super(property);
  }
}
