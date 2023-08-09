package com.skyqol.guieventhandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

//Open the custom chest GUI on the client side
//Minecraft.getMinecraft().displayGuiScreen(new CustomChestGui());

// Custom GUI screen with a grid of 6x9 slots
public class CustomChestGUI extends GuiContainer {
	private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("minecraft", "textures/blocks/glass_lime.png");
	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("minecraft", "textures/gui/container/generic_54.png");
    private static final int CHEST_WIDTH = 176;
    private static final int CHEST_HEIGHT = 222;
    
    public CustomChestGUI(Container inventorySlotsIn) {
        super(inventorySlotsIn);
    }
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    	// Draw the normal chest inventory background
        int xOffset = (width - xSize) / 2;
        int yOffset = (height - ySize) / 2;
        mc.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        drawTexturedModalRect(xOffset, yOffset, 0, 0, CHEST_WIDTH, CHEST_HEIGHT);
        // Call the superclass method to render the default chest inventory
//    	mc.getTextureManager().bindTexture(CHEST_GUI_TEXTURE);
//        drawTexturedModalRect(guiLeft, guiTop, 0, 0, 16, 16);

        // Add your custom rendering code here to draw additional textures
        // For example, you can use methods like drawTexturedModalRect
    }
}

