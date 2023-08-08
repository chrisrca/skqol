package com.skyqol.guieventhandler;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;

import com.skyqol.utils;

public class GuiOpenListener {
	
	final String[] visitorList = {"Adventurer", "Alchemist", "Andrew", "Anita", "Arthur", "Banker Broadjaw", "Bartender", "Beth", "Clerk Seraphine", "Dalbrek", "Duke", "Dusk", "Emissary Carlton", "Emissary Ceanna" , "Emissary Fraiser", "Emissary Sisko", "Emissary Wilson", "Farmer Jon", "Farmhand", "Fear Mongerer", "Felix", "Fisherman", "Fragilis", "Friendly Hiker", "Geonathan Greatforge", "Gimley", "Gold Forger", "Grandma Wolf", "Guy", "Gwendolyn", "Hornum", "Hungry Hiker", "Iron Forger", "Jack", "Jacob", "Jamie", "Jerry", "Jotraeline Greatforge", "Lazy Miner", "Leo", "Liam", "Librarian", "Lumberjack", "Lumina", "Lynn", "Madame Eleanor Q. Goldsworth III", "Mason", "Odawa", "Old Man Garry", "Oringo", "Pete", "Plumber Joe", "Puzzler", "Queen Mismyla", "Rhys", "Royal Resident", "Royal Resident (Neighbor)", "Royal Resident (Snooty)", "Rusty", "Ryu", "Sargwyn", "Seymour", "Shaggy", "Shifty", "Sirius", "Spaceman", "Stella", "Tammy", "Tarwen", "Terry", "Tia the Fairy", "Tom", "Trevor", "Vex", "Weaponsmith", "Wizard", "Xalx", "Zog"};
	final char[] visitorRarity = {'U', 'U', 'U', 'U', 'U', 'R', 'R', 'L', 'L', 'R', 'U', 'U', 'U', 'U', 'U', 'R', 'U', 'U', 'U', 'U', 'U', 'U', 'R', 'U', 'U', 'U', 'R', 'R', 'U', 'R', 'U', 'U', 'R', 'U', 'U', 'U', 'L', 'U', 'R', 'U', 'U', 'U', 'U', 'R', 'U', 'L', 'U', 'U', 'R', 'U', 'R', 'U', 'R', 'R', 'U', 'R', 'U', 'U', 'R', 'U', 'U', 'R', 'U', 'R', 'L', 'S', 'U', 'R', 'U', 'U', 'R', 'U', 'U', 'U', 'U', 'U', 'U', 'R'};
	
	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent event) {
		String currentlyOpenChestName;
		if (event.gui instanceof GuiChest) {
			GuiChest chest = (GuiChest) event.gui;
			ContainerChest container = (ContainerChest) chest.inventorySlots;

			currentlyOpenChestName = container.getLowerChestInventory().getDisplayName().getUnformattedText();
			System.out.println(currentlyOpenChestName);
			
			Scoreboard scoreboard = Minecraft.getMinecraft().thePlayer.getWorldScoreboard();
    		
    		ScoreObjective sidebarObjective = scoreboard.getObjectiveInDisplaySlot(1);
    		List<Score> scores = new ArrayList<>(scoreboard.getSortedScores(sidebarObjective));
    		for (int i = scores.size() - 1; i >= 0; i--) {
    			Score score = scores.get(i);
    			ScorePlayerTeam scoreplayerteam1 = scoreboard.getPlayersTeam(score.getPlayerName());
    			String line = ScorePlayerTeam.formatPlayerName(scoreplayerteam1, score.getPlayerName());
    			line = utils.cleanDuplicateColourCodes(line);
    			String cleanLine = utils.cleanColour(line);
    			if (line.contains("The Garden")) {
    				int index = -1;
    		        for (String visitor : visitorList) {
    		            index++;
    		        	if (visitor.equals(currentlyOpenChestName)) {
    		        		ChatComponentText coloredMessage;
    		                switch (visitorRarity[index]) {
    		                case 'U':
    		                	Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Opened " + EnumChatFormatting.GREEN + currentlyOpenChestName));
    		                    break;
    		                case 'R':
    		                	Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Opened " + EnumChatFormatting.BLUE + currentlyOpenChestName));
    		                    break;
    		                case 'L':
    		                	Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Opened " + EnumChatFormatting.GOLD + currentlyOpenChestName));
    		                    break;
    		                case 'S':
    		                	Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Opened " + EnumChatFormatting.DARK_RED + currentlyOpenChestName));
    		                    break;
    		                }
    		                break;  
    		        	}
    		        }
    			} 
    		}
		} else {
			currentlyOpenChestName = "";
		}
	}
}
