package com.gmail.cactuscata.pcmapi.command.other;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import com.gmail.cactuscata.pcmapi.enums.PrefixMessage;
import com.gmail.cactuscata.pcmapi.staff.DisplayTag;

public class Settag implements CommandExecutor {

	private final DisplayTag tag;

	public Settag() {
		tag = new DisplayTag();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length < 1) {
			sender.sendMessage(
					"§6- §9/settag §bset §e<§cjoueur§e> <§2prefix§e> <§2suffix§e>\n§6- §9/settag §bclear §e<§cjoueur§e>");
			return true;
		}

		if (args[0].equalsIgnoreCase("set")) {

			if (args.length < 2) {
				sender.sendMessage(PrefixMessage.ERROR + "Veuillez préciser le nom du joueur !");
				return true;
			}

			if (args.length < 3) {
				sender.sendMessage(PrefixMessage.ERROR + "Veuillez préciser le prefix !");
				return true;
			}

			if (args.length < 4) {
				sender.sendMessage(PrefixMessage.ERROR + "Veuillez préciser le suffix !");
				return true;
			}

			Player player = Bukkit.getPlayerExact(args[1]);

			if (player != null && player.isOnline()) {

				tag.createBasicRank(player, args[2].substring(0, Math.min(args[2].length(), 16)).replace('&', '§'),
						args[3].substring(0, Math.min(args[3].length(), 16)).replace('&', '§'));

				return true;

			}

			sender.sendMessage(PrefixMessage.ERROR + "Le joueur " + args[1] + " n'est pas en ligne !");
			return true;

		}

		if (args[0].equalsIgnoreCase("clear")) {

			if (args.length < 2) {
				sender.sendMessage(PrefixMessage.ERROR + "Veuillez préciser le joueur !");
				return true;
			}

			ScoreboardManager manager = Bukkit.getScoreboardManager();
			Scoreboard board = manager.getMainScoreboard();
			Player player = Bukkit.getPlayerExact(args[1]);
			if (player != null && player.isOnline()) {

				Team team = board.getTeam(player.getName());

				if (board.getTeam(player.getName()) == null) {
					sender.sendMessage(PrefixMessage.ERROR + "Le joueur n'a déjà plus de team !");
					return true;
				}

				team.unregister();
				return true;

			}

			sender.sendMessage(PrefixMessage.ERROR + "Le joueur " + args[1] + " n'est pas en ligne !");
			return true;

		}

		sender.sendMessage(PrefixMessage.ERROR + "La section " + args[0] + " n'est pas valide !");
		return true;

	}

}
