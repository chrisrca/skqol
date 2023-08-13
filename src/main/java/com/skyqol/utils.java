package com.skyqol;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.skyqol.guieventhandler.Visitor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiOverlayDebug;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent.BackgroundDrawnEvent;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent.Post;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent.Pre;
import net.minecraftforge.fml.client.config.GuiUtils;

public class utils {
	public static String cleanDuplicateColors(String line) {
		StringBuilder sb = new StringBuilder();
		char currentColourCode = 'r';
		boolean sectionSymbolLast = false;
		for (char c : line.toCharArray()) {
			if ((int) c > 50000) continue;

			if (c == '\u00a7') {
				sectionSymbolLast = true;
			} else {
				if (sectionSymbolLast) {
					if (currentColourCode != c) {
						sb.append('\u00a7');
						sb.append(c);
						currentColourCode = c;
					}
					sectionSymbolLast = false;
				} else {
					sb.append(c);
				}
			}
		}
		return sb.toString();
	}

	public static String cleanColor(String in) {
		return in.replaceAll("(?i)\\u00A7.", "");
	}
	
	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("minecraft", "textures/gui/container/generic_54.png");
	private static final ResourceLocation INVENTORY_TEXTURE = new ResourceLocation("minecraft", "textures/gui/container/inventory.png");
	
	public static void drawVisitorBackground(GuiChest chest, int x, int y, int offset, String name, String request, String file, int mouseX, int mouseY) {
		offset *= 33;
        RenderHelper.disableStandardItemLighting();
        Minecraft.getMinecraft().getTextureManager().bindTexture(INVENTORY_TEXTURE);
        chest.drawTexturedModalRect(x, y + offset, 0, 166, 120, 32); 
        ResourceLocation HEAD_TEXTURE = new ResourceLocation("skyqol", ("garden/" + (cleanColor(cleanDuplicateColors(name))) + ".png"));
        Minecraft.getMinecraft().getTextureManager().bindTexture(HEAD_TEXTURE);
        if (file.contains("Alchemist")) {
        	chest.drawModalRectWithCustomSizedTexture(x + 4, y + offset + 9, 0, 0, 22, 22, 22, 22);
        } else {
        	chest.drawModalRectWithCustomSizedTexture(x, y + offset + 1, 0, 0, 30, 30, 30, 30);
        }
        if (file.contains("Madame Eleanor")) {
        	name = "\u00A76Madame Eleanor";
        	file = "\u00A76Madame Eleanor";
        } else if (file.contains("Royal Resident (")) {
        	name = "\u00A7aRoyal Resident";
        	file = "\u00A7aRoyal Resident";
        }
        
        String requestRaw = cleanColor(cleanDuplicateColors(request));
        String requestAmount = "";
        if ((cleanColor(cleanDuplicateColors(request))).lastIndexOf('x') > 0) {
            requestAmount = (cleanColor(cleanDuplicateColors(request))).substring((cleanColor(cleanDuplicateColors(request))).lastIndexOf('x'));
        } else {
        	requestAmount = "x1";
        }

        
        RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
        ItemStack requestedItem = new ItemStack((Items.potato), 1, 0);
        requestedItem.addEnchantment(Enchantment.unbreaking, 1);
        itemRender.renderItemAndEffectIntoGUI(requestedItem, x+27, y+11 + offset);
        
        drawScaledString((cleanColor(cleanDuplicateColors(name))), x+27.2F, y+7.4F + offset, 0.75F, 0x3f3f3f);
        drawScaledString(name, x+27, y+7 + offset, 0.75F, 0x3f3f3f);

        drawScaledString(requestAmount, x+45F, y+18.4F + offset, 0.75F, 0x3f3f3f);
        drawScaledString(requestAmount, x+44, y+18 + offset, 0.75F, 0xFFFFFF);

        RenderHelper.enableGUIStandardItemLighting();
	}	
	
	public static void drawTooltips(GuiChest chest, int x, int y, int offset, String name, String request, String file, int mouseX, int mouseY) {
		offset *= 33;
        RenderHelper.disableStandardItemLighting();
        if (isMouseOver(x+27, y+11 + offset, mouseX, mouseY)) {
            List<String> tooltip = Arrays.asList(request); 
            drawHoveringText(tooltip, mouseX, mouseY);
        }
        RenderHelper.enableGUIStandardItemLighting();
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
	
	public static void drawItemStack(GuiChest chest, ItemStack stack, Slot slot, int x, int y) {
        Minecraft mc = Minecraft.getMinecraft();
        RenderItem itemRender = mc.getRenderItem();
        RenderHelper.disableStandardItemLighting();
        mc.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        chest.drawTexturedModalRect(x, y, 8, 18, 16, 16); 
        itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        RenderHelper.enableGUIStandardItemLighting();
	}
	
	public static String getLocation() {
		Scoreboard scoreboard = Minecraft.getMinecraft().thePlayer.getWorldScoreboard();
		ScoreObjective sidebarObjective = scoreboard.getObjectiveInDisplaySlot(1);
		List<Score> scores = new ArrayList<>(scoreboard.getSortedScores(sidebarObjective));
		// Check each line using utils to format it to plaintext
		for (int i = scores.size() - 1; i >= 0; i--) {
			Score score = scores.get(i);
			ScorePlayerTeam scoreplayerteam1 = scoreboard.getPlayersTeam(score.getPlayerName());
			String line = ScorePlayerTeam.formatPlayerName(scoreplayerteam1, score.getPlayerName());

			if (line.contains(String.valueOf("\u23E3"))) {
				line = line.replace("\u23E3", "").trim();
				line = cleanDuplicateColors(line);
				line = cleanColor(line);
				line = line.replaceAll("^\\s+|\\s+$|\\p{C}", "");
				return line;
			} 
		}
		return "None";
	}

	public static int getGuiWidth(ContainerChest container, GuiChest chest) {
		return (chest.width - 176) / 2;
	}
	
	public static int getGuiHeight(ContainerChest container, GuiChest chest) {
		int y = 0;
		if (((ContainerChest) container).getLowerChestInventory().getSizeInventory() / 9 == 6) {
			y = (chest.height - 222) / 2; 				
		} else if (((ContainerChest) container).getLowerChestInventory().getSizeInventory() / 9 == 5) {
			y = (chest.height - 203) / 2; 
		} else if (((ContainerChest) container).getLowerChestInventory().getSizeInventory() / 9 == 4) {
			y = (chest.height - 185) / 2; 
		} else {
			y = (chest.height - 167) / 2; 
		}
		return y;
	}
	
	public static void log(String str) {
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("" + str));
	}
	
//	public static void getTabList() {
//		GuiPlayerTabOverlay tabOverlay  = Minecraft.getMinecraft().ingameGUI.getTabList();
//        Scoreboard scoreboard = Minecraft.getMinecraft().theWorld.getScoreboard();
//        Collection<ScorePlayerTeam> teams = scoreboard.getTeams();
//
//        for (ScorePlayerTeam team : teams) {
//        	utils.log(team.getRegisteredName());
//            Team.EnumVisible visibility = team.getNameTagVisibility();
//            if (visibility == Team.EnumVisible.ALWAYS || visibility == Team.EnumVisible.HIDE_FOR_OTHER_TEAMS) {
//                Collection<String> players = team.getMembershipCollection();
//                for (String playerName : players) {
//                    ChatComponentText playerNameComponent = new ChatComponentText(playerName);
//                    String playerNameFormatted = playerNameComponent.getFormattedText();
//                    String teamPrefix = EnumChatFormatting.getTextWithoutFormattingCodes(team.getColorPrefix());
//                    String formattedPlayerInfo = teamPrefix + playerNameFormatted;
//                    utils.log(formattedPlayerInfo);
//                }
//            }
//        }
//	}
}
