package com.angga7togk.chatformat;

import com.angga7togk.core.Core;

import cn.nukkit.Player;
import cn.nukkit.scheduler.PluginTask;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;

public class FormatTask extends PluginTask<ChatFormat> {

    private final LuckPerms luckPerms;

    public FormatTask(ChatFormat owner) {
        super(owner);
        this.luckPerms = LuckPermsProvider.get();
    }

    @Override
    public void onRun(int currentTick) {
        this.getOwner().getConfig().reload();
        for (Player player : this.getOwner().getServer().getOnlinePlayers().values()) {
            String rankName = "default";

            User user = luckPerms.getUserManager().getUser(player.getUniqueId());
            if (user != null) {
                rankName = user.getPrimaryGroup();
            }

            String nametagFormat = this.getOwner().getNametagFormat(rankName);
            String scoretagFormat = this.getOwner().getScoretagFormat(rankName);

            String newNameTag = Core.get().getPlaceholder().translate(player, nametagFormat);
            String newScoreTag = Core.get().getPlaceholder().translate(player, scoretagFormat);

            if (!newNameTag.equals(player.getNameTag())) {
                player.setNameTag(newNameTag);
            }

            if (!newScoreTag.equals(player.getScoreTag())) {
                player.setScoreTag(newScoreTag);
            }
        }
    }
}
