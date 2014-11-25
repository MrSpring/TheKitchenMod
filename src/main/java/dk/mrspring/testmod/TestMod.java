package dk.mrspring.testmod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.GameRegisterer;
import dk.mrspring.kitchen.item.ItemBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by MrSpring on 04-11-2014.
 */
//@Mod(modid = "TM", name = "Test Mod", version = "1.0.0")
public class TestMod
{
    public static Item testSandwichableItem;
    public static Item testPanInput;
    public static Item testPanOutput;
    public static Item testOvenInputItem;
    public static Item testOvenOutputItem;
    public static Item testStrawberryItem;
    public static Item testOrangeItem;
    public static Item testOrangeJam;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        System.out.println("Loading test Mod!");

        testSandwichableItem = new ItemBase("test_item", false).setCreativeTab(CreativeTabs.tabFood);
        testPanInput = new ItemBase("pan_input_item", false).setCreativeTab(CreativeTabs.tabFood);
        testPanOutput = new ItemBase("pan_output_item", false).setCreativeTab(CreativeTabs.tabFood);
        testOvenInputItem = new ItemBase("oven_input_item", false).setCreativeTab(CreativeTabs.tabFood);
        testOvenOutputItem = new ItemBase("oven_output_item", false).setCreativeTab(CreativeTabs.tabFood);
        testStrawberryItem = new ItemBase("strawberry_imposter", false).setCreativeTab(CreativeTabs.tabFood);
        testOrangeItem = new ItemBase("orange",false).setCreativeTab(CreativeTabs.tabFood);
        testOrangeJam = new ItemBase("orange_jam",false);

        GameRegisterer.registerItem(testSandwichableItem);
        GameRegisterer.registerItem(testPanInput);
        GameRegisterer.registerItem(testPanOutput);
        GameRegisterer.registerItem(testOvenInputItem);
        GameRegisterer.registerItem(testOvenOutputItem);
        GameRegisterer.registerItem(testStrawberryItem);
        GameRegisterer.registerItem(testOrangeItem);

        FMLInterModComms.sendMessage("kitchen", "makeItemSandwichable",
                "{" +
                    "\"item_name\": \"" + GameRegistry.findUniqueIdentifierFor(testSandwichableItem).toString() + "\"," +
                    "\"heal_amount\": 0," +
                    "\"is_bread\": true," +
                    "\"hide_information\": false" +
                "}");


        FMLInterModComms.sendMessage("kitchen", "linkItemAndIngredient", GameRegistry.findUniqueIdentifierFor(testStrawberryItem).toString() + "," + "strawberry");


        NBTTagCompound recipeCompound = new NBTTagCompound();

        ItemStack input = new ItemStack(testOvenInputItem, 1);
        ItemStack output = new ItemStack(testOvenOutputItem, 1);

        NBTTagCompound inputCompound = new NBTTagCompound();
        NBTTagCompound outputCompound = new NBTTagCompound();

        input.writeToNBT(inputCompound);
        output.writeToNBT(outputCompound);

        recipeCompound.setTag("Input", inputCompound);
        recipeCompound.setTag("Output", outputCompound);

        FMLInterModComms.sendMessage("kitchen", "addOvenRecipe", recipeCompound);


        String inputName = GameRegistry.findUniqueIdentifierFor(testPanInput).toString();
        String outputName = GameRegistry.findUniqueIdentifierFor(testPanOutput).toString();

        FMLInterModComms.sendMessage("kitchen","addPanRecipe",inputName+","+outputName);


        NBTTagCompound jamInfo = new NBTTagCompound();

        jamInfo.setString("JamName","orange");
        jamInfo.setInteger("Color",16751116);
        jamInfo.setString("InputItem",GameRegistry.findUniqueIdentifierFor(testOrangeItem).toString());
        jamInfo.setString("JamItem",GameRegistry.findUniqueIdentifierFor(testOrangeJam).toString());

        FMLInterModComms.sendMessage("kitchen","addJam",jamInfo);
    }
}
