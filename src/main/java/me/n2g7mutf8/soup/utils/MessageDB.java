package me.n2g7mutf8.soup.utils;

import lombok.Getter;
import me.n2g7mutf8.soup.SoupPvP;
import me.n2g7mutf8.soup.utils.chat.ColorText;
import me.n2g7mutf8.soup.utils.chat.MessageUtil;
import me.n2g7mutf8.soup.utils.configuration.Config;
import me.n2g7mutf8.soup.utils.cooldown.Cooldown;
import me.n2g7mutf8.soup.utils.player.DurationFormatter;
import org.bukkit.entity.Player;

import java.util.HashMap;

@Getter
public class MessageDB {

    private final Config config = SoupPvP.getInstance().getMessages();
    public HashMap<String, String> messages = new HashMap<>();

    public MessageDB() {
        registerMessage("actionDeny", "Error.Action-Deny");
        registerMessage("insufficientAmount", "Error.Insufficient-Amount");
        registerMessage("insufficientPermission", "Error.Insufficient-Permission");
    }

    public String remainCooldownMessage(Player player, Cooldown cooldown) {
        return new MessageUtil().setVariable("<time>", DurationFormatter.getRemaining(cooldown.getDuration(player), true)).format(config.getString("Error.Cooldown-Remain"));
    }

    public String killstreakMessage(Player player, Player killer, int amount, boolean isEnd) {
        if (isEnd) {
            return new MessageUtil().setVariable("<victim>", player.getName()).setVariable("<killer>", killer.getName()).setVariable("<amount>", String.valueOf(amount)).format(config.getString("Streak.Streak-End"));
        } else {
            return new MessageUtil().setVariable("<player>", player.getName()).setVariable("<amount>", String.valueOf(amount)).format(config.getString("High-Streak"));
        }
    }

    public void registerMessage(String name, String path) {
        messages.put(name, ColorText.translate(config.getString(path)));
    }
}
