package com.skyqol.guieventhandler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.skyqol.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.BackgroundDrawnEvent;
import net.minecraftforge.fml.client.config.GuiUtils;

public class Visitor {
	
    private static HashMap<String, ItemStack> itemMappings = new HashMap<String, ItemStack>() {{
    // Wheat:
    	put("Wheat", new ItemStack(Items.wheat, 1, 0));
    	put("Bread", new ItemStack(Items.bread, 1, 0));
    	put("Hay Bale", new ItemStack(Item.getItemFromBlock(Blocks.hay_block), 1, 0));
    	put("Enchanted Bread", new ItemStack(Items.bread, 1, 0));
    	put("Enchanted Hay Bale", new ItemStack(Item.getItemFromBlock(Blocks.hay_block), 1, 0));
    	// Tightly-Tied Hay Bale
    // Carrot
    	put("Carrot", new ItemStack(Items.carrot, 1, 0));
    	put("Golden Carrot", new ItemStack(Items.golden_carrot, 1, 0));
    	put("Enchanted Carrot", new ItemStack(Items.carrot, 1, 0));
    	put("Enchanted Golden Carrot", new ItemStack(Items.golden_carrot, 1, 0));
    // Potato
    	put("Potato", new ItemStack(Items.potato, 1, 0));
    	put("Enchanted Potato", new ItemStack(Items.potato, 1, 0));
    	put("Enchanted Baked Potato", new ItemStack(Items.baked_potato, 1, 0));
    // Pumpkin
    	put("Pumpkin", new ItemStack(Item.getItemFromBlock(Blocks.pumpkin), 1, 0));
    	put("Enchanted Pumpkin", new ItemStack(Item.getItemFromBlock(Blocks.pumpkin), 1, 0));
    	// Polished Pumpkin
    // Sugar Cane
    	put("Sugar Cane", new ItemStack(Items.reeds, 1, 0));
    	put("Enchanted Sugar", new ItemStack(Items.sugar, 1, 0));
    	put("Enchanted Sugar Cane", new ItemStack(Items.reeds, 1, 0));
    // Melon
    	put("Melon", new ItemStack(Items.melon, 1, 0));
    	put("Enchanted Melon", new ItemStack(Items.melon, 1, 0));
    	put("Enchanted Melon Block", new ItemStack(Item.getItemFromBlock(Blocks.melon_block), 1, 0));
    // Cactus
    	put("Cactus", new ItemStack(Item.getItemFromBlock(Blocks.cactus), 1, 0));
    	put("Enchanted Cactus Green", new ItemStack(Items.dye, 1, 2));
    	put("Enchanted Cactus", new ItemStack(Item.getItemFromBlock(Blocks.cactus), 1, 0));
    // Cocoa Beans
    	put("Cocoa Beans", new ItemStack(Items.dye, 1, 3));
    	put("Enchanted Cocoa Beans", new ItemStack(Items.dye, 1, 3));
    	put("Enchanted Cookie", new ItemStack(Items.cookie, 1, 0));
    // Mushrooms
    	put("Brown Mushroom", new ItemStack(Item.getItemFromBlock(Blocks.brown_mushroom), 1, 0));
    	put("Red Mushroom", new ItemStack(Item.getItemFromBlock(Blocks.red_mushroom), 1, 0));
    	put("Enchanted Brown Mushroom", new ItemStack(Item.getItemFromBlock(Blocks.brown_mushroom), 1, 0));
    	put("Enchanted Red Mushroom", new ItemStack(Item.getItemFromBlock(Blocks.red_mushroom), 1, 0));
    	put("Enchanted Brown Mushroom Block", new ItemStack(Item.getItemFromBlock(Blocks.brown_mushroom_block), 1, 0));
    	put("Enchanted Red Mushroom Block", new ItemStack(Item.getItemFromBlock(Blocks.red_mushroom_block), 1, 0));
    // Nether Wart
    	put("Nether Wart", new ItemStack(Items.nether_wart, 1, 0));
    	put("Enchanted Nether Wart", new ItemStack(Items.nether_wart, 1, 0));
    	// Mutant Nether Wart
    // Seeds
    	put("Seeds", new ItemStack(Items.wheat_seeds, 1, 0));
    	put("Enchanted Seeds", new ItemStack(Items.wheat_seeds, 1, 0));
    // Egg
    	put("Egg", new ItemStack(Items.egg, 1, 0));
    	put("Enchanted Egg", new ItemStack(Items.egg, 1, 0));
    	put("Super Enchanted Egg", new ItemStack(Items.spawn_egg, 1, 0));
    // Raw Mutton
    	put("Raw Mutton", new ItemStack(Items.mutton, 1, 0));
    	put("Enchanted Mutton", new ItemStack(Items.mutton, 1, 0));
    	put("Enchanted Cooked Mutton", new ItemStack(Items.cooked_mutton, 1, 0));
    // Raw Porkchop
    	put("Raw Porkchop", new ItemStack(Items.porkchop, 1, 0));
    	put("Enchanted Pork", new ItemStack(Items.porkchop, 1, 0));
    	put("Enchanted Grilled Pork", new ItemStack(Items.cooked_porkchop, 1, 0));
    // Raw Rabbit
    	put("Raw Rabbit", new ItemStack(Items.rabbit, 1, 0));
    	put("Enchanted Raw Rabbit", new ItemStack(Items.rabbit, 1, 0));
    // Wool
    	put("Wool", new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 0));
    	put("Enchanted Wool", new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 0));
    // Compost
    	// Compost
    // Jack o' Lantern
    	put("Jack o' Lantern", new ItemStack(Item.getItemFromBlock(Blocks.lit_pumpkin), 1, 0));
    // Milk Bucket
    	put("Milk Bucket", new ItemStack(Items.milk_bucket, 1, 0));
    }};
	
	final static String[] visitorNameList = {"Adventurer", "Alchemist", "Andrew", "Anita", "Arthur", "Banker Broadjaw", "Bartender", "Beth", "Clerk Seraphine", "Dalbrek", "Duke", "Dusk", "Emissary Carlton", "Emissary Ceanna" , "Emissary Fraiser", "Emissary Sisko", "Emissary Wilson", "Farmer Jon", "Farmhand", "Fear Mongerer", "Felix", "Fisherman", "Fragilis", "Friendly Hiker", "Geonathan Greatforge", "Gimley", "Gold Forger", "Grandma Wolf", "Guy", "Gwendolyn", "Hornum", "Hungry Hiker", "Iron Forger", "Jack", "Jacob", "Jamie", "Jerry", "Jotraeline Greatforge", "Lazy Miner", "Leo", "Liam", "Librarian", "Lumberjack", "Lumina", "Lynn", "Madame Eleanor Q. Goldsworth III", "Mason", "Odawa", "Old Man Garry", "Oringo", "Pete", "Plumber Joe", "Puzzler", "Queen Mismyla", "Rhys", "Royal Resident", "Royal Resident (Neighbor)", "Royal Resident (Snooty)", "Rusty", "Ryu", "Sargwyn", "Seymour", "Shaggy", "Shifty", "Sirius", "Spaceman", "Stella", "Tammy", "Tarwen", "Terry", "Tia the Fairy", "Tom", "Trevor", "Vex", "Weaponsmith", "Wizard", "Xalx", "Zog"};
	final static char[] visitorRarity = {'U', 'U', 'U', 'U', 'U', 'R', 'R', 'L', 'L', 'R', 'U', 'U', 'U', 'U', 'U', 'R', 'U', 'U', 'U', 'U', 'U', 'U', 'R', 'U', 'U', 'U', 'R', 'R', 'U', 'R', 'U', 'U', 'R', 'U', 'U', 'U', 'L', 'U', 'R', 'U', 'U', 'U', 'U', 'R', 'U', 'L', 'U', 'U', 'R', 'U', 'R', 'U', 'R', 'R', 'U', 'R', 'U', 'U', 'R', 'U', 'U', 'R', 'U', 'R', 'L', 'S', 'U', 'R', 'U', 'U', 'R', 'U', 'U', 'U', 'U', 'U', 'U', 'R'};
	
	private static LinkedList<LinkedList<String>> visitorList = new LinkedList<>();
	
	private static String getVisitor(String currentGui) {
		int index = -1;
		if (currentGui.contains("Bazaar")) {
			return "Bazaar";
		}
       	for (String visitor : visitorNameList) {
           	index++;
       		if (visitor.equals(currentGui)) {
       			ChatComponentText coloredMessage;
               	switch (visitorRarity[index]) {
              	case 'U':
               		return (EnumChatFormatting.GREEN + currentGui);
              	case 'R':
               		return (EnumChatFormatting.BLUE + currentGui);
              	case 'L':
              		return (EnumChatFormatting.GOLD + currentGui);
              	case 'S':
               		return (EnumChatFormatting.DARK_RED + currentGui);
        		}
        	} 
		}
       	return "";
	}

	private static String getOffer(GuiOpenEvent event) {
        IInventory chestInventory = ((ContainerChest) ((GuiChest) event.gui).inventorySlots).getLowerChestInventory();
        ItemStack itemStack = chestInventory.getStackInSlot(29);
        if (itemStack != null && itemStack.stackSize > 0) {
		    	// Get the Item from the ItemStack
		    	Item item = itemStack.getItem();
		    	List<String> tooltipLines = itemStack.getTooltip(null, false);
		    	for (int i = 0; i < tooltipLines.size(); i++) {
		    		if (i > 0 && utils.cleanColor(utils.cleanDuplicateColors(tooltipLines.get(i-1))).contains("Items Required")) {
		    			String line = tooltipLines.get(i);
		    			if (line.indexOf("(") > 0) {
		    				line = line.substring(0, line.indexOf("("));
		    				line = line.substring(0, line.lastIndexOf(' '));
		    			}
		    			return(line);
		    		}
		    	}
		} 
		return "";
	}
	
	public static boolean addVisitor(GuiOpenEvent event, ContainerChest container, GuiChest chest) {
        LinkedList<String> visitorInfo = new LinkedList<>();
        // Prevent already existing visitors from being added to the list
        for (LinkedList<String> innerList : visitorList) {
        	for (String data : innerList) {
        		if (data.contains(container.getLowerChestInventory().getDisplayName().getUnformattedText())) {
                    return false;
                }
        	}
        }

        // get visitor, offer, and raw offer
        String visitor = Visitor.getVisitor(container.getLowerChestInventory().getDisplayName().getUnformattedText()); 
        String offer = Visitor.getOffer(event);
        // parse offer so it can be used to scrape bazaar
        String offerParsed = ((utils.cleanColor(utils.cleanDuplicateColors(Visitor.getOffer(event)))).replaceAll("^\\s+|\\s+$|\\p{C}", ""));
		if (offerParsed.lastIndexOf("x[0-9]") > 0) {
			offerParsed = offerParsed.substring(0, offerParsed.lastIndexOf("x[0-9]"));
		}
		if (visitor.contains("Bazaar")) {
			return true;
		} else if (!visitor.equals("") && !offer.equals("") && !offerParsed.equals("")) {
        	visitorInfo.add(visitor);
        	visitorInfo.add(offer);
            visitorInfo.add(offerParsed);
            visitorList.add(visitorInfo);
            //utils.log("Added New Visitor: " + visitor);
        }
		return false;
	}
	
	public static LinkedList<LinkedList<String>> getVisitorList() {
		return visitorList;
	}
	
	public static void removeVisitor(String visitor) {
        for (LinkedList<String> innerList : visitorList) {
        	if ((innerList.getFirst()).contains(visitor)) {
        		visitorList.remove(innerList);
        	}
        }
	}
	
	private static final ResourceLocation INVENTORY_TEXTURE = new ResourceLocation("minecraft", "textures/gui/container/inventory.png");
	
	public static void drawVisitorBackground(GuiChest chest, int x, int y, int offset, String name, String request, String file, int mouseX, int mouseY) {
		offset *= 33;
        
        Minecraft.getMinecraft().getTextureManager().bindTexture(INVENTORY_TEXTURE);
        chest.drawTexturedModalRect(x, y + offset, 0, 166, 120, 32); 
        ResourceLocation HEAD_TEXTURE = new ResourceLocation("skyqol", ("garden/" + (utils.cleanColor(utils.cleanDuplicateColors(name))) + ".png"));
        Minecraft.getMinecraft().getTextureManager().bindTexture(HEAD_TEXTURE);
    
        if (file.contains("Alchemist")) {
        	chest.drawModalRectWithCustomSizedTexture(x + 4, y + offset + 9, 0, 0, 22, 22, 22, 22);
        } else {
        	chest.drawModalRectWithCustomSizedTexture(x, y + offset + 1, 0, 0, 30, 30, 30, 30);
        }
        
        String requestRaw = utils.cleanColor(utils.cleanDuplicateColors(request));

        Pattern pattern = Pattern.compile("x[0-9]+$");
        Matcher matcher = pattern.matcher(requestRaw);
        if (matcher.find()) {
        	requestRaw = requestRaw.substring(1, matcher.start() - 1);
        } else {
        	requestRaw = requestRaw.substring(1);
        }
        
        if (requestRaw.equals("Tightly-Tied Hay Bale") || requestRaw.equals("Compost") || requestRaw.equals("Polished Pumpkin") || requestRaw.equals("Mutant Nether Wart")) {
            ResourceLocation HAY_TEXTURE = new ResourceLocation("skyqol", ("garden/" + requestRaw + ".png"));
            Minecraft.getMinecraft().getTextureManager().bindTexture(HAY_TEXTURE);
            chest.drawModalRectWithCustomSizedTexture(x+30, y+12 + offset, 0, 0, 16, 16, 16, 16);
        } else {
        	RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
            ItemStack requestedItem = itemMappings.get(requestRaw);
            
            if (requestedItem == null) return;
            
            if (requestRaw.contains("Enchanted")) {
                requestedItem.addEnchantment(Enchantment.unbreaking, 1);
            }
            
            RenderHelper.enableGUIStandardItemLighting();
            itemRender.renderItemAndEffectIntoGUI(requestedItem, x+30, y+12 + offset);
            RenderHelper.disableStandardItemLighting();
        }
	}	
	
	public static void drawTooltips(GuiChest chest, int x, int y, int offset, String name, String request, String file, int mouseX, int mouseY) {
		offset *= 33;
        String requestAmount = "";
        if ((utils.cleanColor(utils.cleanDuplicateColors(request))).lastIndexOf('x') < 0) {
        	request += " \u00A78x1";
        }
        if (isMouseOver(x+27, y+11 + offset, mouseX, mouseY)) {
            List<String> tooltip = Arrays.asList(fixTooltips(request)); 
            drawHoveringText(tooltip, mouseX, mouseY);
        }
	}	
	
    public static void drawScaledString(String text, float x, float y, float scale, int color) {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, scale);
        fontRenderer.drawString(text, Math.round(x / scale), Math.round(y / scale), color);
        GlStateManager.popMatrix();
    }
	
	private static boolean isMouseOver(int itemX, int itemY, int mouseX, int mouseY) {
	    return mouseX >= itemX && mouseX <= itemX + 16 && mouseY >= itemY && mouseY <= itemY + 16;
	}

	private static void drawHoveringText(List<String> textLines, int x, int y) {
	    Minecraft mc = Minecraft.getMinecraft();
	    GuiUtils.drawHoveringText(textLines, x, y, mc.displayWidth, mc.displayHeight, -1, mc.fontRendererObj);
	}
	
	public static String fixTooltips(String input) {
        StringBuilder result = new StringBuilder(input);
        boolean capitalFound = false;
        for (int i = 0; i < result.length(); i++) {
            char c = result.charAt(i);
            if (Character.isUpperCase(c)) {
                capitalFound = true;
                break;
            }
            if (Character.isWhitespace(c)) {
                result.deleteCharAt(i);
                i--;
            }
        }
        return result.toString();
    }

	public static void drawText(GuiChest chest, int x, int y, int offset, String name, String request, String file, int mouseX, int mouseY) {
		offset *= 33;
        if (file.contains("Madame Eleanor")) {
        	name = "\u00A76Madame Eleanor";
        	file = "\u00A76Madame Eleanor";
        } else if (file.contains("Royal Resident (")) {
        	name = "\u00A7aRoyal Resident";
        	file = "\u00A7aRoyal Resident";
        }
        String requestAmount = "";
        if ((utils.cleanColor(utils.cleanDuplicateColors(request))).lastIndexOf('x') > 0) {
            requestAmount = (utils.cleanColor(utils.cleanDuplicateColors(request))).substring((utils.cleanColor(utils.cleanDuplicateColors(request))).lastIndexOf('x'));
        } else {
        	requestAmount = "x1";
        }
        String colorCode = (name.substring(1, 2));
        int shadow = 0x3f3f3f;
        if (colorCode.equals("a")) {
        	shadow = 0x153f15;
        } else if (colorCode.equals("9")) {
        	shadow = 0x15153f;
        } else if (colorCode.equals("6")) {
        	shadow = 0x2a2a00;
        } else if (colorCode.equals("c")) {
        	shadow = 0x3f1515;
        } 
  
        drawScaledString((utils.cleanColor(utils.cleanDuplicateColors(name))), x+30.5F, y+6F + offset, 0.75F, shadow);
        drawScaledString(name, x+30, y+5 + offset, 0.75F, 0x3f3f3f);
        drawScaledString(requestAmount, x+48F, y+18F + offset, 0.75F, 0x151515);
        drawScaledString(requestAmount, x+47, y+17 + offset, 0.75F, 0x555555);
	}

	public static void displayRequests(BackgroundDrawnEvent event, GuiChest chest, Container container, ContainerChest chestContainer) {
		int x = utils.getGuiWidth(chestContainer, chest);
		int y = utils.getGuiHeight(chestContainer, chest);
		Slot slot = chestContainer.getSlot(8);
		
		int offset = 0;
		try {
			RenderHelper.disableStandardItemLighting();
			LinkedList<LinkedList<String>> visitorList = Visitor.getVisitorList();
			for (LinkedList<String> visitor : visitorList) {
				Visitor.drawVisitorBackground(chest, (int) (x + slot.xDisplayPosition + (slot.xDisplayPosition / 9) * 1.8), y, offset, visitor.getFirst(), visitor.get(1), visitor.getLast(), event.getMouseX(), event.getMouseY());
				offset++;
			}	
			offset = 0;
			for (LinkedList<String> visitor : visitorList) {
				Visitor.drawText(chest, (int) (x + slot.xDisplayPosition + (slot.xDisplayPosition / 9) * 1.8), y, offset, visitor.getFirst(), visitor.get(1), visitor.getLast(), event.getMouseX(), event.getMouseY());
				offset++;
			}	
			offset = 0;
			for (LinkedList<String> visitor : visitorList) {
				Visitor.drawTooltips(chest, (int) (x + slot.xDisplayPosition + (slot.xDisplayPosition / 9) * 1.8), y, offset, visitor.getFirst(), visitor.get(1), visitor.getLast(), event.getMouseX(), event.getMouseY());
				offset++;
			}	
	        RenderHelper.enableStandardItemLighting();
		} catch (ConcurrentModificationException e) { // list is still being written to
			return;
		}
	}

	public static void addRequests(final GuiOpenEvent event, GuiChest chest, ContainerChest container) {
		new Thread() {
	        @Override
	        public void run() {
	        	try {
	        		Thread.sleep(10);
	        		GuiChest chest = (GuiChest) event.gui;
					ContainerChest container = (ContainerChest) chest.inventorySlots;
					if (!Visitor.addVisitor(event, container, chest)) {
						LinkedList<LinkedList<String>> visitorList = Visitor.getVisitorList();
					}
	        	} catch (InterruptedException e) {
	        		e.printStackTrace();
	        	}
	         }
		}.start();		
		
	}	
}
