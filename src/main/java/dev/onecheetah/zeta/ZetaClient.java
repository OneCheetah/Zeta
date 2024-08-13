package dev.onecheetah.zeta;

import dev.onecheetah.zeta.event.ZetaEventListener;
import dev.onecheetah.zeta.handler.ApplicationCommandHandler;
import dev.onecheetah.zeta.handler.ButtonInteractionHandler;
import dev.onecheetah.zeta.handler.ContextMenuInteractionHandler;
import dev.onecheetah.zeta.handler.ModalInteractionHandler;
import dev.onecheetah.zeta.handler.SelectMenuInteractionHandler;
import dev.onecheetah.zeta.handler.util.ExceptionHandler;
import dev.onecheetah.zeta.handler.util.Registry;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

@Slf4j
public final class ZetaClient {

  private final JDABuilder BUILDER;

  private ApplicationCommandHandler applicationCommandHandler;
  private ButtonInteractionHandler buttonHandler;
  private ContextMenuInteractionHandler contextMenuHandler;
  private ModalInteractionHandler modalHandler;
  private SelectMenuInteractionHandler selectMenuHandler;

  private ExceptionHandler exceptionHandler;

  private ZetaClient(final JDABuilder BUILDER) {
    this.BUILDER = BUILDER;
  }

  public static ZetaClient with(final JDABuilder BUILDER) {
    return new ZetaClient(BUILDER);
  }

  public ZetaClient addApplicationCommandHandler(
      ApplicationCommandHandler applicationCommandHandler) {
    this.applicationCommandHandler = applicationCommandHandler;
    return this;
  }

  public ZetaClient addApplicationCommandHandler(
      String packageName) {
    this.applicationCommandHandler = new ApplicationCommandHandler(packageName);
    return this;
  }

  public ZetaClient addButtonHandler(ButtonInteractionHandler buttonHandler) {
    this.buttonHandler = buttonHandler;
    return this;
  }

  public ZetaClient addButtonHandler(String packageName) {
    this.buttonHandler = new ButtonInteractionHandler(packageName);
    return this;
  }

  public ZetaClient addContextMenuHandler(ContextMenuInteractionHandler contextMenuHandler) {
    this.contextMenuHandler = contextMenuHandler;
    return this;
  }

  public ZetaClient addContextMenuHandler(String packageName) {
    this.contextMenuHandler = new ContextMenuInteractionHandler(packageName);
    return this;
  }

  public ZetaClient addModalHandler(ModalInteractionHandler modalHandler) {
    this.modalHandler = modalHandler;
    return this;
  }

  public ZetaClient addModalHandler(String packageName) {
    this.modalHandler = new ModalInteractionHandler(packageName);
    return this;
  }

  public ZetaClient addSelectMenuHandler(SelectMenuInteractionHandler selectMenuHandler) {
    this.selectMenuHandler = selectMenuHandler;
    return this;
  }

  public ZetaClient addSelectMenuHandler(String packageName) {
    this.selectMenuHandler = new SelectMenuInteractionHandler(packageName);
    return this;
  }

  public ZetaClient setExceptionHandler(ExceptionHandler exceptionHandler) {
    this.exceptionHandler = exceptionHandler;
    return this;
  }

  public JDA start() throws InterruptedException {
    Registry registry = new Registry();
    List<CommandData> data = new ArrayList<>();
    if (applicationCommandHandler != null) {
      registry.setApplicationCommands(applicationCommandHandler.process());
      if (applicationCommandHandler.getRegistry().isPresent()) {
        data.addAll(applicationCommandHandler.getRegistry().get());
      }
    }
    if (buttonHandler != null) {
      registry.setButtons(buttonHandler.process());
    }
    if (contextMenuHandler != null) {
      registry.setContexts(contextMenuHandler.process());
      if (contextMenuHandler.getRegistry().isPresent()) {
        data.addAll(contextMenuHandler.getRegistry().get());
      }
    }
    if (modalHandler != null) {
      registry.setModals(modalHandler.process());
    }
    if (selectMenuHandler != null) {
      registry.setSelectMenus(selectMenuHandler.process());
    }
    if (exceptionHandler == null) {
      exceptionHandler = new ExceptionHandler() {
      };
    }
    final JDA JDA = BUILDER
        .addEventListeners(new ZetaEventListener(registry, exceptionHandler))
        .build()
        .awaitReady();

    JDA.updateCommands()
        .addCommands(data)
        .queue();
    return JDA;
  }
}
