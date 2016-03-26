package dk.mrspring.kitchen;

import dk.mrspring.kitchen.common.item.ItemContainerBase;
import dk.mrspring.kitchen.common.item.ItemNoLogic;
import dk.mrspring.kitchen.common.util.RegistryUtils;
import net.minecraft.item.Item;

import static dk.mrspring.kitchen.common.util.RegistryUtils.registerItem;

/**
 * Created on 07-03-2016 for TheKitchenMod.
 */
public class KitchenItems
{
    protected KitchenItems()
    {
    }

    public final Item timer = new ItemNoLogic("timer");
    public final Item burnt_meat = new ItemNoLogic("burnt_meat");
    public final ItemContainerBase mixing_bowl = new ItemContainerBase("MixType", "mixing_bowl").setContainsFormat("item.mixing_bowl.desc", "mix.%s.name");
    public final ItemContainerBase jam_jar = new ItemContainerBase("JamType", "jam_jar");

    public void register()
    {
        registerItem(timer);
        registerItem(burnt_meat);
        registerItem(mixing_bowl);
        registerItem(jam_jar);
    }
}
