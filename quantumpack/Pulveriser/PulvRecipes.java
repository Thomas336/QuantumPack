package quantumpack.Pulveriser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import quantumpack.core.Core;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PulvRecipes
{
    private static final PulvRecipes crushingBase = new PulvRecipes();

    /** The list of crushing results. */
    private Map crushingList = new HashMap();
    private Map experienceList = new HashMap();
    private HashMap<List<Integer>, ItemStack> metaCrushingList = new HashMap<List<Integer>, ItemStack>();
    private HashMap<List<Integer>, Float> metaExperience = new HashMap<List<Integer>, Float>();

    /**
     * Used to call methods addCrushing and getCrushingResult.
     */
    public static final PulvRecipes crushing()
    {
        return crushingBase;
    }

    private PulvRecipes()
    {
        this.addCrushing(Core.oreCyanide.blockID, new ItemStack(Core.cyanidePowder));
    }

    /**
     * Adds a crushing recipe.
     */
    public void addCrushing(int par1, ItemStack par2ItemStack)
    {
        this.crushingList.put(Integer.valueOf(par1), par2ItemStack);
        this.experienceList.put(Integer.valueOf(par2ItemStack.itemID), 0.0F);
    }

    /**
     * Returns the crushing result of an item.
     * Deprecated in favor of a metadata sensitive version
     */
    @Deprecated
    public ItemStack getCrushingResult(int par1)
    {
        return (ItemStack)this.crushingList.get(Integer.valueOf(par1));
    }

    public Map getCrushingList()
    {
        return this.crushingList;
    }

    @Deprecated //In favor of ItemStack sensitive version
    public float getExperience(int par1)
    {
        return 0.0F;
    }

    /**
     * A metadata sensitive version of adding a pulv recipe.
     */
    public void addCrushing(int itemID, int metadata, ItemStack itemstack)
    {
        metaCrushingList.put(Arrays.asList(itemID, metadata), itemstack);
        metaExperience.put(Arrays.asList(itemID, metadata), 0.0F);
    }

    /**
     * Used to get the resulting ItemStack form a source ItemStack
     * @param item The Source ItemStack
     * @return The result ItemStack
     */
    public ItemStack getCrushingResult(ItemStack item) 
    {
        if (item == null)
        {
            return null;
        }
        ItemStack ret = (ItemStack)metaCrushingList.get(Arrays.asList(item.itemID, item.getItemDamage()));
        if (ret != null) 
        {
            return ret;
        }
        return (ItemStack)crushingList.get(Integer.valueOf(item.itemID));
    }

    /**
     * Grabs the amount of base experience for this item to give when pulled from the pulv slot.
     */
    public float getExperience(ItemStack item)
    {
        return 0.0F;
    }

    public Map<List<Integer>, ItemStack> getMetaCrushingList()
    {
        return metaCrushingList;
    }
}
