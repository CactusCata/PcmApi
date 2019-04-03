package com.gmail.cactuscata.pcmapi.enums;

public enum PrefixMessage {

	PREFIX("§5§l[§3Pcm§5§l]§a "),
	ERROR("§c[Pcm] ");

	private final String message;

	private PrefixMessage(final String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return this.message;
	}

}
