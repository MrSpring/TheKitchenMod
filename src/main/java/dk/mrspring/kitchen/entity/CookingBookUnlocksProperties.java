package dk.mrspring.kitchen.entity;

import dk.mrspring.javanbt.NBTType;
import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.config.BaseConfig;
import dk.mrspring.kitchen.config.KitchenConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 09-09-2015 for TheKitchenMod.
 */
public class CookingBookUnlocksProperties implements IExtendedEntityProperties
{
    public List<String> unlocks = new ArrayList<String>();

    public static final String UNLOCKED_CHAPTERS = "UnlockedCookingBookChapters";

    public static CookingBookUnlocksProperties getFromPlayer(EntityPlayer player)
    {
        IExtendedEntityProperties properties = player.getExtendedProperties(CookingBookUnlocksProperties.UNLOCKED_CHAPTERS);
        CookingBookUnlocksProperties unlocks;
        if (properties == null || !(properties instanceof CookingBookUnlocksProperties))
        {
            player.registerExtendedProperties(CookingBookUnlocksProperties.UNLOCKED_CHAPTERS, unlocks = new CookingBookUnlocksProperties());
        } else unlocks = (CookingBookUnlocksProperties) properties;
        return unlocks;
    }

    public static boolean hasPlayerUnlocked(String chapter, EntityPlayer player)
    {
        return (ModConfig.getKitchenConfig().cooking_book_creative_auto_unlock && player.capabilities.isCreativeMode) ||
                getFromPlayer(player).hasUnlocked(chapter);
    }

    public static void unlockChapter(String chapter, EntityPlayer player)
    {
        getFromPlayer(player).unlockChapter(chapter);
    }

    public boolean hasUnlocked(String chapter)
    {
        return unlocks.contains(chapter);
    }

    public void unlockChapter(String chapter)
    {
        this.unlocks.add(chapter);
    }

    @Override
    public void saveNBTData(NBTTagCompound compound)
    {
        NBTTagList unlocksList = new NBTTagList();
        for (String s : unlocks)
            unlocksList.appendTag(new NBTTagString(s));
        compound.setTag(UNLOCKED_CHAPTERS, unlocksList);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound)
    {
        NBTTagList unlocksList = compound.getTagList(UNLOCKED_CHAPTERS, NBTType.STRING.getId());
        for (int i = 0; i < unlocksList.tagCount(); i++)
        {
            String s = unlocksList.getStringTagAt(i);
            if (s != null) unlocks.add(s);
        }
    }

    @Override
    public void init(Entity entity, World world)
    {
    }
}
