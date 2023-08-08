package com.skyqol.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class SettingsCommand extends ClientCommandBase {

	public SettingsCommand() {
		super("skyqol");
	}

	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Settings!"));
	}
}