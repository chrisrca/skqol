package com.skyqol.guieventhandler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
import java.util.LinkedList;
import java.util.List;

import com.skyqol.utils;

public class GuiOpenListener {
			
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onItemTooltip(GuiScreenEvent.DrawScreenEvent.Pre event) {
		if (event.gui instanceof GuiChest && event.gui != null) {
			GuiChest chest = (GuiChest) event.gui; // Get the GuiChest
	        Container container = chest.inventorySlots; // Get the Container
	        
			if (container instanceof ContainerChest) {
				ContainerChest chestContainer = (ContainerChest) container;
				 // Get Gui Width and Height (This is necessary for rendering items in right locations with any scale game)
				int x = utils.getGuiWidth(chestContainer, chest);
				int y = utils.getGuiHeight(chestContainer, chest);
				
				Slot slot = chestContainer.getSlot(1);						
				utils.drawItemStack(event, chest, new ItemStack(Item.getItemFromBlock(Blocks.stained_glass_pane), 1, 5), slot, x + slot.xDisplayPosition, y + slot.yDisplayPosition);
			}
		}
	}
	
	@SubscribeEvent
	public void drawGuiContainerForegroundLayer(GuiScreenEvent.BackgroundDrawnEvent event) {
		if (event.gui instanceof GuiChest && event.gui != null) {
			GuiChest chest = (GuiChest) event.gui; // Get the GuiChest
	        Container container = chest.inventorySlots; // Get the Container
	        
			if (container instanceof ContainerChest) {
				ContainerChest chestContainer = (ContainerChest) container;
				 // Get Gui Width and Height (This is necessary for rendering items in right locations with any scale game)
				int x = utils.getGuiWidth(chestContainer, chest);
				int y = utils.getGuiHeight(chestContainer, chest);
				
				Slot slot = chestContainer.getSlot(8);		
				utils.drawEffectBackground(event, chest, (int) (x + slot.xDisplayPosition + (slot.xDisplayPosition / 9) * 1.8), y);
			}
		}
	}
    
	@SubscribeEvent
	public void onGuiOpen(final GuiOpenEvent event) {
		String currentGui;
		if (event.gui == null) {
			return;
		} 
		if (event.gui instanceof GuiChest) {
			new Thread() {
		        @Override
		        public void run() {
		        	try {
		        		Thread.sleep(10);
		        		GuiChest chest = (GuiChest) event.gui;
						ContainerChest container = (ContainerChest) chest.inventorySlots;
						if ((utils.getLocation()).contains("The Garden")) { 
							if (!Visitor.addVisitor(event, container, chest)) {
								LinkedList<LinkedList<String>> visitorList = Visitor.getVisitorList();
//								for (LinkedList<String> visitor : visitorList) {
//									utils.log(" " + visitor.getFirst());
//							    }
								
							}
						}
		        	} catch (InterruptedException e) {
		        		e.printStackTrace();
		        	}
		         }
			}.start();						
		}
	}
}