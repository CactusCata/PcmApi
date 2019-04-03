package com.gmail.cactuscata.pcmapi.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.cactuscata.pcmapi.PcmApi;
import com.google.common.base.Charsets;

public class FileRestor {

	private final PcmApi plugin;

	public FileRestor(final PcmApi plugin) {
		this.plugin = plugin;
	}

	public void initialize() {
		File fileYAMLRepopblock = new File(plugin.getDataFolder(), "tabtext.yml");
		FileConfiguration configYAMLRepopblock = YamlConfiguration.loadConfiguration(fileYAMLRepopblock);
		saveAndUpdate(fileYAMLRepopblock, configYAMLRepopblock);
	}

	private void saveAndUpdate(File file, FileConfiguration config) {

		checkIfFileExist(file, config);
		try {
			config.load(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}

	}

	private void checkIfFileExist(File file, FileConfiguration config) {
		if (!file.exists()) {
			InputStream defConfigStream = plugin.getResource(file.getName());
			config.setDefaults(
					YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
			saveResource(file.getName(), false);

		}

	}

	private void saveResource(String resourcePath, boolean replace) {
		if ((resourcePath == null) || (resourcePath.equals(""))) {
			throw new IllegalArgumentException("ResourcePath cannot be null or empty");
		}

		resourcePath = resourcePath.replace('\\', '/');
		InputStream in = plugin.getResource(resourcePath);
		if (in == null) {
			throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in file");
		}

		File outFile = new File(plugin.getDataFolder(), resourcePath);
		int lastIndex = resourcePath.lastIndexOf('/');
		File outDir = new File(plugin.getDataFolder(), resourcePath.substring(0, lastIndex >= 0 ? lastIndex : 0));

		if (!outDir.exists())
			outDir.mkdirs();

		try {
			if ((!outFile.exists()) || (replace)) {
				OutputStream out = new FileOutputStream(outFile);
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.close();
				in.close();
			}
		} catch (IOException ex) {

		}

	}

}
