package quantumpack.Pulveriser;

import quantumpack.core.Core;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public class TilePulv extends TileEntity implements IInventory, ISidedInventory
{
    /**
     * The ItemStacks that hold the items currently being used in the pulv
     */
    private ItemStack[] pulvItemStacks = new ItemStack[3];

    /** The number of ticks that the pulv will keep Fueling */
    public int pulvFuel = 0;

    /**
     * The number of ticks that a fresh copy of the currently-Fueling item would keep the pulv Fueling for
     */
    public int currentItemCrushTime = 0;

    /** The number of ticks that the current item has been crushing for */
    public int pulvCrushTime = 0;

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return this.pulvItemStacks.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1)
    {
        return this.pulvItemStacks[par1];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.pulvItemStacks[par1] != null)
        {
            ItemStack var3;

            if (this.pulvItemStacks[par1].stackSize <= par2)
            {
                var3 = this.pulvItemStacks[par1];
                this.pulvItemStacks[par1] = null;
                return var3;
            }
            else
            {
                var3 = this.pulvItemStacks[par1].splitStack(par2);

                if (this.pulvItemStacks[par1].stackSize == 0)
                {
                    this.pulvItemStacks[par1] = null;
                }

                return var3;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (this.pulvItemStacks[par1] != null)
        {
            ItemStack var2 = this.pulvItemStacks[par1];
            this.pulvItemStacks[par1] = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        this.pulvItemStacks[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return "container.pulv";
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
        this.pulvItemStacks = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            byte var5 = var4.getByte("Slot");

            if (var5 >= 0 && var5 < this.pulvItemStacks.length)
            {
                this.pulvItemStacks[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }

        this.pulvFuel = par1NBTTagCompound.getShort("Fuel");
        this.pulvCrushTime = par1NBTTagCompound.getShort("CrushTime");
        this.currentItemCrushTime = getItemFuel(this.pulvItemStacks[1]);
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("Fuel", (short)this.pulvFuel);
        par1NBTTagCompound.setShort("CrushTime", (short)this.pulvCrushTime);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.pulvItemStacks.length; ++var3)
        {
            if (this.pulvItemStacks[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.pulvItemStacks[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        par1NBTTagCompound.setTag("Items", var2);
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * Crushed
     */
    public int getCrushProgressScaled(int par1)
    {
        return this.pulvCrushTime * par1 / 200;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns an integer between 0 and the passed value representing how much Fuel time is left on the current fuel
     * item, where 0 means that the item is exhausted and the passed value means that the item is fresh
     */
    public int getFuelRemainingScaled(int par1)
    {
        if (this.currentItemCrushTime == 0)
        {
            this.currentItemCrushTime = 200;
        }

        return this.pulvFuel * par1 / this.currentItemCrushTime;
    }

    /**
     * Returns true if the pulv is currently Fueling
     */
    public boolean isCrushing()
    {
        return this.pulvFuel > 0;
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        boolean var1 = this.pulvFuel > 0;
        boolean var2 = false;

        if (this.pulvFuel > 0)
        {
            --this.pulvFuel;
        }

        if (!this.worldObj.isRemote)
        {
            if (this.pulvFuel == 0 && this.canCrush())
            {
                this.currentItemCrushTime = this.pulvFuel = getItemFuel(this.pulvItemStacks[1]);

                if (this.pulvFuel > 0)
                {
                    var2 = true;

                    if (this.pulvItemStacks[1] != null)
                    {
                        --this.pulvItemStacks[1].stackSize;

                        if (this.pulvItemStacks[1].stackSize == 0)
                        {
                            this.pulvItemStacks[1] = this.pulvItemStacks[1].getItem().getContainerItemStack(pulvItemStacks[1]);
                        }
                    }
                }
            }

            if (this.isCrushing() && this.canCrush())
            {
                ++this.pulvCrushTime;

                if (this.pulvCrushTime == 200)
                {
                    this.pulvCrushTime = 0;
                    this.crushItem();
                    var2 = true;
                }
            }
            else
            {
                this.pulvCrushTime = 0;
            }

            if (var1 != this.pulvFuel > 0)
            {
                var2 = true;
                BlockPulv.updatePulvBlockState(this.pulvFuel > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }

        if (var2)
        {
            this.onInventoryChanged();
        }
    }

    /**
     * Returns true if the pulv can crush an item, i.e. has a source item, destination stack isn't full, etc.
     */
    private boolean canCrush()
    {
        if (this.pulvItemStacks[0] == null)
        {
            return false;
        }
        else
        {
            ItemStack var1 = PulvRecipes.crushing().getCrushingResult(this.pulvItemStacks[0]);
            if (var1 == null) return false;
            if (this.pulvItemStacks[2] == null) return true;
            if (!this.pulvItemStacks[2].isItemEqual(var1)) return false;
            int result = pulvItemStacks[2].stackSize + var1.stackSize;
            return (result <= getInventoryStackLimit() && result <= var1.getMaxStackSize());
        }
    }

    /**
     * Turn one item from the pulv source stack into the appropriate Crushed item in the pulv result stack
     */
    public void crushItem()
    {
        if (this.canCrush())
        {
            ItemStack var1 = PulvRecipes.crushing().getCrushingResult(this.pulvItemStacks[0]);

            if (this.pulvItemStacks[2] == null)
            {
                this.pulvItemStacks[2] = var1.copy();
            }
            else if (this.pulvItemStacks[2].isItemEqual(var1))
            {
            	pulvItemStacks[2].stackSize += var1.stackSize;
            }

            --this.pulvItemStacks[0].stackSize;

            if (this.pulvItemStacks[0].stackSize <= 0)
            {
                this.pulvItemStacks[0] = null;
            }
        }
    }

    /**
     * Returns the number of ticks that the supplied fuel item will keep the pulv Fueling, or 0 if the item isn't
     * fuel
     */
    public static int getItemFuel(ItemStack par0ItemStack)//TODO
    {
        if (par0ItemStack == null)
        {
            return 0;
        }
        else
        {
            int var1 = par0ItemStack.getItem().itemID;
            Item var2 = par0ItemStack.getItem();

            if (var1 == Core.sulphurPowder.itemID) return 100;
            if (var1 == Core.cyanidePowder.itemID) return 1600;
            return 0;
        }
    }

    /**
     * Return true if item is a fuel source (getItemFuel() > 0).
     */
    public static boolean isItemFuel(ItemStack par0ItemStack)
    {
        return getItemFuel(par0ItemStack) > 0;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    public void openChest() {}

    public void closeChest() {}

    @Override
    public int getStartInventorySide(ForgeDirection side)
    {
        if (side == ForgeDirection.DOWN) return 1;
        if (side == ForgeDirection.UP) return 0;
        return 2;
    }

    @Override
    public int getSizeInventorySide(ForgeDirection side)
    {
        return 1;
    }
}
