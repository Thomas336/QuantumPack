package quantumpack.Blocks;

import quantumpack.core.Core;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class RubyOre extends Block
{

	public RubyOre(int par1)
	{
		super(par1, 6, Material.iron);
        this.setCreativeTab(Core.qpTab);
	}
	
	public String getTextureFile () {
        return Core.TEXTURE_BLOCKS;
	}

}
