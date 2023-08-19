package com.skyqol.entityrenderer.skins;

import com.mojang.authlib.GameProfile;
import com.skyqol.skyqol;
import com.skyqol.utils;

import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SkinEvents {

	private double BazaarposX = -99999999;
	private double BazaarposZ = -99999999;
	private double BazaarposY = -99999999;

	@SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
    	AbstractClientPlayer player = (AbstractClientPlayer) event.entityPlayer;
        if (player instanceof AbstractClientPlayer) {
            if (player.posX == this.BazaarposX && player.posZ == this.BazaarposZ && player.posY == this.BazaarposY) {
            	if (!(utils.isRealPlayer(player.getName()))) {
                	skyqol.skinManager.updateSkin(player, new ResourceLocation("skyqol", ("privateisland/Bazaar.png")));
            	}
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderLiving(RenderLivingEvent.Pre<EntityLivingBase> event) {
        EntityLivingBase entity = event.entity;
    	if (event.entity instanceof net.minecraft.entity.item.EntityArmorStand) {
    		if (event.entity.getName().equals("\u00A76Bazaar")) {
    			this.BazaarposX = event.entity.posX;
    			this.BazaarposZ = event.entity.posZ;
    			this.BazaarposY = event.entity.posY;
            }
        }
    } 
}

