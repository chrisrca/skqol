package com.skyqol.worldeventhandler;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;

public class WorldListener {
	private static boolean scoreboardFetched = false;
	
//	@SubscribeEvent
//	public void onClientTick(ClientTickEvent event) {
//        if (event.phase == ClientTickEvent.Phase.END && (Minecraft.getMinecraft().theWorld != null) && scoreboardFetched == false) {
//        	scoreboardFetched = true;
//        	System.out.println("Joined New World");
//			Scoreboard scoreboard = Minecraft.getMinecraft().thePlayer.getWorldScoreboard();
//		
//			ScoreObjective sidebarObjective = scoreboard.getObjectiveInDisplaySlot(1);
//			List<Score> scores = new ArrayList<>(scoreboard.getSortedScores(sidebarObjective));
//			for (int i = scores.size() - 1; i >= 0; i--) {
//				Score score = scores.get(i);
//				ScorePlayerTeam scoreplayerteam1 = scoreboard.getPlayersTeam(score.getPlayerName());
//				String line = ScorePlayerTeam.formatPlayerName(scoreplayerteam1, score.getPlayerName());
//				System.out.println(line);
//			}
//        }
//	}
//	public static void onPlayerLeaveWorld(PlayerLoggedOutEvent event) {    
//		
//	}

}
