package dev.onecheetah.zeta.model.impl.context;

import dev.onecheetah.zeta.model.base.Interaction;
import lombok.EqualsAndHashCode;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;

@EqualsAndHashCode(callSuper = true)
public abstract class MessageContextMenu extends ContextMenu implements
    Interaction<MessageContextInteractionEvent> {

  public MessageContextMenu(Property property) {
    super(property);
  }
}
