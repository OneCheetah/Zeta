package dev.onecheetah.zeta.handler;

import dev.onecheetah.zeta.model.impl.modal.Modal;
import dev.onecheetah.zeta.util.Reflections;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ModalInteractionHandler extends AbstractInteractionHandler<Modal> {

  public ModalInteractionHandler(final String... PACKAGE_NAME) {
    super(Modal.class, PACKAGE_NAME);
  }

  @Override
  public Map<String, Constructor<? extends Modal>> process() {
    final Map<String, Constructor<? extends Modal>> DATA = new HashMap<>();
    for (final var CONSTRUCTOR : extract()) {
      final Modal INSTANCE = Reflections.noArgsNewInstance(CONSTRUCTOR);
      DATA.put(INSTANCE.getProperty().getId(), CONSTRUCTOR);
      log.info("Modal registered with ID of '{}'", INSTANCE.getProperty().getId());
    }
    return DATA;
  }
}
