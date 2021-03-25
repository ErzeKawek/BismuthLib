package ru.paulevs.colorfulfabric;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.texture.TextureUtil;

public class SectionTexture {
	private static final IntBuffer PIXELS = BufferUtils.createIntBuffer(2048);
	private static final int[] EMPY = new int[2048];
	private final int textureID;
	
	public SectionTexture() {
		this(EMPY);
	}
	
	public SectionTexture(int[] data) {
		textureID = TextureUtil.generateId();
		fillTexture(data);
	}
	
	public SectionTexture fillTexture(int[] data) {
		initTexture();
		PIXELS.rewind();
		PIXELS.put(data);
		PIXELS.flip();
		GL20.glTexImage2D(GL20.GL_TEXTURE_2D, 0, GL20.GL_RGBA8, 32, 64, 0, GL20.GL_RGBA, GL20.GL_INT, PIXELS);
		GL20.glBindTexture(GL20.GL_TEXTURE_2D, 0);
		return this;
	}
	
	private void initTexture() {
		GL20.glBindTexture(GL20.GL_TEXTURE_2D, textureID);
		GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MAG_FILTER, GL20.GL_NEAREST);
		GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MIN_FILTER, GL20.GL_NEAREST);
		GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_S, GL20.GL_CLAMP_TO_EDGE);
		GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_T, GL20.GL_CLAMP_TO_EDGE);
	}
	
	public void bind() {
		GL20.glActiveTexture(GL20.GL_TEXTURE6);
		GL20.glBindTexture(GL20.GL_TEXTURE_2D, textureID);
		GL20.glActiveTexture(GL20.GL_TEXTURE0);
	}

	public void delete() {
		RenderSystem.deleteTexture(textureID);
	}
}