package dk.mrspring.kitchen;

public class ModInfo
{
	// The mod-id of the Mod
	public static final String modid = "kitchen";
	// The name of the Mod
	public static final String name = "Kitchen";
	// The version of the Mod
	public static final String version = "1.3.0";

	public static String toTexture(String name)
	{
		return modid+":"+name;
	}
}
