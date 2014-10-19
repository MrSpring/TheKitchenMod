package dk.mrspring.kitchen.pot;

/**
 * Created by MrSpring on 25-09-2014 for ModJam4.
 */
public enum Jam
{
	EMPTY(000000),
	STRAWBERRY(16196364),
	APPLE(14415786);

	final int color;

	public int getColor()
	{
		return color;
	}

	Jam(int color)
	{
		this.color = color;
	}

}