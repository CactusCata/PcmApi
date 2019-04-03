package com.gmail.cactuscata.pcmapi.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import com.gmail.cactuscata.pcmapi.utils.SendTab;

public class Other implements Listener {

	@EventHandler
	public final void worldChange(PlayerChangedWorldEvent event) {
		SendTab.sendtab(event.getPlayer());
	}

}
