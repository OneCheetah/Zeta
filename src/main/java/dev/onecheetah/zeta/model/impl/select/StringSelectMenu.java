package dev.onecheetah.zeta.model.impl.select;

import dev.onecheetah.zeta.model.base.Interaction;
import lombok.EqualsAndHashCode;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

@EqualsAndHashCode(callSuper = true)
public abstract class StringSelectMenu extends SelectMenu implements
    Interaction<StringSelectInteractionEvent> {

  public StringSelectMenu(Property property) {
    super(property);
  }
}
