package quantumpack.Blocks;

import quantumpack.core.Core;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class SulphurOre extends Block
{

	public SulphurOre(int par1)
	{
		super(par1, 8, Material.rock);
        this.setCreativeTab(Core.qpTab);
	}
	
	public String getTextureFile () {
        return Core.TEXTURE_BLOCKS;
	}

}
