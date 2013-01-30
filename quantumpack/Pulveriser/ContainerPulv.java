package quantumpack.Pulveriser;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPulv extends Container
{
    private TilePulv pulv;
    private int lastCrushTime = 0;
    private int lastFuel = 0;
    private int lastItemCrushTime = 0;

    public ContainerPulv(InventoryPlayer par1InventoryPlayer, TilePulv par2TilePulv)
    {
        this.pulv = par2TilePulv;
        this.addSlotToContainer(new Slot(par2TilePulv, 0, 56, 17));
        this.addSlotToContainer(new Slot(par2TilePulv, 1, 56, 53));
        this.addSlotToContainer(new SlotPulv(par1InventoryPlayer.player, par2TilePulv, 2, 116, 35));
        int var3;

        for (var3 = 0; var3 < 3; ++var3)
        {
            for (int var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 142));
        }
    }

    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.pulv.pulvCrushTime);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.pulv.pulvFuel);
        par1ICrafting.sendProgressBarUpdate(this, 2, this.pulv.currentItemCrushTime);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int var1 = 0; var1 < this.crafters.size(); ++var1)
        {
            ICrafting var2 = (ICrafting)this.crafters.get(var1);

            if (this.lastCrushTime != this.pulv.pulvCrushTime)
            {
                var2.sendProgressBarUpdate(this, 0, this.pulv.pulvCrushTime);
            }

            if (this.lastFuel != this.pulv.pulvFuel)
            {
                var2.sendProgressBarUpdate(this, 1, this.pulv.pulvFuel);
            }

            if (this.lastItemCrushTime != this.pulv.currentItemCrushTime)
            {
                var2.sendProgressBarUpdate(this, 2, this.pulv.currentItemCrushTime);
            }
        }

        this.lastCrushTime = this.pulv.pulvCrushTime;
        this.lastFuel = this.pulv.pulvFuel;
        this.lastItemCrushTime = this.pulv.currentItemCrushTime;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
            this.pulv.pulvCrushTime = par2;
        }

        if (par1 == 1)
        {
            this.pulv.pulvFuel = par2;
        }

        if (par1 == 2)
        {
            this.pulv.currentItemCrushTime = par2;
        }
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.pulv.isUseableByPlayer(par1EntityPlayer);
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(par2);

        if (var4 != null && var4.getHasStack())
        {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();

            if (par2 == 2)
            {
                if (!this.mergeItemStack(var5, 3, 39, true))
                {
                    return null;
                }

                var4.onSlotChange(var5, var3);
            }
            else if (par2 != 1 && par2 != 0)
            {
                if (PulvRecipes.crushing().getCrushingResult(var5) != null)
                {
                    if (!this.mergeItemStack(var5, 0, 1, false))
                    {
                        return null;
                    }
                }
                else if (TilePulv.isItemFuel(var5))
                {
                    if (!this.mergeItemStack(var5, 1, 2, false))
                    {
                        return null;
                    }
                }
                else if (par2 >= 3 && par2 < 30)
                {
                    if (!this.mergeItemStack(var5, 30, 39, false))
                    {
                        return null;
                    }
                }
                else if (par2 >= 30 && par2 < 39 && !this.mergeItemStack(var5, 3, 30, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var5, 3, 39, false))
            {
                return null;
            }

            if (var5.stackSize == 0)
            {
                var4.putStack((ItemStack)null);
            }
            else
            {
                var4.onSlotChanged();
            }

            if (var5.stackSize == var3.stackSize)
            {
                return null;
            }

            var4.onPickupFromSlot(par1EntityPlayer, var5);
        }

        return var3;
    }
}
