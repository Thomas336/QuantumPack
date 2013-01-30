package quantumpack.Blocks;

import quantumpack.core.Core;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class SaphireOre extends Block
{

	public SaphireOre(int par1)
	{
		super(par1, 7, Material.iron);
        this.setCreativeTab(Core.qpTab);
	}
	
	public String getTextureFile () {
        return Core.TEXTURE_BLOCKS;
	}

}
