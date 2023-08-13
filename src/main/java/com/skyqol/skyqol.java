package com.skyqol;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.Instance;

import java.awt.Container;

import com.skyqol.chateventhandler.ChatEventListener;
import com.skyqol.commands.Commands;
import com.skyqol.guieventhandler.GuiOpenListener;
import com.skyqol.worldeventhandler.WorldListener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Mod(modid = skyqol.MODID, name=skyqol.NAME, version = skyqol.VERSION)
public class skyqol
{
	public static final String NAME = "skyqol";
    public static final String MODID = "skyqol";
    public static final String VERSION = "1.0";
    
    private static final long CHAT_MSG_COOLDOWN = 200;

    @Instance(MODID)
    public static skyqol instance;

    public Commands commands;
	private long lastChatMessage = 0;
	private long secondLastChatMessage = 0;
	private String currChatMessage = null;
    
    @EventHandler
    public void preinit(FMLInitializationEvent event)
    {
    	//MinecraftForge.EVENT_BUS.register(new ChatListener(this));
    	MinecraftForge.EVENT_BUS.register(new GuiOpenListener());
    	MinecraftForge.EVENT_BUS.register(new WorldListener());
    	MinecraftForge.EVENT_BUS.register(new ChatEventListener());
    	this.commands = new Commands();
    }

    public void trySendCommand(String message) {
		if (ClientCommandHandler.instance.executeCommand(Minecraft.getMinecraft().thePlayer, message) == 0) {
			sendChatMessage(message);
		}
	}
    
    public void sendChatMessage(String message) {
		if (System.currentTimeMillis() - lastChatMessage > CHAT_MSG_COOLDOWN) {
			secondLastChatMessage = lastChatMessage;
			lastChatMessage = System.currentTimeMillis();
			Minecraft.getMinecraft().thePlayer.sendChatMessage(message);
			currChatMessage = null;
		} else {
			currChatMessage = message;
		}
	}
}