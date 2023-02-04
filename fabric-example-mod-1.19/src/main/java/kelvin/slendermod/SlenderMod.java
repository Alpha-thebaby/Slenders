package kelvin.slendermod;

import kelvin.slendermod.client.rendering.RenderingRegistry;
import kelvin.slendermod.common.blocks.BlockRegistry;
import kelvin.slendermod.common.entities.EntityRegistry;
import kelvin.slendermod.common.entities.EntitySlenderman;
import kelvin.slendermod.common.entities.EntitySmallSlender;
import kelvin.slendermod.common.items.ItemRegistry;
import kelvin.slendermod.common.sounds.SoundRegistry;
import kelvin.slendermod.network.client.ClientPacketHandler;
import kelvin.slendermod.network.server.ServerPacketHandler;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.WindowFramebuffer;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL30;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class SlenderMod implements ModInitializer, ClientModInitializer {
	public static final String MODID = "slendermod";
	public static final Logger LOGGER = LoggerFactory.getLogger("slendermod");

	private Framebuffer framebuffer;

	private final ManagedShaderEffect motionBlurShader = ShaderEffectManager.getInstance().manage(new Identifier(MODID, "shaders/post/motionblur.json"));

	public static float fright_blur = 0.0f;
	public static float fear_zoom = 1.0f;
	public static float scared_timer = 0;
	public static float scare_music = 0;
	public static float breath_timer = 0;

	public static float heartbeat_delay = 0;

	public static float iTime = 0;

	public static KeyBinding crawlKey;
	public static SoundInstance somethingApproachesInstance;

	@Override
	public void onInitialize() {
		EntityRegistry.Register();
		BlockRegistry.Register();
		ItemRegistry.Register();

		ServerPacketHandler.Start();
	}


	@Override
	public void onInitializeClient() {

		ClientPacketHandler.Start();

		crawlKey = new KeyBinding("key.crawl", GLFW.GLFW_KEY_G, "key.categories.movement");


		RenderingRegistry.Register();
		SoundRegistry.Register();

		ClientTickEvents.START_CLIENT_TICK.register((client) -> {
			if (client.isPaused()) {
				return;
			}

			float tickDelta = 1.0f / 20.0f;
			if (scared_timer > 0) {
				if (heartbeat_delay <= 0) {
					Entity camera = client.getCameraEntity();
					if (camera != null) {
						playPositionlessSound(client, camera.getX(), camera.getY(), camera.getZ(), SoundRegistry.HEARTBEAT, SoundCategory.AMBIENT, 1.0f, 1.0f);
						heartbeat_delay = 20 * 5;
					}
				}
				fear_zoom = MathHelper.lerp(tickDelta * 8, fear_zoom, 0.5f);
				fright_blur = MathHelper.lerp(tickDelta * 4, fright_blur, 1);

				scared_timer -= tickDelta;
				if (scared_timer <= 0.01f) {
					scared_timer = 0;
				}
			} else {
				fright_blur = MathHelper.lerp(tickDelta, fright_blur, 0);
				if (fright_blur <= 0.01f) {
					fear_zoom = 2.0f;
					fright_blur = 0;
				}
			}

			if (scare_music > 0) {
				scare_music--;
			}
			if (heartbeat_delay > 0) {
				heartbeat_delay--;
			}
			if (breath_timer > 0) {
				breath_timer--;
			}

			if (scared_timer <= 0) {
				client.getSoundManager().stop(somethingApproachesInstance);
			}
		});

		ShaderEffectRenderCallback.EVENT.register(tickDelta -> {
			MinecraftClient minecraft = MinecraftClient.getInstance();

			if (minecraft == null) {
				return;
			}

			if (this.framebuffer == null) {
				this.framebuffer = new WindowFramebuffer(minecraft.getWindow().getFramebufferWidth(), minecraft.getWindow().getFramebufferHeight());
				this.framebuffer.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
				this.framebuffer.clear(Util.getOperatingSystem() == Util.OperatingSystem.OSX);

			}

			if (minecraft.getFramebuffer().textureWidth != this.framebuffer.textureWidth ||
			minecraft.getFramebuffer().textureHeight != this.framebuffer.textureHeight) {
				this.framebuffer.resize(minecraft.getFramebuffer().textureWidth, minecraft.getFramebuffer().textureHeight, true);
			}

			motionBlurShader.setSamplerUniform("LastFrame", this.framebuffer);
			motionBlurShader.setUniformValue("fright", fright_blur);
			motionBlurShader.setUniformValue("fear_zoom", fear_zoom);
			motionBlurShader.setUniformValue("iTime", iTime);
			iTime += tickDelta;
			motionBlurShader.render(tickDelta);
			copyFrameBufferTexture(this.framebuffer.textureWidth, this.framebuffer.textureHeight, minecraft.getFramebuffer().fbo, minecraft.getFramebuffer().getColorAttachment(), this.framebuffer.fbo, this.framebuffer.getColorAttachment());

			if (minecraft.world != null) {
				minecraft.world.getEntities().forEach((entity) -> {
					if (entity instanceof EntitySlenderman || entity instanceof EntitySmallSlender) {
						if (lookingAtEntity(minecraft, entity)) {
							scare(minecraft);
						}
					}
				});
			}

		});
	}

	private void copyFrameBufferTexture(int width, int height, int fboIn, int textureIn, int fboOut, int textureOut)
	{
		// Bind input FBO + texture to a color attachment
		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, fboIn);
		GL30.glFramebufferTexture2D(GL30.GL_READ_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL30.GL_TEXTURE_2D, textureIn, 0);
		GL30.glReadBuffer(GL30.GL_COLOR_ATTACHMENT0);

		// Bind destination FBO + texture to another color attachment
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, fboOut);
		GL30.glFramebufferTexture2D(GL30.GL_DRAW_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT1, GL30.GL_TEXTURE_2D, textureOut, 0);
		GL30.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT1);

		// specify source, destination drawing (sub)rectangles.
		GL30.glBlitFramebuffer(0, 0, width, height,
				0, 0, width, height,
				GL30.GL_COLOR_BUFFER_BIT, GL30.GL_NEAREST);

		// unbind the color attachments
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT1, GL30.GL_TEXTURE_2D, 0, 0);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL30.GL_TEXTURE_2D, 0, 0);
	}

	public static void scare(MinecraftClient minecraft)
	{
		float x = 0;
		float y = 0;
		float z = 0;

		if (minecraft.cameraEntity != null) {
			x = (float)minecraft.cameraEntity.getX();
			y = (float)minecraft.cameraEntity.getY();
			z = (float)minecraft.cameraEntity.getZ();
		}

		if (scared_timer <= 0) {
			playPositionlessSound(minecraft, x, y, z, SoundRegistry.SHOCK, SoundCategory.AMBIENT, 1.0f, new Random().nextFloat() * 0.5f - 0.25f + 1.0f);

			fear_zoom = 2.0f;
		}
		if (breath_timer <= 0) {
			breath_timer = 20 * 60;
			playPositionlessSound(minecraft, x, y, z, SoundRegistry.BREATHING, SoundCategory.AMBIENT, 1.0f, 1.0f);
			playPositionlessSound(minecraft, x, y, z, SoundRegistry.WIND, SoundCategory.AMBIENT, 1.0f, 1.0f);
		}
		if (scare_music <= 0) {
			scare_music = 20 * 60 * 2.5f;
			somethingApproachesInstance = playPositionlessSound(minecraft, x, y, z, SoundRegistry.SOMETHING_APPROACHES, SoundCategory.AMBIENT, 1.0f, new Random().nextFloat() * 0.5f - 0.25f + 1.0f);
		}
		scared_timer = 10;
		fear_zoom = MathHelper.lerp(0.5f, fear_zoom, 2.0f);
	}

	public static boolean lookingAtEntity(MinecraftClient minecraft, Entity entity) {
		Entity camera = minecraft.getCameraEntity();
		if (camera != null) {
			if (camera instanceof PlayerEntity) {
				Quaternion quat = minecraft.gameRenderer.getCamera().getRotation();
				Vec3f vec = new Vec3f(0, 0, 1);
				vec.rotate(quat);

				Vec3d lookVec = new Vec3d(vec.getX(), vec.getY(), vec.getZ()).normalize();

				Vec3d direction = entity.getPos().subtract(camera.getPos()).normalize();

				double dot = lookVec.dotProduct(direction);
				if (dot > 0.5f && ((PlayerEntity)camera).canSee(entity)) {
					return true;
				}
			}
		}
		return false;
	}

	public static SoundInstance playPositionlessSound(MinecraftClient minecraft, double x, double y, double z, SoundEvent event, SoundCategory category, float volume, float pitch) {
		PositionedSoundInstance positionedSoundInstance = new PositionedSoundInstance(event.getId(), category, volume, pitch, minecraft.world.random, false, 0, SoundInstance.AttenuationType.NONE, x, y, z, false);
		minecraft.getSoundManager().play(positionedSoundInstance);
		return positionedSoundInstance;
	}
}
