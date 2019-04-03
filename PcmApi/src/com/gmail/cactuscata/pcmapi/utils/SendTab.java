package com.gmail.cactuscata.pcmapi.utils;

import java.io.File;
import java.lang.reflect.Field;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.gmail.cactuscata.pcmapi.PcmApi;

import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;

public class SendTab {

	public static void sendtab(Player player){
		
		File file = new File(PcmApi.getInstance().getDataFolder(), "tabtext.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

		String headerText = "[\"\",{\"text\":\"";
		String footherText = "[\"\",{\"text\":\"";

		for (String text : config.getStringList("Header"))
			headerText += text + "§r\n";

		for (String text : config.getStringList("Foother"))
			footherText += text + "§r\n";

		headerText += "\"}]";
		footherText += "\"}]";

		PacketPlayOutPlayerListHeaderFooter headerfooter = new PacketPlayOutPlayerListHeaderFooter();
		try {
			Field header = headerfooter.getClass().getDeclaredField("a");
			Field footer = headerfooter.getClass().getDeclaredField("b");
			header.setAccessible(true);
			footer.setAccessible(true);
			header.set(headerfooter, ChatSerializer.a(headerText.replace('&', '§').replace("§ ", "& ")));
			footer.set(headerfooter, ChatSerializer.a(footherText.replace('&', '§').replace("§ ", "& ")));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(headerfooter);	
	}

}
