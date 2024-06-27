package ru.paulevs.bismuthlib.mixin;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TextureAtlasSprite.class)
public interface TextureAtlasSpriteAccessor {
	@Accessor("mainImage")
	NativeImage[] cf_getImages();
}
