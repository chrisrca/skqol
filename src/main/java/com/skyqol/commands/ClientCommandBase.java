package com.skyqol.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public abstract class ClientCommandBase extends CommandBase {
	private final String name;

	protected ClientCommandBase(String name) {
		this.name = name;
	}

	public String getCommandName() {
		return name;
	}

	public String getCommandUsage(ICommandSender sender) {
		return "/" + name;
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}
}