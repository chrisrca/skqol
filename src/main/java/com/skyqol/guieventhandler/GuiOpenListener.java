package com.skyqol.guieventhandler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import com.skyqol.guieventhandler.*;
import com.skyqol.utils;

public class GuiOpenListener {
	
	private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("textures/block/glass_pane_light_green.png");
	
	@SubscribeEvent
	public void onGuiOpen(final GuiOpenEvent event) {
		String currentGui;
		// Check type of gui
		if (event.gui instanceof GuiChest) {
			new Thread() {
		         @Override
		         public void run() {
		            try {
		               Thread.sleep(10);
		               IInventory chestInventory = ((ContainerChest) ((GuiChest) event.gui).inventorySlots).getLowerChestInventory();
		               ItemStack itemStack = chestInventory.getStackInSlot(29);
		               if (itemStack != null && itemStack.stackSize > 0) {
		   			    // Get the Item from the ItemStack
		   			    Item item = itemStack.getItem();
		   			    
		   			    List<String> tooltipLines = itemStack.getTooltip(null, false);

		   			    for (int i = 0; i < tooltipLines.size(); i++) {
		   			    	if (i > 0 && utils.cleanColour(utils.cleanDuplicateColourCodes(tooltipLines.get(i-1))).contains("Items Required")) {
		   			    		String line = tooltipLines.get(i);
		   			    		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(line));
		   			    		break;
		   			    	}
		   			    }
		   			    
		   			    //Minecraft.getMinecraft().displayGuiScreen(new CustomChestGUI(((ContainerChest) ((GuiChest) event.gui).inventorySlots)));
		   			    
		   			    // Get the registry name of the Item
		   			    ResourceLocation itemRegistryName = Item.itemRegistry.getNameForObject(item);

		   			    // Get the full resource location string
		   			    String itemName = itemRegistryName.toString();

		   			    // Print the item name
		   			    System.out.println("Item Name: " + itemName);
		   			} else {
		   			    System.out.println("Slot is empty");
		   			}
		               
		            } catch (InterruptedException e) {
		               e.printStackTrace();
		            }
		         }
		      }.start();
		      Minecraft.getMinecraft().displayGuiScreen(new CustomChestGUI(((ContainerChest) ((GuiChest) event.gui).inventorySlots)));
			// Save current gui
			GuiChest chest = (GuiChest) event.gui;
			ContainerChest container = (ContainerChest) chest.inventorySlots;
			// Get name of current gui
			currentGui = container.getLowerChestInventory().getDisplayName().getUnformattedText();
			System.out.println(currentGui);

			
			// Get right side scoreboard
			Scoreboard scoreboard = Minecraft.getMinecraft().thePlayer.getWorldScoreboard();
    		ScoreObjective sidebarObjective = scoreboard.getObjectiveInDisplaySlot(1);
    		List<Score> scores = new ArrayList<>(scoreboard.getSortedScores(sidebarObjective));
    		// Check each line using utils to format it to plaintext
    		for (int i = scores.size() - 1; i >= 0; i--) {
    			Score score = scores.get(i);
    			ScorePlayerTeam scoreplayerteam1 = scoreboard.getPlayersTeam(score.getPlayerName());
    			String line = ScorePlayerTeam.formatPlayerName(scoreplayerteam1, score.getPlayerName());
    			line = utils.cleanDuplicateColourCodes(line);
    			String cleanLine = utils.cleanColour(line);
    			// Check region
    			if (line.contains("The Garden")) {
    				VisitorOffer.VisitorHelper(currentGui);
    			} 
    		}
		} else {
			currentGui = "";
		}
	}
}

//Minecraft.getMinecraft().fontRendererObj.drawString(Minecraft.getMinecraft().fontRendererObj.trimStringToWidth(
//		itemName,
//		165
//	),
//	width / 2 - 74, topY + 35 + num * 22 + 1, 0xdddddd, true
//);

//Minecraft.getMinecraft().fontRendererObj.drawString(
//		"Past Searches:",
//		width / 2 - 100,
//		topY + 25 + 118 + 5,
//		0xdddddd,
//		true
//	);
