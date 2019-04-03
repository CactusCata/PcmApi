package com.gmail.cactuscata.pcmapi.api;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MenuSystem {

	private static Map<Class<? extends CustomInventory>, CustomInventory> registeredMenus = new HashMap<>();

	public static void addMenu(CustomInventory m) {
		registeredMenus.put(m.getClass(), m);
	}

	public static void open(Player player, Class<? extends CustomInventory> gClass) {

		if (!registeredMenus.containsKey(gClass))
			return;

		CustomInventory menu = registeredMenus.get(gClass);
		Inventory inv = Bukkit.createInventory(null, menu.getSize(), menu.name());
		menu.contents(player, inv);
		player.openInventory(inv);

	}

	public static Map<Class<? extends CustomInventory>, CustomInventory> getRegisteredMenus() {
		return registeredMenus;
	}

}
