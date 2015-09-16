package dk.mrspring.kitchen;

import net.minecraft.world.World;

public class CommonProxy
{
	public String versionHighlights = "";

	public void registerRenderers()
	{
		
	}

	public void spawnDingParticle(World world, double posX, double posY, double posZ)
	{
		System.out.println("Server spawn");
	}
}
