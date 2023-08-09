package com.skyqol.guieventhandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class CustomGuiScreen extends GuiScreen {

	private static final ResourceLocation STAINED_GLASS_TEXTURE = new ResourceLocation("minecraft", "textures/blocks/glass_lime.png");
	
	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Call the super method to draw the default GUI components
        super.drawScreen(mouseX, mouseY, partialTicks);

        // Bind the stone texture
        Minecraft.getMinecraft().getTextureManager().bindTexture(STAINED_GLASS_TEXTURE);

        // Set the color to white (to ensure proper texture colors)
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        // Calculate the position to render the stone texture
        int x = 8/* calculate x-coordinate */;
        int y = 16/* calculate y-coordinate */;

        // Draw the stone texture
        drawTexturedModalRect(x, y, 0, 0, 16, 16);
    }
}