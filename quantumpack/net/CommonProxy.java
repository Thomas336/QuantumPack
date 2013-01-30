package quantumpack.net;


import quantumpack.Pulveriser.ContainerPulv;
import quantumpack.Pulveriser.GuiPulv;
import quantumpack.Pulveriser.TilePulv;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler {

	public void registerRenderInformation() { }

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		// ID:
			// 0: pulv

    	System.out.println("ba");
		if( ID == 0 ) { // Pulv
			TilePulv pulv = (TilePulv) world.getBlockTileEntity(x, y, z);
			if( pulv == null )
				return null;

			if( pulv instanceof TilePulv ) {
				return new ContainerPulv(player.inventory, (TilePulv) pulv);
			}
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		// ID:
			// 0: pulv

    	System.out.println("bb");
		if( ID == 0 ) { // Pulv
            TilePulv pulv = (TilePulv) world.getBlockTileEntity(x, y, z);
            if( pulv == null )
                return null;

            if( pulv instanceof TilePulv ) {
                return new GuiPulv(player.inventory, (TilePulv) pulv);
            }
        }

		return null;
	}

}