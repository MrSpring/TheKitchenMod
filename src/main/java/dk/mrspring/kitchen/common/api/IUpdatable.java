package dk.mrspring.kitchen.common.api;

/**
 * Created on 30-04-2016 for TheKitchenMod.
 */
public interface IUpdatable
{
    /**
     * Sends the tile entities information to the client and tells Minecraft that the tile entity has changed and
     * should be saved to the world files.
     */
    void markForUpdate();
}
