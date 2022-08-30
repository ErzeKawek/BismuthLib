package ru.paulevs.colorfulfabric.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.paulevs.colorfulfabric.ColorfulFabricClient;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Inject(method = "<init>*", at = @At("TAIL"))
	private void cf_onMinecraftInit(GameConfig gameConfig, CallbackInfo info) {
		ColorfulFabricClient.initData();
	}
}
