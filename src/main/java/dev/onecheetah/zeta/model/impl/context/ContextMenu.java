package dev.onecheetah.zeta.model.impl.context;

import java.util.Map;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import net.dv8tion.jda.api.interactions.DiscordLocale;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Data
public abstract class ContextMenu {

  private final Property property;

  @EqualsAndHashCode(onlyExplicitlyIncluded = true)
  @RequiredArgsConstructor(staticName = "of")
  @Accessors(chain = true, makeFinal = true)
  @Data
  public static final class Property {

    @EqualsAndHashCode.Include
    private final String name;
    private DefaultMemberPermissions permissions = DefaultMemberPermissions.ENABLED;
    private boolean guildOnly = true;
    private boolean NSFW = false;
    private Map<DiscordLocale, String> nameLocalizations = Map.of();
  }
}
