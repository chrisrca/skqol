package com.skyqol.entityrenderer.skins;

import com.skyqol.*;
import com.skyqol.entityrenderer.utils.ReflectUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class SkinManager {

    private Minecraft mc;

    public SkinManager() {
        this.mc = Minecraft.getMinecraft();
    }

    public void updateSkin(AbstractClientPlayer player, ResourceLocation resourceLocation) {
        Minecraft.getMinecraft().addScheduledTask(() -> replaceSkin(player, resourceLocation));
    }

    private void replaceSkin(AbstractClientPlayer player, ResourceLocation resourceLocation) {
        NetworkPlayerInfo playerInfo;

        try {
            playerInfo = (NetworkPlayerInfo) ReflectUtils.findMethod(AbstractClientPlayer.class, new String[] {"getPlayerInfo", "func_175155_b"}).invoke(player);
        } catch (Throwable ex) {
            return;
        }
        
        if (resourceLocation != null) {
            try {
                ObfuscationReflectionHelper.setPrivateValue(NetworkPlayerInfo.class, playerInfo, resourceLocation, "locationSkin", "field_178865_e");
            } catch (Exception ex) {
            	return;
            }
        }
    }
}

