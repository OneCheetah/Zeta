package dev.onecheetah.zeta.model.impl.commands;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Data
public class ApplicationCommandCategory {

  private final ApplicationCommandCategory.Property property;

  @EqualsAndHashCode(onlyExplicitlyIncluded = true)
  @RequiredArgsConstructor(staticName = "of")
  @Accessors(chain = true, makeFinal = true)
  @Data
  public static final class Property {

    @EqualsAndHashCode.Include
    private final String name;
    @EqualsAndHashCode.Include
    private final String description;
    private DefaultMemberPermissions permissions = DefaultMemberPermissions.ENABLED;
    private boolean NSFW = false;
    private boolean guildOnly = true;
  }
}
