package com.gmail.cactuscata.pcmapi.command.other;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.gmail.cactuscata.pcmapi.enums.PrefixMessage;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;

public class TitleApi implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (args.length == 0) {
			sender.sendMessage(PrefixMessage.ERROR + "Veuillez préciser title ou actionbar !");
			return true;
		}

		if (args[0].equalsIgnoreCase("title")) {

			if (args.length == 1) {
				sender.sendMessage(PrefixMessage.ERROR + "Veuillez préciser le joueur !");
				return true;
			}

			Player player = Bukkit.getPlayerExact(args[1]);
			if (player == null || !player.isOnline()) {
				sender.sendMessage(PrefixMessage.ERROR + "Le joueur n'est pas connecté !");
				return true;
			}

			if (args.length == 2 || !isCoorectNumber(args[2])) {
				sender.sendMessage(PrefixMessage.ERROR + "Veuillez préciser le temps d'apparition !");
				return true;
			}

			if (args.length == 3 || !isCoorectNumber(args[3])) {
				sender.sendMessage(
						PrefixMessage.ERROR + "Veuillez préciser le temps pendant lequel le message restera affiché !");
				return true;
			}

			if (args.length == 4 || !isCoorectNumber(args[4])) {
				sender.sendMessage(PrefixMessage.ERROR + "Veuillez préciser le temps de disparition !");
				return true;
			}

			if (args.length == 5) {
				sender.sendMessage(PrefixMessage.ERROR + "Veuillez préciser unn message !");
				return true;
			}

			String contract = "";
			for (int i = 5, j = args.length; i < j; i++) {
				contract += args[i];
			}

			if (!contract.contains("<nl>")) {
				sender.sendMessage(PrefixMessage.ERROR
						+ "Veuillez mettre l'armument <nl> pour déterminer la partie titre et la partie sous-titres !");
				return true;
			}

			String[] title = contract.split("<nl>");
			sendTitle(player, Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]),
					title[0].replace('&', '§'), title[1].replace('&', '§'));
			sender.sendMessage(PrefixMessage.PREFIX + "Le title a bien été envoyé !");
			return true;

		} else if (args[0].equalsIgnoreCase("actionbar")) {

			if (args.length == 1) {
				sender.sendMessage(PrefixMessage.ERROR + "Veuillez préciser le message !");
				return true;
			}

			Player player = Bukkit.getPlayer(args[1]);
			if (player == null || !player.isOnline()) {
				sender.sendMessage(PrefixMessage.ERROR + "Le joueur n'est pas en ligne !");
				return true;
			}

			if (args.length == 2) {
				sender.sendMessage(PrefixMessage.ERROR + "Veuillez préciser le message à envoyer !");
				return true;
			}

			String str = "";
			for (int i = 3, j = args.length; i < j; i++) {
				str += args[i];
			}

			sendActionBarre(player, str);
			sender.sendMessage(PrefixMessage.PREFIX + "Le joueur a bien reçu l'actionbar !");
			return true;

		}

		sender.sendMessage(PrefixMessage.ERROR + "Veuillez préciser title ou actionbar comme premier argument !");
		return true;
	}

	public static void sendTitle(Player player, int before, int on, int after, String title, String subtitle) {
		PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(EnumTitleAction.TITLE,
				ChatSerializer.a("{\"text\":\"" + title + "\"}"), before, on, after);
		PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE,
				ChatSerializer.a("{\"text\":\"" + subtitle + "\"}"), before, on, after);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(titlePacket);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(subtitlePacket);
	}

	public static void sendActionBarre(Player player, String message) {
		IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
		PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(cbc, (byte) 2);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutChat);
	}

	private boolean isCoorectNumber(String falseNumber) {
		try {
			@SuppressWarnings("unused")
			int i = Integer.parseInt(falseNumber) * 2;
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
