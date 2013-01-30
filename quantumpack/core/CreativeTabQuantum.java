package quantumpack.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import quantumpack.*;


public class CreativeTabQuantum extends CreativeTabs {

	public CreativeTabQuantum() {
		super( "Quantum Pack" );
	}

	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack( Core.pulvOff, 1, 1 );
	}

}