package quantumpack.core;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class Util {
	public static void dropItemAsEntity(World world, int x, int y, int z, ItemStack itemStack) {
		Random random = new Random();
		float var10 = random.nextFloat() * 0.8F + 0.1F;
		float var11 = random.nextFloat() * 0.8F + 0.1F;
		EntityItem item;

		for( float var12 = random.nextFloat() * 0.8F + 0.1F; itemStack.stackSize > 0; world.spawnEntityInWorld( item ) ) {
			int var13 = random.nextInt( 21 ) + 10;

			if( var13 > itemStack.stackSize ) {
				var13 = itemStack.stackSize;
			}

			itemStack.stackSize -= var13;
			item = new EntityItem( world, (double) (x + var10), (double) (y + var11), (double) (z + var12), new ItemStack( itemStack.itemID, var13, itemStack.getItemDamage() ) );
			float var15 = 0.05F;
			item.motionX = (random.nextGaussian() * var15);
			item.motionY = (random.nextGaussian() * var15 + 0.2F);
			item.motionZ = (random.nextGaussian() * var15);

			if( itemStack.hasTagCompound() ) {
				item.func_92014_d().setTagCompound( (NBTTagCompound) itemStack.getTagCompound().copy() );
			}
		}
	}
}
