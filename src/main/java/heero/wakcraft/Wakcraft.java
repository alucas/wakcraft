package heero.wakcraft;

import heero.wakcraft.entity.item.*;
import heero.wakcraft.entity.monster.Bouffette;
import heero.wakcraft.entity.monster.BouftonBlanc;
import heero.wakcraft.entity.monster.BouftonNoir;
import heero.wakcraft.entity.monster.Bouftou;
import heero.wakcraft.entity.monster.BouftouCG;
import heero.wakcraft.proxy.ServerProxy;
import heero.wakcraft.reference.References;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler; // used in 1.6.2
//import cpw.mods.fml.common.Mod.PreInit;    // used in 1.5.2
//import cpw.mods.fml.common.Mod.Init;       // used in 1.5.2
//import cpw.mods.fml.common.Mod.PostInit;   // used in 1.5.2
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = References.MODID, name = References.NAME, version = References.VERSION)
public class Wakcraft {
	// The instance of your mod that Forge uses.
	@Instance(value = References.MODID)
	public static Wakcraft instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide = References.CLIENT_PATH + ".ClientProxy", serverSide = References.SERVER_PATH
			+ ".ServerProxy")
	public static ServerProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		// Items
		GameRegistry.registerItem(new LaineDeBouftou(), "LaineDeBouftou");
		GameRegistry.registerItem(new CuirDeBouftou(), "CuirDeBouftou");
		GameRegistry.registerItem(new CorneDeBouftou(), "CorneDeBouftou");
		GameRegistry.registerItem(new ClefLaineuse(), "ClefLaineuse");
		
		// Mobs
		registerEntity(Bouftou.class, "Bouftou", 0xeaeaea, 0xc99a03);
		registerEntity(Bouffette.class, "Bouffette", 0xeaeaea, 0xc99ab3);
		registerEntity(BouftonBlanc.class, "BouftonBlanc", 0xeaeaea, 0xc29ab3);
		registerEntity(BouftonNoir.class, "BouftonNoir", 0xeaeaea, 0xc22ab3);
		registerEntity(BouftouCG.class, "BouftouChefDeGuerre", 0xeaeaea, 0xc22a23);
		
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.registerRenderers();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		// Stub Method
	}

	public void registerEntity(Class<? extends Entity> entityClass,
			String entityName, int bkEggColor, int fgEggColor) {
		int id = EntityRegistry.findGlobalUniqueEntityId();

		EntityRegistry.registerGlobalEntityID(entityClass, entityName, id);
		EntityList.entityEggs.put(Integer.valueOf(id), new EntityEggInfo(id,
				bkEggColor, fgEggColor));
	}

	public void addSpawn(Class<? extends EntityLiving> entityClass,
			int spawnProb, int min, int max, BiomeGenBase[] biomes) {
		if (spawnProb > 0) {
			EntityRegistry.addSpawn(entityClass, spawnProb, min, max,
					EnumCreatureType.creature, biomes);
		}
	}
}