package dev.onecheetah.zeta.model.impl.select;

import dev.onecheetah.zeta.model.base.Interaction;
import lombok.EqualsAndHashCode;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;

@EqualsAndHashCode(callSuper = true)
public abstract class EntitySelectMenu extends SelectMenu implements
    Interaction<EntitySelectInteractionEvent> {

  public EntitySelectMenu(Property property) {
    super(property);
  }
}
