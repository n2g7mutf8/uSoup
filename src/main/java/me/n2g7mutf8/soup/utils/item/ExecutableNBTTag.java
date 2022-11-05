package me.n2g7mutf8.soup.utils.item;

import lombok.AllArgsConstructor;
import me.n2g7mutf8.soup.utils.Callback;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagString;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class ExecutableNBTTag extends NBTTagString {
    private Callback<Player> callback;

    public void execute(Player player) {
        callback.callback(player);
    }

    @Override
    public NBTBase clone() {
        return new ExecutableNBTTag(callback);
    }
}