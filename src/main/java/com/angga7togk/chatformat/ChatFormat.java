package com.angga7togk.chatformat;

import com.angga7togk.placeholderapi.PlaceholderAPI;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.plugin.PluginBase;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;

public class ChatFormat extends PluginBase implements Listener {

    private LuckPerms luckPerms;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        // cache LuckPerms
        this.luckPerms = LuckPermsProvider.get();

        // register event
        this.getServer().getPluginManager().registerEvents(this, this);

        this.getServer().getScheduler().scheduleRepeatingTask(
                new FormatTask(this), 20 * 3);
    }

    public String getChatFormat(String rank) {
        String def = this.getConfig().getString("chat-format.default");
        return this.getConfig().getString("chat-format." + rank, def);
    }

    public String getNametagFormat(String rank) {
        String def = this.getConfig().getString("nametag-format.default");
        return this.getConfig().getString("nametag-format." + rank, def);
    }

    public String getScoretagFormat(String rank) {
        String def = this.getConfig().getString("scoretag-format.default");
        return this.getConfig().getString("scoretag-format." + rank, def);
    }

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String msg = event.getMessage();
        String rankName = "default";

        User user = luckPerms.getUserManager().getUser(player.getUniqueId());
        if (user != null) {
            rankName = user.getPrimaryGroup();
        }

        String format = this.getChatFormat(rankName)
                .replace("%msg%", msg);

        event.setFormat(
                PlaceholderAPI.get().translate(player, format));
    }
}
