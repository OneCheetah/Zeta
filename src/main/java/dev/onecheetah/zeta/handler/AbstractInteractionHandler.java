package dev.onecheetah.zeta.handler;

import dev.onecheetah.zeta.util.Reflections;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public sealed abstract class AbstractInteractionHandler<T>
    permits ApplicationCommandHandler, ButtonInteractionHandler, ContextMenuInteractionHandler,
    ModalInteractionHandler, SelectMenuInteractionHandler {

  protected final Set<CommandData> REGISTRY = new HashSet<>();

  private final String[] PACKAGE_NAME;
  private final Class<T> TYPE;

  protected AbstractInteractionHandler(final Class<T> TYPE, final String... PACKAGE_NAME) {
    this.PACKAGE_NAME = PACKAGE_NAME;
    this.TYPE = TYPE;
  }

  protected List<? extends Constructor<? extends T>> extract() {
    try (final ScanResult RESULT = new ClassGraph().enableClassInfo()
        .acceptPackages(PACKAGE_NAME).scan()) {
      return RESULT
          .getSubclasses(TYPE)
          .filter(info -> !info.isExternalClass())
          .filter(info -> !info.isAbstract())
          .loadClasses(TYPE)
          .stream()
          .map(Reflections::getNoArgConstructor)
          .toList();
    }
  }

  public abstract Map<String, Constructor<? extends T>> process();

  public final Optional<Set<CommandData>> getRegistry() {
    if (REGISTRY.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(REGISTRY);
  }
}
