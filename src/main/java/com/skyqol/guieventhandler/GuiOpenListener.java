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
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
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
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
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
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.skyqol.guieventhandler.*;

import java.util.Collections;
import java.util.List;

import com.skyqol.utils;

public class GuiOpenListener {
			
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onItemTooltip(GuiScreenEvent.DrawScreenEvent.Pre event) {
		if (event.gui instanceof GuiChest && event.gui != null) {
			GuiChest chest = (GuiChest) event.gui;
	        Container container = chest.inventorySlots; // Get the container
	                    
			if (container instanceof ContainerChest) {
				ContainerChest chestContainer = (ContainerChest) container;
				
				int x = (chest.width - 176) / 2;
				int y = 0;
				
				if (((ContainerChest) container).getLowerChestInventory().getSizeInventory() / 9 == 6) {
					y = (chest.height - 222) / 2; 				
				} else {
					y = (chest.height - 167) / 2; 
				}
		
				Slot slot = chestContainer.getSlot(1);
									
				utils.drawItemStack(event, chest, new ItemStack(Item.getItemFromBlock(Blocks.stained_glass_pane), 1, 5), slot, x + slot.xDisplayPosition, y + slot.yDisplayPosition);
			}
		}
	}
    
	@SubscribeEvent
	public void onGuiOpen(final GuiOpenEvent event) {
		String currentGui;
		// Check type of gui
		if (event.gui == null) {
			return;
		}
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
		      
			// Save current gui
			GuiChest chest = (GuiChest) event.gui;
			ContainerChest container = (ContainerChest) chest.inventorySlots;
			// Get name of current gui
			currentGui = container.getLowerChestInventory().getDisplayName().getUnformattedText();
			System.out.println(currentGui);
							
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(utils.getLocation()));
						
		} else {
			currentGui = "";
		}
	}
}