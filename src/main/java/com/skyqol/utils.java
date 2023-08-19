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
	
	public static boolean isRealPlayer(String unformattedText) {
		unformattedText = "] " + unformattedText;
		GuiPlayerTabOverlay tabOverlay  = Minecraft.getMinecraft().ingameGUI.getTabList();
	    Scoreboard scoreboard = Minecraft.getMinecraft().theWorld.getScoreboard();
	    Collection<ScorePlayerTeam> teams = scoreboard.getTeams();
	    for (ScorePlayerTeam team : teams) {
	    	if (team.getRegisteredName().contains(unformattedText)) {
	    		return true;
	    	}
	        Team.EnumVisible visibility = team.getNameTagVisibility();
	        if (visibility == Team.EnumVisible.ALWAYS || visibility == Team.EnumVisible.HIDE_FOR_OTHER_TEAMS) {
	            Collection<String> players = team.getMembershipCollection();
	            for (String playerName : players) {
	                ChatComponentText playerNameComponent = new ChatComponentText(playerName);
	                String playerNameFormatted = playerNameComponent.getFormattedText();
	                String teamPrefix = EnumChatFormatting.getTextWithoutFormattingCodes(team.getColorPrefix());
	                String formattedPlayerInfo = teamPrefix + playerNameFormatted;
	                if (formattedPlayerInfo.contains(unformattedText)) {
	                	return true;
	                }               
	            }
	        }
	    }    
	    return false;
	}
	
	public static void writeFile(String text) {
		try {
        	BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\goali\\AppData\\Roaming\\.minecraft\\mods\\out.txt"));
        	writer.write(text);
        	writer.newLine();
        	writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
//	public static void getTabList() {
//		GuiPlayerTabOverlay tabOverlay  = Minecraft.getMinecraft().ingameGUI.getTabList();
//        Scoreboard scoreboard = Minecraft.getMinecraft().theWorld.getScoreboard();
//        Collection<ScorePlayerTeam> teams = scoreboard.getTeams();
//        try {
//        	BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\goali\\AppData\\Roaming\\.minecraft\\mods\\out.txt"));
//        for (ScorePlayerTeam team : teams) {
//        	utils.log(team.getRegisteredName());
//        	writer.write(team.getRegisteredName() + "Visability: " + team.getNameTagVisibility());
//            writer.newLine();
//            Team.EnumVisible visibility = team.getNameTagVisibility();
//            if (visibility == Team.EnumVisible.ALWAYS || visibility == Team.EnumVisible.HIDE_FOR_OTHER_TEAMS) {
//                Collection<String> players = team.getMembershipCollection();
//                for (String playerName : players) {
//                    ChatComponentText playerNameComponent = new ChatComponentText(playerName);
//                    String playerNameFormatted = playerNameComponent.getFormattedText();
//                    String teamPrefix = EnumChatFormatting.getTextWithoutFormattingCodes(team.getColorPrefix());
//                    String formattedPlayerInfo = teamPrefix + playerNameFormatted;
//                    utils.log(formattedPlayerInfo);
//                        writer.write(formattedPlayerInfo);
//                        writer.newLine();
//                        // Close the writer
//                        
//
//
//                }
//            }
//        }
//        writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        
//	}
}
