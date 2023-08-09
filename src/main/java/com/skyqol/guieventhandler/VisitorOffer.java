package com.skyqol.guieventhandler;

import com.skyqol.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class VisitorOffer {
	final static String[] visitorList = {"Adventurer", "Alchemist", "Andrew", "Anita", "Arthur", "Banker Broadjaw", "Bartender", "Beth", "Clerk Seraphine", "Dalbrek", "Duke", "Dusk", "Emissary Carlton", "Emissary Ceanna" , "Emissary Fraiser", "Emissary Sisko", "Emissary Wilson", "Farmer Jon", "Farmhand", "Fear Mongerer", "Felix", "Fisherman", "Fragilis", "Friendly Hiker", "Geonathan Greatforge", "Gimley", "Gold Forger", "Grandma Wolf", "Guy", "Gwendolyn", "Hornum", "Hungry Hiker", "Iron Forger", "Jack", "Jacob", "Jamie", "Jerry", "Jotraeline Greatforge", "Lazy Miner", "Leo", "Liam", "Librarian", "Lumberjack", "Lumina", "Lynn", "Madame Eleanor Q. Goldsworth III", "Mason", "Odawa", "Old Man Garry", "Oringo", "Pete", "Plumber Joe", "Puzzler", "Queen Mismyla", "Rhys", "Royal Resident", "Royal Resident (Neighbor)", "Royal Resident (Snooty)", "Rusty", "Ryu", "Sargwyn", "Seymour", "Shaggy", "Shifty", "Sirius", "Spaceman", "Stella", "Tammy", "Tarwen", "Terry", "Tia the Fairy", "Tom", "Trevor", "Vex", "Weaponsmith", "Wizard", "Xalx", "Zog"};
	final static char[] visitorRarity = {'U', 'U', 'U', 'U', 'U', 'R', 'R', 'L', 'L', 'R', 'U', 'U', 'U', 'U', 'U', 'R', 'U', 'U', 'U', 'U', 'U', 'U', 'R', 'U', 'U', 'U', 'R', 'R', 'U', 'R', 'U', 'U', 'R', 'U', 'U', 'U', 'L', 'U', 'R', 'U', 'U', 'U', 'U', 'R', 'U', 'L', 'U', 'U', 'R', 'U', 'R', 'U', 'R', 'R', 'U', 'R', 'U', 'U', 'R', 'U', 'U', 'R', 'U', 'R', 'L', 'S', 'U', 'R', 'U', 'U', 'R', 'U', 'U', 'U', 'U', 'U', 'U', 'R'};

	public static void VisitorHelper(String currentGui) {
		int index = -1;
		if (currentGui.contains("Bazaar")) {
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Opened " + EnumChatFormatting.GREEN + "Bazaar"));
		} else {
        	for (String visitor : visitorList) {
            	index++;
        		if (visitor.equals(currentGui)) {
        			ChatComponentText coloredMessage;
                	switch (visitorRarity[index]) {
                	case 'U':
                		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Opened " + EnumChatFormatting.GREEN + currentGui));
                    	break;
                	case 'R':
                		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Opened " + EnumChatFormatting.BLUE + currentGui));
                    	break;
                	case 'L':
                		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Opened " + EnumChatFormatting.GOLD + currentGui));
                    	break;
                	case 'S':
                		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Opened " + EnumChatFormatting.DARK_RED + currentGui));
                    	break;
                	}
                	break;  
        		}
        	} 
        }
	}
}
