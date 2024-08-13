package dev.onecheetah.zeta.handler;

import dev.onecheetah.zeta.model.impl.button.Button;
import dev.onecheetah.zeta.util.Reflections;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ButtonInteractionHandler extends AbstractInteractionHandler<Button> {

  private static final Logger log = LoggerFactory.getLogger(ButtonInteractionHandler.class);

  public ButtonInteractionHandler(final String... PACKAGE_NAME) {
    super(Button.class, PACKAGE_NAME);
  }

  @Override
  public Map<String, Constructor<? extends Button>> process() {
    final Map<String, Constructor<? extends Button>> DATA = new HashMap<>();
    for (final var CONSTRUCTOR : extract()) {
      final Button INSTANCE = Reflections.noArgsNewInstance(CONSTRUCTOR);
      DATA.put(INSTANCE.getProperty().getId(), CONSTRUCTOR);
      log.info("Button registered with ID of '{}'", INSTANCE.getProperty().getId());
    }
    return DATA;
  }
}
