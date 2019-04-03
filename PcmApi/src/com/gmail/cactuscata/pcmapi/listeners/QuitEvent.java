package com.gmail.cactuscata.pcmapi.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import com.gmail.cactuscata.pcmapi.staff.DisplayTag;

public class QuitEvent implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {

		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getMainScoreboard();
		Player player = event.getPlayer();

		Team team = board.getTeam(player.getName());

		if (team != null)
			team.unregister();

		if (DisplayTag.getMap().containsKey(player)) {
			team = board.getTeam(DisplayTag.getMap().get(player).getPower() + player.getName());
			if (team != null)
				team.unregister();
		}

	}

}
