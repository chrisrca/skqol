package com.skyqol.entityrenderer.skins;

import com.skyqol.skyqol;
import com.skyqol.utils;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SkinEvents {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
    	AbstractClientPlayer player = (AbstractClientPlayer) event.entityPlayer;
    	utils.log(player.getName());
        if (player instanceof AbstractClientPlayer) {
            	skyqol.skinManager.updateSkin(player);
            }
        }
	
//    @SubscribeEvent
//    public void onRender(RenderWorldLastEvent event) {
//        if (Minecraft.getMinecraft().currentScreen == null) {
//            if (!skyqol.skinManager.getSkinName().isEmpty()) {
//            	skyqol.skinManager.updateSkin(Minecraft.getMinecraft().thePlayer);
//            }
//        }
//    }
    
//    @SubscribeEvent
//    public void onRender(RenderWorldLastEvent event) {
//        if (Minecraft.getMinecraft().currentScreen == null) {
//            String targetName = "Bazaar"; // The target display name
//            if (!skyqol.skinManager.getSkinName().isEmpty()) {
//                AbstractClientPlayer player = Minecraft.getMinecraft().thePlayer;
//                if (player != null) {
//                    skyqol.skinManager.updateSkin(player);
//                }
//            }
//        }
//    }
}

