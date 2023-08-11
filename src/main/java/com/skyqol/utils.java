package com.skyqol;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.skyqol.guieventhandler.VisitorOffer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent.Post;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent.Pre;

public class utils {
	public static String cleanDuplicateColourCodes(String line) {
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

	public static String cleanColour(String in) {
		return in.replaceAll("(?i)\\u00A7.", "");
	}
	
	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("minecraft", "textures/gui/container/generic_54.png");
	
	public static void drawItemStack(DrawScreenEvent event, GuiChest chest, ItemStack stack, Slot slot, int x, int y) {
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
				line = cleanColour(line);
				line = line.replaceAll("^\\s+|\\s+$|\\p{C}", "");
				return line;
			} 
		}
		return "None";
	}
}
