package dk.mrspring.kitchen.config;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.lang.reflect.Modifier;

/**
 * Created by MrSpring on 29-09-2014 for TheKitchenMod.
 */
public class BaseConfig
{
	private File location;
	private String label;

	public BaseConfig(){}
	
	public BaseConfig(File location,String name)
	{
		this.setLocation(location);
		this.setLabel(name);
	}

	public BaseConfig readFromFile() throws IOException
	{
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
		Gson gson = builder.create();

		if (this.location.exists())
		{
			BufferedReader reader = new BufferedReader(new FileReader(this.location));
			BaseConfig config = gson.fromJson(reader, this.getClass());
			config.setLocation(this.location);
			config.setLabel(this.getLabel());
			return config;
		} else
		{
			this.location.getParentFile().mkdir();
			this.location.createNewFile();
			this.writeToFile();
			return this;
		}
	}

	public void writeToFile() throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(this.location));
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting().setExclusionStrategies(new ExclusionStrategy()
		{
			@Override
			public boolean shouldSkipField(FieldAttributes f)
			{
				return f.hasModifier(Modifier.PRIVATE);
			}

			@Override
			public boolean shouldSkipClass(Class<?> clazz)
			{
				return false;
			}
		});
		Gson gson = builder.create();
		String json = gson.toJson(this);
		this.location.createNewFile();
		FileWriter writer = new FileWriter(this.location);
		writer.write(json);
		writer.close();
	}

	public BaseConfig setLocation(File location)
	{
		this.location = location;
		return this;
	}

	public File getLocation()
	{
		return location;
	}

	public BaseConfig setLabel(String label)
	{
		this.label = label;
		return this;
	}

	public String getLabel()
	{
		return label;
	}

	/*public void readFromFile() throws IOException
	{
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
		Gson gson = builder.create();

		BufferedReader reader = new BufferedReader(new FileReader(this.location));
		config = gson.fromJson(reader, getClass());
		//config.setLocation(this.location);
	}*/
}
