package quantumpack.Blocks;

import quantumpack.core.Core;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class UnDiamondOre extends Block
{

	public UnDiamondOre(int par1)
	{
		super(par1, 10, Material.iron);
        this.setCreativeTab(Core.qpTab);
	}
	
	public String getTextureFile () {
        return Core.TEXTURE_BLOCKS;
	}

}
