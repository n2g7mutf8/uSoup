package me.n2g7mutf8.soup.utils.chat;

import net.md_5.bungee.api.chat.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChatUtil {

    private final List<TextComponent> components = new ArrayList<>();

    public ChatUtil(String message) {
        TextComponent component = new TextComponent(ColorText.translate(message));

        components.add(component);
    }

    public ChatUtil(String message, String hover, String click) {
        this.add(message, hover, click);
    }

    public ChatUtil copy(String message, String hover, String copy) {
        TextComponent component = new TextComponent(ColorText.translate(message));

        if (hover != null) {
            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ColorText.translate(hover)).create()));
        }

        if (copy != null) {
            component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, copy));
        }

        this.components.add(component);
        return this;
    }

    private void add(String message, String hover, String click) {
        TextComponent component = new TextComponent(ColorText.translate(message));

        if (hover != null) {
            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ColorText.translate(hover)).create()));
        }

        if (click != null) {
            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, click));
        }

        this.components.add(component);
    }

    public void add(String message) {
        components.add(new TextComponent(message));
    }

    public void send(Player player) {
        BaseComponent[] msg = components.toArray(new TextComponent[0]);

        player.spigot().sendMessage(msg);
    }
}