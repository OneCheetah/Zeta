package dev.onecheetah.zeta.handler;

import dev.onecheetah.zeta.model.impl.select.EntitySelectMenu;
import dev.onecheetah.zeta.model.impl.select.SelectMenu;
import dev.onecheetah.zeta.model.impl.select.StringSelectMenu;
import dev.onecheetah.zeta.util.Reflections;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class SelectMenuInteractionHandler extends AbstractInteractionHandler<SelectMenu> {

  public static final String ENTITY_MAP_PREFIX = "!!entity@";
  public static final String STRING_MAP_PREFIX = "!!str@";

  public SelectMenuInteractionHandler(final String... PACKAGE_NAME) {
    super(SelectMenu.class, PACKAGE_NAME);
  }

  @Override
  public Map<String, Constructor<? extends SelectMenu>> process() {
    final Map<String, Constructor<? extends SelectMenu>> DATA = new HashMap<>();
    for (final var CONSTRUCTOR : extract()) {
      final SelectMenu INSTANCE = Reflections.noArgsNewInstance(CONSTRUCTOR);
      if (INSTANCE instanceof EntitySelectMenu) {
        DATA.put(ENTITY_MAP_PREFIX + INSTANCE.getProperty().getComponentId(), CONSTRUCTOR);
        log.info("Entity Select Menu registered with ID of '{}'",
            INSTANCE.getProperty().getComponentId());
      } else if (INSTANCE instanceof StringSelectMenu) {
        DATA.put(STRING_MAP_PREFIX + INSTANCE.getProperty().getComponentId(), CONSTRUCTOR);
        log.info("String Select Menu registered with ID of '{}'",
            INSTANCE.getProperty().getComponentId());
      } else {
        throw new IllegalArgumentException(
            "Unknown select menu type: " + INSTANCE.getClass().getName());
      }
    }
    return DATA;
  }
}
