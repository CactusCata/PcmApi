package com.gmail.cactuscata.pcmapi.staff;

public enum StaffList {

	FONDATEUR("§c§l[Fondateur]", "§b", "fondateur", 'a'),
	ADMINISTRATEUR("§c[Admin]", "§b", "administrateur", 'b'),
	DEVELOPPEUR("§2[Dev]", "§b", "developpeur", 'c'),
	RESPONSABLE("§5[Responsable]", "§3", "responsable", 'c'),
	ANIMATEUR("§9[Animateur]", "§b", "animateur", 'd'),
	ANIMATRICE("§9[Animatrice]", "§b", "animatrice", 'e'),
	ORGANISATEUR("§9[Organisateur]", "§d", "arganisateur", 'f'),
	ORGANISATRICE("§9[Organisatrice]", "§d", "arganisatrice", 'g'),
	COMMUNITYMANAGER("§1[Commu.M]", "§d", "communityM", 'h'),
	MODERATEUR("§d[Modo]", "§3", "moderateur", 'i'),
	GUARDIAN("§a[Guardian]", "§3", "guardian", 'j'),
	GUIDE("§7[Guide]", "§3", "guide", 'k'),
	ANCIEN("§8[Ancien]", "§3", "ancien", 'l'),
	PCT("§2[PCT]", "§3", "pct", 'm'),
	AMI("§6[Ami]", "§3", "ami", 'n'),
	AUCUN("§e", "§f", "aucun", 'o'),
	NULL("null", "null", "null", 'z');

	private final String prefix;
	private final String suffix;
	private final String nameOfStaff;
	private final char power;

	private StaffList(final String prefix, final String suffix, final String nameOfStaff, final char power) {

		this.prefix = prefix;
		this.suffix = suffix;
		this.nameOfStaff = nameOfStaff;
		this.power = power;

	}

	public final String getPrefix() {
		return this.prefix;
	}

	public final String getSuffix() {
		return this.suffix;
	}

	public final String getNameOfStaff() {
		return this.nameOfStaff;
	}
	
	public char getPower() {
		return this.power;
	}

	public final boolean contains(String str) {
		for (StaffList staff : StaffList.values())
			if (staff.getNameOfStaff().equals(str))
				return true;
		return false;
	}

	public final static StaffList getStaff(String str) {
		for (StaffList staff : StaffList.values())
			if (staff.getNameOfStaff().equalsIgnoreCase(str))
				return staff;
		return StaffList.NULL;
	}
	
}
