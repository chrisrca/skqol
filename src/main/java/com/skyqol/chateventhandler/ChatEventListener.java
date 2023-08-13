package com.skyqol.chateventhandler;

import java.util.LinkedList;

import com.skyqol.utils;
import com.skyqol.guieventhandler.Visitor;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatEventListener {

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        String chatMessage = event.message.getUnformattedText(); 
        chatMessage = utils.cleanDuplicateColors(chatMessage);
        chatMessage = utils.cleanColor(chatMessage);
        if ((utils.getLocation()).contains("The Garden")) { 
        	if (chatMessage.contains("ACCEPTED")) {
				LinkedList<LinkedList<String>> visitorList = Visitor.getVisitorList();
				for (LinkedList<String> visitor : visitorList) {
					if (chatMessage.contains(utils.cleanColor(utils.cleanDuplicateColors(visitor.getFirst())))) {
						Visitor.removeVisitor(utils.cleanColor(utils.cleanDuplicateColors(visitor.getFirst())));
						break;
					}
			    }
        	}        
        }
    }
}
