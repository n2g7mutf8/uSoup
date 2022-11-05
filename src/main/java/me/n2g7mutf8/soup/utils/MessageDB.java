package me.n2g7mutf8.soup.utils;

import lombok.Getter;
import me.n2g7mutf8.soup.SoupPvP;
import me.n2g7mutf8.soup.utils.chat.MessageUtil;
import me.n2g7mutf8.soup.utils.configuration.Config;
import me.n2g7mutf8.soup.utils.cooldown.Cooldown;
import me.n2g7mutf8.soup.utils.player.DurationFormatter;
import org.bukkit.entity.Player;

@Getter
public class MessageDB {

    private static final Config config = SoupPvP.getInstance().getMessages();

    private final String actionDeny;
    private final String insufficientAmount;

    public MessageDB() {
        actionDeny = config.getString("Error.Insufficient-Amount");
        insufficientAmount = config.getString("Error.Action-Deny");
    }

    public static String getRemainCooldown(Player player, Cooldown cooldown) {
        return new MessageUtil()
                .setVariable("<time>", DurationFormatter.getRemaining(cooldown.getDuration(player), true))
                .format(config.getString("Error.Cooldown-Remain"));
    }
}
