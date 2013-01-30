package quantumpack.Blocks;

import quantumpack.core.Core;
import quantumpack.core.Util;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockTower extends Block {
	public BlockTower(int par1)
	{
		super(par1, 11, Material.wood);
		this.setCreativeTab(Core.qpTab);
	}

	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
            {
                    ItemStack inHand = par5EntityPlayer.inventory.getStackInSlot(0);

                    if (inHand == null)
                            return true;

                    if (inHand.getItem() instanceof ItemBlock)
                    {
                            int height = inHand.stackSize;

                            for (int i = 0; i < height; i++)
                            {
                                    int y = 1;
                                    while (par1World.getBlockId(par2, par3 + y, par4) != 0)
                                    {
                                            ++y;
                                    }
                                    if (!par1World.isRemote)
                                            par1World.setBlock(par2, par3 + y, par4, ((ItemBlock) inHand.getItem()).getBlockID());

                                    if (!par5EntityPlayer.capabilities.isCreativeMode)
                                    {
                                            par5EntityPlayer.inventory.getStackInSlot(0).stackSize--;
                                    }
                            }

                            return true;
                    }
                    return true;
            }
    }
	
	public void breakBlock(World world, int x, int y, int z, int par5, int par6)
	{
		for (int i = 0; i<257; x++)
		{
			y++;
			ItemStack stack = new ItemStack(world.getBlockId(x, y + i, z), 1, 1);
			System.out.println(world.getBlockId(x, y + i, z));
			//if (stack.itemID != this.blockID){return;}
			Util.dropItemAsEntity(world, x, y + i, z, stack);
		}
		super.breakBlock( world, x, y, z, par5, par6 );
	}
}
