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

import com.skyqol.commands.Commands;

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
    
    //Open the custom chest GUI on the client side
    //Minecraft.getMinecraft().displayGuiScreen(new CustomChestGui());
    
    // Custom GUI screen with a grid of 6x9 slots
    private static class CustomChestGui extends GuiContainer {
    	private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
        private static final int GUI_WIDTH = 176; // Change to the desired width
        private static final int GUI_HEIGHT = 222; // Change to the desired height
        
        public CustomChestGui() {
            super(new ContainerEmpty());
            xSize = GUI_WIDTH;
            ySize = GUI_HEIGHT;
        }

        @Override
        protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(CHEST_GUI_TEXTURE);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
            System.out.println(CHEST_GUI_TEXTURE);
        }

        @Override
        protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
            // Draw your custom GUI foreground here
        }
    }

    // Dummy container class for the custom GUI
    private static class ContainerEmpty extends net.minecraft.inventory.Container {
        @Override
        public boolean canInteractWith(EntityPlayer playerIn) {
            return true;
        }
    }
}