package quantumpack.net;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.MinecraftForgeClient;
import quantumpack.core.Core;

public class ClientProxy extends CommonProxy {

	@SideOnly(Side.CLIENT)
	public static GuiScreen getCurrentScreen() {
		return Minecraft.getMinecraft().currentScreen;
	}

	public void registerRenderInformation() {
		MinecraftForgeClient.preloadTexture(Core.TEXTURE_ITEMS);
		MinecraftForgeClient.preloadTexture(Core.TEXTURE_BLOCKS);
		MinecraftForgeClient.preloadTexture(Core.TEXTURE_MACHINES);
	}

}