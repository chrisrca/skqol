package com.skyqol.guieventhandler;

import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SignOpenListener {
	@SubscribeEvent
    public void onGuiScreenInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.gui instanceof GuiEditSign) {
            // A sign editing GUI is being opened
            GuiEditSign editSignGui = (GuiEditSign) event.gui;
            
            // Perform your actions here
            // For example, you can access the sign's text using editSignGui.tileSign.signText
            
        }
    }
}
