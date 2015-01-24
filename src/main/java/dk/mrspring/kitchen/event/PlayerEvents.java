package dk.mrspring.kitchen.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import dk.mrspring.kitchen.ModAchievements;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by Konrad on 24-01-2015.
 */
public class PlayerEvents
{
    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event)
    {
        EntityPlayer player = event.player;
        player.triggerAchievement(ModAchievements.installed);
    }
}
