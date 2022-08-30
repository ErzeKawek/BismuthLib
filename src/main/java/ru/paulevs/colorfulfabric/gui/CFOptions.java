package ru.paulevs.colorfulfabric.gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CFOptions {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final File FILE = new File(FabricLoader.getInstance().getConfigDir().toString(), "colorfulfabric.json");
	private static JsonObject config;
	
	static {
		if (FILE.exists()) {
			try {
				FileReader reader = new FileReader(FILE);
				config = GSON.fromJson(reader, JsonObject.class);
				reader.close();
			}
			catch (IOException e) {
				config = new JsonObject();
				e.printStackTrace();
			}
		}
		else {
			config = new JsonObject();
		}
	}
	
	private static int mapRadiusXZ = getInt("mapRadiusXZ", 2);
	private static int mapRadiusY = getInt("mapRadiusY", 2);
	private static boolean fastLight = getBool("fastLight", false);
	private static int threads = getInt("threads", 4);
	private static boolean modifyColor = getBool("modifyColor", false);
	
	public static final OptionInstance<Integer> MAP_RADIUS_XZ = new OptionInstance<>(
		"colorfulfabric.options.mapRadiusXZ",
		OptionInstance.noTooltip(),
		(component, integer) -> {
			int i = integer * 2 + 1;
			return Options.genericValueLabel(component, Component.translatable("options.biomeBlendRadius." + i));
		},
		new OptionInstance.IntRange(1, 7),
		mapRadiusXZ,
		val -> {
			setInt("mapRadiusXZ", val);
			mapRadiusXZ = val;
		}
	);
	public static final OptionInstance<Integer> MAP_RADIUS_Y = new OptionInstance<>(
		"colorfulfabric.options.mapRadiusY",
		OptionInstance.noTooltip(),
		(component, integer) -> {
			int i = integer * 2 + 1;
			return Options.genericValueLabel(component, Component.translatable("colorfulfabric.options.mapRadiusY." + i));
		},
		new OptionInstance.IntRange(1, 7),
		mapRadiusY,
		val -> {
			setInt("mapRadiusY", val);
			mapRadiusY = val;
		}
	);
	private static final OptionInstance<Boolean> FAST_LIGHT = new OptionInstance<>(
		"colorfulfabric.options.lightType",
		OptionInstance.noTooltip(),
		(component, bool) -> Component.translatable("colorfulfabric.options.fastLight." + bool),
		OptionInstance.BOOLEAN_VALUES,
		fastLight,
		val -> {
			setBool("fastLight", val);
			fastLight = val;
		}
	);
	private static final OptionInstance<Integer> THREADS = new OptionInstance<>(
		"colorfulfabric.options.threads",
		OptionInstance.noTooltip(),
		(component, i) -> Options.genericValueLabel(component, i),
		new OptionInstance.IntRange(1, 16),
		threads,
		val -> {
			setInt("threads", val);
			threads = val;
		}
	);
	private static final OptionInstance<Boolean> MODIFY_COLOR = new OptionInstance<>(
		"colorfulfabric.options.modifyColor",
		OptionInstance.noTooltip(),
		(component, bool) -> Component.translatable("colorfulfabric.options.modifyColor." + bool),
		OptionInstance.BOOLEAN_VALUES,
		modifyColor,
		val -> {
			setBool("modifyColor", val);
			modifyColor = val;
		}
	);
	
	public static final OptionInstance[] OPTIONS = new OptionInstance[] {
		FAST_LIGHT, THREADS, MODIFY_COLOR
	};
	
	public static int getMapRadiusXZ() {
		return mapRadiusXZ * 2 + 1;
	}
	
	public static int getMapRadiusY() {
		return mapRadiusY * 2 + 1;
	}
	
	public static boolean isFastLight() {
		return fastLight;
	}
	
	public static int getThreadCount() {
		return threads;
	}
	
	public static boolean modifyColor() {
		return modifyColor;
	}
	
	private static int getInt(String name, int def) {
		return config.has(name) ? config.get(name).getAsInt() : def;
	}
	
	private static void setInt(String name, int val) {
		config.add(name, new JsonPrimitive(val));
	}
	
	private static boolean getBool(String name, boolean def) {
		return config.has(name) ? config.get(name).getAsBoolean() : def;
	}
	
	private static void setBool(String name, boolean val) {
		config.add(name, new JsonPrimitive(val));
	}
	
	public static void save() {
		String line = GSON.toJson(config);
		try {
			FileWriter writer = new FileWriter(FILE);
			writer.write(line);
			writer.flush();
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
