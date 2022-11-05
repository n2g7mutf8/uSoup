package me.n2g7mutf8.soup.listener.list;

import me.n2g7mutf8.soup.utils.chat.ColorText;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignListener implements Listener {

    @EventHandler
    public void onSignCreate(SignChangeEvent event) {
        Player player = event.getPlayer();
        if (player.isOp()) {
            String[] lines = event.getLines();
            for (int i = 0; i < lines.length; i++) {
                event.setLine(i, ColorText.translate(lines[i]));
            }
        }
    }

}
