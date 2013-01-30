package quantumpack.net;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import quantumpack.*;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;


public class PacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player packetSender) {

		// 0x03: Importing recipe from NEI: Gui sending an ItemStack to Container.

		byte action = -1;
		if( packet.channel.equals("qp_channel") ) {
			try {
				DataInputStream packetData = new DataInputStream(new ByteArrayInputStream( packet.data ));
				action = packetData.readByte();

				System.out.println("Packet unhandled: " + action);
			} catch (IOException e) {
				FMLCommonHandler.instance().raiseException(e, "Packet Handler: "+action, true);
			}
		}

	}


	private ItemStack getItemStack(DataInputStream packetData) {
		try {
			short itemID = packetData.readShort();
			if( itemID <= 0 )
				return null;

			byte stackSize = packetData.readByte();
			short itemDamage = packetData.readShort();

			ItemStack stack = new ItemStack(itemID, stackSize, itemDamage);
			NBTTagCompound stackNbtTag = getNBT(packetData);
			stack.setTagCompound(stackNbtTag);

			return stack;
		} catch (IOException e) {
			return null;
		}
	}

	private NBTTagCompound getNBT(DataInputStream packetData) throws IOException {
		short nbtLength = packetData.readShort();
		if( nbtLength <= 0 )
			return null;

		byte[] byteArray = new byte[nbtLength];
		for( int i=0; i<nbtLength; i++ ) {
			byteArray[i] = packetData.readByte();
		}

		return CompressedStreamTools.decompress(byteArray);
	}

}