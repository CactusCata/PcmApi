package com.gmail.cactuscata.pcmapi.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.gmail.cactuscata.pcmapi.utils.SendTab;

public class JoinEvent implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {

		Player player = event.getPlayer();

		SendTab.sendtab(player);

	}

}
