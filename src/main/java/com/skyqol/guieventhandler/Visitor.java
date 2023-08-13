package com.skyqol.guieventhandler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.skyqol.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;

public class Visitor {
	
	final static String[] visitorNameList = {"Adventurer", "Alchemist", "Andrew", "Anita", "Arthur", "Banker Broadjaw", "Bartender", "Beth", "Clerk Seraphine", "Dalbrek", "Duke", "Dusk", "Emissary Carlton", "Emissary Ceanna" , "Emissary Fraiser", "Emissary Sisko", "Emissary Wilson", "Farmer Jon", "Farmhand", "Fear Mongerer", "Felix", "Fisherman", "Fragilis", "Friendly Hiker", "Geonathan Greatforge", "Gimley", "Gold Forger", "Grandma Wolf", "Guy", "Gwendolyn", "Hornum", "Hungry Hiker", "Iron Forger", "Jack", "Jacob", "Jamie", "Jerry", "Jotraeline Greatforge", "Lazy Miner", "Leo", "Liam", "Librarian", "Lumberjack", "Lumina", "Lynn", "Madame Eleanor Q. Goldsworth III", "Mason", "Odawa", "Old Man Garry", "Oringo", "Pete", "Plumber Joe", "Puzzler", "Queen Mismyla", "Rhys", "Royal Resident", "Royal Resident (Neighbor)", "Royal Resident (Snooty)", "Rusty", "Ryu", "Sargwyn", "Seymour", "Shaggy", "Shifty", "Sirius", "Spaceman", "Stella", "Tammy", "Tarwen", "Terry", "Tia the Fairy", "Tom", "Trevor", "Vex", "Weaponsmith", "Wizard", "Xalx", "Zog"};
	final static char[] visitorRarity = {'U', 'U', 'U', 'U', 'U', 'R', 'R', 'L', 'L', 'R', 'U', 'U', 'U', 'U', 'U', 'R', 'U', 'U', 'U', 'U', 'U', 'U', 'R', 'U', 'U', 'U', 'R', 'R', 'U', 'R', 'U', 'U', 'R', 'U', 'U', 'U', 'L', 'U', 'R', 'U', 'U', 'U', 'U', 'R', 'U', 'L', 'U', 'U', 'R', 'U', 'R', 'U', 'R', 'R', 'U', 'R', 'U', 'U', 'R', 'U', 'U', 'R', 'U', 'R', 'L', 'S', 'U', 'R', 'U', 'U', 'R', 'U', 'U', 'U', 'U', 'U', 'U', 'R'};
	
	private static LinkedList<LinkedList<String>> visitorList = new LinkedList<>();
	
	private static String getVisitor(String currentGui) {
		int index = -1;
		if (currentGui.contains("Bazaar")) {
			return "Bazaar";
		}
       	for (String visitor : visitorNameList) {
           	index++;
       		if (visitor.equals(currentGui)) {
       			ChatComponentText coloredMessage;
               	switch (visitorRarity[index]) {
              	case 'U':
               		return (EnumChatFormatting.GREEN + currentGui);
              	case 'R':
               		return (EnumChatFormatting.BLUE + currentGui);
              	case 'L':
              		return (EnumChatFormatting.GOLD + currentGui);
              	case 'S':
               		return (EnumChatFormatting.DARK_RED + currentGui);
        		}
        	} 
		}
       	return "";
	}

	private static String getOffer(GuiOpenEvent event) {
        IInventory chestInventory = ((ContainerChest) ((GuiChest) event.gui).inventorySlots).getLowerChestInventory();
        ItemStack itemStack = chestInventory.getStackInSlot(29);
        if (itemStack != null && itemStack.stackSize > 0) {
		    	// Get the Item from the ItemStack
		    	Item item = itemStack.getItem();
		    	List<String> tooltipLines = itemStack.getTooltip(null, false);
		    	for (int i = 0; i < tooltipLines.size(); i++) {
		    		if (i > 0 && utils.cleanColor(utils.cleanDuplicateColors(tooltipLines.get(i-1))).contains("Items Required")) {
		    			String line = tooltipLines.get(i);
		    			return(line);
		    		}
		    	}
		} 
		return "";
	}
	
	public static boolean addVisitor(GuiOpenEvent event, ContainerChest container, GuiChest chest) {
        LinkedList<String> visitorInfo = new LinkedList<>();
        // Prevent already existing visitors from being added to the list
        for (LinkedList<String> innerList : visitorList) {
        	for (String data : innerList) {
        		if (data.contains(container.getLowerChestInventory().getDisplayName().getUnformattedText())) {
                    return false;
                }
        	}
        }

        // get visitor, offer, and raw offer
        String visitor = Visitor.getVisitor(container.getLowerChestInventory().getDisplayName().getUnformattedText()); 
        String offer = Visitor.getOffer(event);
        // parse offer so it can be used to scrape bazaar
        String offerParsed = ((utils.cleanColor(utils.cleanDuplicateColors(Visitor.getOffer(event)))).replaceAll("^\\s+|\\s+$|\\p{C}", ""));
		if (offerParsed.lastIndexOf("x[0-9]") > 0) {
			offerParsed = offerParsed.substring(0, offerParsed.lastIndexOf("x[0-9]"));
		}
		if (visitor.contains("Bazaar")) {
			return true;
		} else if (!visitor.equals("") && !offer.equals("") && !offerParsed.equals("")) {
//	        utils.log(visitor + " " + offer + " " + offerParsed);
        	visitorInfo.add(visitor);
        	visitorInfo.add(offer);
            visitorInfo.add(offerParsed);
            visitorList.add(visitorInfo);
            utils.log("Added New Visitor: " + visitor);
        }
		return false;
	}
	
	public static LinkedList<LinkedList<String>> getVisitorList() {
		return visitorList;
	}
	
	public static void removeVisitor(String visitor) {
        for (LinkedList<String> innerList : visitorList) {
        	if ((innerList.getFirst()).contains(visitor)) {
        		visitorList.remove(innerList);
        	}
        }
	}
}