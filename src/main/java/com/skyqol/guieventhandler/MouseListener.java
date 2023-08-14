package com.skyqol.guieventhandler;

import java.util.ConcurrentModificationException;
import java.util.LinkedList;

import org.lwjgl.input.Mouse;

import com.skyqol.utils;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MouseListener {
	@SubscribeEvent
    public void onMouseEvent(GuiScreenEvent.MouseInputEvent event) {
        // Get the x and y coordinates of the mouse
		
        int mouseButton = Mouse.getEventButton();
        
        // Check if the left mouse button was pressed down
        if (mouseButton == 0 && Mouse.getEventButtonState()) {
    		GuiScreen guiScreen = event.gui;
    		if (event.gui instanceof GuiChest && event.gui != null) {
    			GuiChest chest = (GuiChest) event.gui; // Get the GuiChest
    	        Container container = chest.inventorySlots; // Get the Container
    			if (container instanceof ContainerChest) {
    				ContainerChest chestContainer = (ContainerChest) container;
    				int x = utils.getGuiWidth(chestContainer, chest);
    	    		int y = utils.getGuiHeight(chestContainer, chest);
    	    		Slot slot = chestContainer.getSlot(8);
    	    		int offset = 0;
    	    		try {
    	    		LinkedList<LinkedList<String>> visitorList = Visitor.getVisitorList();
    					for (LinkedList<String> visitor : visitorList) {
    						Visitor.mouseInput(chest, (int) (x + slot.xDisplayPosition + (slot.xDisplayPosition / 9) * 1.8), y, offset, visitor.getFirst(), visitor.get(1), visitor.getLast(), (Mouse.getEventX() * guiScreen.width / guiScreen.mc.displayWidth), (guiScreen.height - Mouse.getEventY() * guiScreen.height / guiScreen.mc.displayHeight - 1));
    						offset++;
    					}
    	    		} catch (ConcurrentModificationException e) { 
    	    			return;
    	    		}
    			}
    		}
        }
    }
}
