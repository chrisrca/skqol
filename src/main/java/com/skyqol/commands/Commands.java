package com.skyqol.commands;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Loader;

public class Commands {
	public Commands() {
		// Setting Command
		ClientCommandHandler.instance.registerCommand(new SettingsCommand());
	}
}