package com.skyqol;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.Instance;

import java.awt.Container;

import com.skyqol.chateventhandler.ChatEventListener;
import com.skyqol.commands.Commands;
import com.skyqol.entityrenderer.skins.SkinEvents;
import com.skyqol.entityrenderer.skins.SkinManager;
import com.skyqol.guieventhandler.GuiOpenListener;
import com.skyqol.guieventhandler.MouseListener;
import com.skyqol.guieventhandler.SignOpenListener;
import com.skyqol.worldeventhandler.WorldListener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
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
    
    public static SkinManager skinManager;
    
    private static final long CHAT_MSG_COOLDOWN = 200;

    @Instance(MODID)
    public static skyqol instance;

    public Commands commands;
	private static long lastChatMessage = 0;
	private static long secondLastChatMessage = 0;
	private static String currChatMessage = null;
    
    @EventHandler
    public void preinit(FMLInitializationEvent event)
    {
    	//MinecraftForge.EVENT_BUS.register(new ChatListener(this));
    	MinecraftForge.EVENT_BUS.register(new GuiOpenListener());
    	MinecraftForge.EVENT_BUS.register(new WorldListener());
    	MinecraftForge.EVENT_BUS.register(new ChatEventListener());
    	MinecraftForge.EVENT_BUS.register(new SignOpenListener());
    	MinecraftForge.EVENT_BUS.register(new MouseListener());
    	this.commands = new Commands();
    	skinManager = new SkinManager();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	MinecraftForge.EVENT_BUS.register(new SkinEvents());
    }

    public void trySendCommand(String message) {
		if (ClientCommandHandler.instance.executeCommand(Minecraft.getMinecraft().thePlayer, message) == 0) {
			sendChatMessage(message);
		}
	}
    
    public static void sendChatMessage(String message) {
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