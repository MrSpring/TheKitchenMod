package dk.mrspring.kitchen.item;

import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.jam.Jam;
import dk.mrspring.kitchen.tileentity.TileEntityJamJar;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by MrSpring on 25-09-2014 for ModJam4.
 */
public class ItemBlockJam extends ItemBlock
{
	IIcon[] jamIcon = new IIcon[6];

	public ItemBlockJam(Block name)
	{
		super(name);
		this.setCreativeTab(Kitchen.instance.tab);
		this.setUnlocalizedName("jam_jar");
		this.setTextureName(ModInfo.modid + ":jam_jar");
		this.setMaxStackSize(1);
	}

	@Override
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}

	@Override
	public int getRenderPasses(int metadata)
	{
		if (metadata == 0)
			return 1;
		else return 2;
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass)
	{
		if (pass == 1 || stack.getItemDamage() == 0)
			return super.getIcon(stack, pass);
		else
		{
			if (stack.stackTagCompound != null)
			{
				NBTTagCompound jamInfo = stack.stackTagCompound.getCompoundTag("JamInfo");
				if (jamInfo != null)
				{
					int usesLeft = jamInfo.getInteger("UsesLeft");
					if (usesLeft != 0)
					{
						return this.jamIcon[usesLeft-1];
					}
				}
			}
			System.out.println("");
			return super.getIcon(stack, pass);
		}
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		if (par1ItemStack.stackTagCompound != null)
		{
			NBTTagCompound jamInfo = par1ItemStack.stackTagCompound.getCompoundTag("JamInfo");
			if (jamInfo != null)
			{
				int usesLeft = jamInfo.getInteger("UsesLeft");
				if (usesLeft!=0)
				{
					par3List.add(StatCollector.translateToLocal("item.jam_jar.uses_left_msg" + ": " + usesLeft));
				}
			}
		}
	}

	@Override
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		if (par2 == 0 && par1ItemStack.getItemDamage() != 0)
		{
			Jam jam = Jam.EMPTY;

			if (par1ItemStack.stackTagCompound != null)
			{
				NBTTagCompound jamInfo = par1ItemStack.stackTagCompound.getCompoundTag("JamInfo");
				if (jamInfo != null)
				{
					jam = Jam.valueOf(jamInfo.getString("JamType"));
				}
			}

			return jam.getColor();
		} else return super.getColorFromItemStack(par1ItemStack, par2);
	}

	@Override
	public void registerIcons(IIconRegister par1IconRegister)
	{
		super.registerIcons(par1IconRegister);
		for (int i = 1; i < this.jamIcon.length; i++)
			this.jamIcon[i] = par1IconRegister.registerIcon(ModInfo.modid + ":jam_jar_filling_" + (i+1));
		this.jamIcon[0] = this.itemIcon;
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack)
	{
		if (par1ItemStack.getItemDamage() != 0)
		{
			NBTTagCompound compound = par1ItemStack.getTagCompound();
			if (compound != null)
			{
				NBTTagCompound jamInfo = compound.getCompoundTag("JamInfo");
				if (jamInfo != null)
				{
					Jam jam = Jam.valueOf(jamInfo.getString("JamType"));
					return StatCollector.translateToLocal("jam." + jam.name().toLowerCase() + ".name") + " " + StatCollector.translateToLocal("item.jam_jar.filled.name");
				}
			}
		}
		return StatCollector.translateToLocal("item.jam_jar.empty.name");
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
	{
		if (player.isSneaking())
		{
			super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
			world.setBlockMetadataWithNotify(x, y, z, 2, stack.getItemDamage());
			TileEntityJamJar tileEntity = TileEntityJamJar.create(stack);
			world.setTileEntity(x, y, z, tileEntity);
			return true;
		} else return false;
	}
}