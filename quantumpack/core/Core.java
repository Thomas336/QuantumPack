package quantumpack.core;

import java.util.Random;

import quantumpack.*;
import quantumpack.Blocks.BlockCyanide;
import quantumpack.Blocks.BlockRs;
import quantumpack.Blocks.BlockTower;
import quantumpack.Blocks.CTNT;
import quantumpack.Blocks.CopperOre;
import quantumpack.Blocks.CyanideOre;
import quantumpack.Blocks.DyeGlass;
import quantumpack.Blocks.QuartzOre;
import quantumpack.Blocks.ReSteel;
import quantumpack.Blocks.RubyOre;
import quantumpack.Blocks.SaphireOre;
import quantumpack.Blocks.SulphurOre;
import quantumpack.Blocks.TinOre;
import quantumpack.Blocks.UnDiamondOre;
import quantumpack.Pulveriser.BlockPulv;
import quantumpack.Pulveriser.TilePulv;
import quantumpack.net.*;

import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemReed;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;



@Mod(modid = "quantpakftw", name = "Quantum Pack", version = "beta-0.0.1")	
@NetworkMod(clientSideRequired = true, serverSideRequired = true,
	channels = {"qp_channel"}, packetHandler = PacketHandler.class)
public class Core
{
	@Mod.Instance("quantpakftw")
	public static Core instance;

	
	@SidedProxy(clientSide = "quantumpack.net.ClientProxy", serverSide = "quantumpack.net.CommonProxy")
	public static CommonProxy proxy;
	
    public static final StepSound soundPowderFootstep = new StepSound("stone", 1.0F, 1.0F);
    public static final StepSound soundWoodFootstep = new StepSound("wood", 1.0F, 1.0F);
    public static final StepSound soundGravelFootstep = new StepSound("gravel", 1.0F, 1.0F);
    public static final StepSound soundGrassFootstep = new StepSound("grass", 1.0F, 1.0F);
    public static final StepSound soundStoneFootstep = new StepSound("stone", 1.0F, 1.0F);
    public static final StepSound soundMetalFootstep = new StepSound("stone", 1.0F, 1.5F);
    public static final StepSound soundGlassFootstep = new StepSound("stone", 1.0F, 1.0F);
    public static final StepSound soundClothFootstep = new StepSound("cloth", 1.0F, 1.0F);
    public static final StepSound soundSandFootstep = new StepSound("sand", 1.0F, 1.0F);
    public static final StepSound soundSnowFootstep = new StepSound("snow", 1.0F, 1.0F);
    public static final StepSound soundLadderFootstep = new StepSound("ladder", 1.0F, 1.0F);
    public static final StepSound soundAnvilFootstep = new StepSound("anvil", 0.3F, 1.0F);

	public static final String TEXTURE_MACHINES = "/gui/machines.png";
	public static final String TEXTURE_BLOCKS = "/gui/blocks.png";
	public static final String TEXTURE_ITEMS = "/gui/items.png";
	
	public static CreativeTabQuantum qpTab;

	//Items
	//Item <NAME>;
	public static Item ruby;
	public static Item saphire;
	public static Item uncutDiamond;
	public static Item sulphurPowder;
	public static Item cyanidePowder;
	public static Item ingotCopper;
	public static Item ingotTin;
	public static Item ingotBronze; //"'CCC' 'CTC' 'XXX'" makes 3
	public static Item ingotSteel; //smelt usSteel
	public static Item usSteel; //"'IXX' 'XCX' 'XXX'" makes 1
	public static Item sSugarcane; //idk man...

	//Blocks
	//Block <NAME>;
	public static Block oreRuby;
	public static Block oreSaphire;
	public static Block oreUncutDiamond;
	public static Block oreSulphur;
	public static Block oreCyanide; //5-6 per vein
	public static Block oreCopper;
	public static Block oreTin;
	public static Block reSteel; //"'SSS' 'SSS' 'SSS'"
	public static Block dyeGlass; //"'[DYE]WX' 'XXX' 'XXX'"
	public static Block oreQuartz; //chiseled, pillar, stairs, slab
	public static Block cTNT; //"'TTX' 'TTX' 'XXX'" 1.7x rad, 1.5x pow
	public static Block rsBlock;
	public static Block pulvOff;
	public static Block pulvOn;
	public static Block blockCyanide;
	public static Block blockTower;

	public void generateSurface(World w, Random r, int cx, int cz)
	{
		for (int i = 0; i < 10; i++)
		{
			int x = cx + r.nextInt(16);
			int y = r.nextInt(20);
			int z = cz + r.nextInt(16);
			System.out.println("Pos: " + x + ", " + y + ", " + z);

			//new WorldGenMinable(this.oreLight.blockID, 10).generate(w, r, x, y ,z);
		}
	}

	public String getVersion()
	{
		return "0.0";
	}
	
	@Mod.Init
	public void load(FMLInitializationEvent ignoredEvent)
	{
		//LOAD SHIT
		// Register GUIs
		NetworkRegistry.instance().registerGuiHandler(Core.instance, proxy);
		//TODO ModLoader.getMinecraftInstance().theWorld.newExplosion(null, 0, 0, 0, 10.0F, true, true);
		//TODO Potion
		//TODO Armor(Slime)
		//TODO Finish blocks and items
		//TODO Add crafting
		//TODO Recipies
		//TODO Texture binding
		//TODO Test like a mother fucker!!!!
		
		
		
		
		//SHIT
		//Items
		//6saq			ERxcv																<NAME> = (new [TYPE](ID, [arg 2], [arg 3]).setName("<INTERNALNAME>").[OTHER SHIT];
		ruby = (new Item(150).setItemName("ruby").setCreativeTab(CreativeTabs.tabFood));
		saphire = (new Item(151).setItemName("saphire").setCreativeTab(CreativeTabs.tabFood));
		uncutDiamond = (new Item(152).setItemName("ucDiamond").setCreativeTab(CreativeTabs.tabFood));
		sulphurPowder = (new Item(153).setItemName("sulphurPowder").setCreativeTab(CreativeTabs.tabFood));
		cyanidePowder = (new Item(154).setItemName("cyanidePowder").setCreativeTab(CreativeTabs.tabFood));
		ingotCopper = (new Item(155).setItemName("ingotCopper").setCreativeTab(CreativeTabs.tabFood));
		ingotTin = (new Item(156).setItemName("ingotTin").setCreativeTab(CreativeTabs.tabFood));
		ingotBronze = (new Item(157).setItemName("ingotBronze").setCreativeTab(CreativeTabs.tabFood));
		ingotSteel = (new Item(158).setItemName("ingotSteel").setCreativeTab(CreativeTabs.tabFood));
		usSteel = (new Item(159).setItemName("usSteel").setCreativeTab(CreativeTabs.tabFood));

		//Blocks
		//<NAME> = (new [CLASSNAME](ID, [TEX], [MAT])).setHardness([DIGTIME]).setResistance([EXPLOSION]).setStepSound([SOUND]).setBlockName(<INTERNALNAME>);
		oreRuby = (new RubyOre(1150)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setBlockName("oreRuby");
		oreSaphire = (new SaphireOre(1151)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setBlockName("oreSaphire");
		oreUncutDiamond = (new UnDiamondOre(1152)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setBlockName("oreUncutDiamond");
		oreSulphur = (new SulphurOre(1153)).setHardness(5.0F).setResistance(2.0F).setStepSound(soundStoneFootstep).setBlockName("oreSulphur");
		oreCyanide = (new CyanideOre(1154)).setHardness(5.0F).setResistance(2.0F).setStepSound(soundStoneFootstep).setBlockName("oreCyanide");
		oreCopper = (new CopperOre(1155)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setBlockName("oreCopper");
		oreTin = (new TinOre(1156)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setBlockName("oreTin");
		reSteel = (new ReSteel(1157)).setHardness(5.0F).setResistance(50.0F).setStepSound(soundStoneFootstep).setBlockName("reSteel");
		dyeGlass = (new DyeGlass(1158)).setHardness(5.0F).setResistance(0.0F).setStepSound(soundStoneFootstep).setBlockName("dyeGlass");
		oreQuartz = (new QuartzOre(1159)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setBlockName("oreQuartz");
		cTNT = (new CTNT(1160)).setHardness(5.0F).setResistance(0.0F).setStepSound(soundStoneFootstep).setBlockName("cTNT");
		rsBlock = (new BlockRs(1161)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setBlockName("reBlock");
		pulvOff = (new BlockPulv(1162, false)).setHardness(5.0F).setResistance(30.0F).setStepSound(soundMetalFootstep).setBlockName("pulv");
		pulvOn = (new BlockPulv(1163, true)).setHardness(5.0F).setResistance(30.0F).setStepSound(soundMetalFootstep).setBlockName("pulv");
		blockCyanide = (new BlockCyanide(1164)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setBlockName("blockCyanide");
		blockTower = (new BlockTower(1165)).setHardness(1.0F).setResistance(5.0F).setStepSound(soundWoodFootstep).setBlockName("blockTower").setCreativeTab(CreativeTabs.tabFood);
		
		qpTab = new CreativeTabQuantum();

		//Names
		//ModLoader.addName(<NAME>, "NAME");
		LanguageRegistry.addName(ruby, "Ruby");
		LanguageRegistry.addName(saphire, "Saphire");
		LanguageRegistry.addName(uncutDiamond, "Un-Cut Diamond");
		LanguageRegistry.addName(sulphurPowder, "Sulphur");
		LanguageRegistry.addName(cyanidePowder, "Cyanide Powder");
		LanguageRegistry.addName(ingotCopper, "Copper Ingot");
		LanguageRegistry.addName(ingotTin, "Tin Ingot");
		LanguageRegistry.addName(ingotBronze, "Bronze Ingot");
		LanguageRegistry.addName(ingotSteel, "Steel Ingot");
		LanguageRegistry.addName(usSteel, "Raw Steel");

		LanguageRegistry.addName(oreRuby, "Ruby Ore");
		LanguageRegistry.addName(oreSaphire, "Saphire Ore");
		LanguageRegistry.addName(oreUncutDiamond, "Uncut Diamond");
		LanguageRegistry.addName(oreSulphur, "Sulphur Ore");
		LanguageRegistry.addName(oreCyanide, "Cyanide Ore");
		LanguageRegistry.addName(oreCopper, "Copper Ore");
		LanguageRegistry.addName(oreTin, "Tin Ore");
		LanguageRegistry.addName(reSteel, "Reinforced Steel");
		LanguageRegistry.addName(dyeGlass, "Dyed Glass");
		LanguageRegistry.addName(oreQuartz, "Quartz Ore");
		LanguageRegistry.addName(cTNT, "Concentrated TNT");
		LanguageRegistry.addName(rsBlock, "Redstone Block");
		LanguageRegistry.addName(pulvOff, "Pulv Off");
		LanguageRegistry.addName(pulvOn, "Pulv On");
		LanguageRegistry.addName(blockTower, "blockTower");

		//Registers
		//MoGameRegistryegisterBlock(<NAME>);
		GameRegistry.registerBlock(oreRuby, "oreRuby");
		GameRegistry.registerBlock(oreSaphire, "oreSaphire");
		GameRegistry.registerBlock(oreUncutDiamond, "oreUncutDiamond");
		GameRegistry.registerBlock(oreSulphur, "oreSulphur");
		GameRegistry.registerBlock(oreCyanide, "oreCyanide");
		GameRegistry.registerBlock(oreCopper, "oreCopper");
		GameRegistry.registerBlock(oreTin, "oreTin");
		GameRegistry.registerBlock(reSteel, "reSteel");
		GameRegistry.registerBlock(dyeGlass, "dyeGlass");
		GameRegistry.registerBlock(oreQuartz, "oreQuartz");
		GameRegistry.registerBlock(cTNT, "cTNT");
		GameRegistry.registerBlock(rsBlock, "rsBlock");
		GameRegistry.registerBlock(pulvOff, "pulvOff");
		GameRegistry.registerBlock(pulvOn, "pulvOn");
		GameRegistry.registerBlock(blockCyanide, "blockCyanide");
		GameRegistry.registerBlock(blockTower, "blockTower");
		
		GameRegistry.registerTileEntity(TilePulv.class, "tile.qp.pulv");

		//Recipes
		//ModLoader.addRecipe(new ItemSatck(<NAME>, [AMT]), new Object[] {"TOP", "MID", "BOT", 'a', Item.diamond, 'b', Block.blockDiamond, 'c', <NAME>});
		GameRegistry.addRecipe(new ItemStack(ingotBronze, 2), new Object[] {"CTC", "CCC", 'C', ingotCopper, 'T', ingotTin});
		GameRegistry.addRecipe(new ItemStack(Block.sandStone, 1), new Object[] {"SSS", "SSS", "SSS", 'S', Block.sand});

		//Textures
		
	}

}
