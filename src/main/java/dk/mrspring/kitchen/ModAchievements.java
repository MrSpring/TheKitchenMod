package dk.mrspring.kitchen;

import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

/**
 * Created by Konrad on 24-01-2015.
 */
public class ModAchievements
{
    public static Achievement installed;

    public static Achievement cutting_board;
    public static Achievement first_sandwich;

    public static Achievement oven;

    public static Achievement frying_pan;
    public static Achievement bacon;

    public static void load()
    {
        installed = new Achievement("achievement.kitchen.installed", "kitchen.installed", 0, 0, KitchenItems.basic_sandwich, null).registerStat();
        cutting_board = new Achievement("achievement.kitchen.cutting_board", "kitchen.cutting_board", 1, 2, KitchenBlocks.board, installed).registerStat();
        first_sandwich = new Achievement("achievement.kitchen.first_sandwich", "kitchen.first_sandwich", 3, 2, KitchenItems.basic_sandwich, cutting_board).registerStat();
        oven = new Achievement("achievement.kitchen.oven", "kitchen.oven", -1, 2, KitchenBlocks.oven, installed).registerStat();
        frying_pan = new Achievement("achievement.kitchen.frying_pan", "kitchen.frying_pan", 2, 0, KitchenBlocks.frying_pan, installed).registerStat();
        bacon = new Achievement("achievement.kitchen.bacon", "kitchen.bacon", 3, 2, KitchenItems.bacon, frying_pan).registerStat();

        AchievementPage.registerAchievementPage(new AchievementPage("Kitchen", installed, cutting_board, first_sandwich, oven, frying_pan, bacon));
    }
}
