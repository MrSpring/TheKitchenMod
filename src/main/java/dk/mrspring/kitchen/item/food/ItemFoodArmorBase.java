package dk.mrspring.kitchen.item.food;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.ModInfo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import squeek.applecore.api.food.FoodValues;
import squeek.applecore.api.food.ItemFoodProxy;

/**
 * Created by Konrad on 13-05-2015.
 */
@Optional.Interface(iface = "squeek.applecore.api.food.IEdible", modid = "AppleCore")
public class ItemFoodArmorBase extends ItemArmor implements squeek.applecore.api.food.IEdible
{
    private static final ArmorMaterial MATERIAL = EnumHelper.addArmorMaterial("FOOD", 20, new int[]{0, 0, 0, 0}, 0);

    private int heal;
    private float saturation;
    private String armorTexture;
    private int eatTime = 32;
    private boolean canAlwaysEat = false;

    public ItemFoodArmorBase(String name, String textureName, int healAmount, float saturation, String armorTexture, int armorType, CreativeTabs creativeTab)
    {
        super(MATERIAL, 0, armorType);

        this.setUnlocalizedName(name);
        this.setTextureName(textureName);

        this.setCreativeTab(creativeTab);

        this.armorTexture = armorTexture;
        this.heal = healAmount;
        this.saturation = saturation;
    }

    public ItemFoodArmorBase(String name, String textureName, int healAmount, float saturation, String armorTexture, int armorType)
    {
        this(name, textureName, healAmount, saturation, armorTexture, armorType, Kitchen.instance.foodTab);
    }

    public ItemFoodArmorBase(String name, String textureName, int healAmount, String armorTexture, int armorType, CreativeTabs creativeTab)
    {
        this(name, textureName, healAmount, 0.6F, armorTexture, armorType, creativeTab);
    }

    public ItemFoodArmorBase(String name, String textureName, int healAmount, String armorTexture, int armorType)
    {
        this(name, textureName, healAmount, 0.6F, armorTexture, armorType, Kitchen.instance.foodTab);
    }

    public ItemFoodArmorBase(String name, int healAmount, float saturation, String armorTexture, int armorType, CreativeTabs creativeTab)
    {
        this(name, ModInfo.toTexture(name), healAmount, saturation, armorTexture, armorType, creativeTab);
    }

    public ItemFoodArmorBase(String name, int healAmount, float saturation, String armorTexture, int armorType)
    {
        this(name, ModInfo.toTexture(name), healAmount, saturation, armorTexture, armorType);
    }

    public ItemFoodArmorBase(String name, int healAmount, String armorTexture, int armorType, CreativeTabs creativeTab)
    {
        this(name, ModInfo.toTexture(name), healAmount, 0.6F, armorTexture, armorType, creativeTab);
    }

    public ItemFoodArmorBase(String name, int healAmount, String armorTexture, int armorType)
    {
        this(name, ModInfo.toTexture(name), healAmount, 0.6F, armorTexture, armorType, Kitchen.instance.foodTab);
    }

    public int getEatTime()
    {
        return this.eatTime;
    }

    public ItemFoodArmorBase setEatTime(int time)
    {
        this.eatTime = time;
        return this;
    }

    public boolean getCanAlwaysEat()
    {
        return this.canAlwaysEat;
    }

    public ItemFoodArmorBase setCanAlwaysEat(boolean can)
    {
        this.canAlwaysEat = can;
        return this;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return this.getEatTime();
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (player.canEat(this.getCanAlwaysEat()))
            player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return stack;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.eat;
    }

    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
        --stack.stackSize;
        int healAmount = this.getHealAmount(stack);
        float saturation = this.getSaturation(stack);
        if (Loader.isModLoaded("AppleCore"))
            this.onEatenCompatibility(stack, player);
        else player.getFoodStats().addStats(healAmount, saturation);

        world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
        return stack;
    }

    @Optional.Method(modid = "AppleCore")
    public void onEatenCompatibility(ItemStack itemStack, EntityPlayer player)
    {
        player.getFoodStats().func_151686_a(new ItemFoodProxy(this), itemStack);
    }

    public int getHealAmount(ItemStack stack)
    {
        return this.heal;
    }

    public float getSaturation(ItemStack stack)
    {
        return this.saturation;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        return armorTexture;
    }

    public FoodValues getFoodValues(ItemStack itemStack)
    {
        return new FoodValues(this.getHealAmount(itemStack), this.getSaturation(itemStack));
    }
}
