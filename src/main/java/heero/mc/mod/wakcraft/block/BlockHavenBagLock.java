package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import heero.mc.mod.wakcraft.entity.property.HavenBagProperty;
import heero.mc.mod.wakcraft.havenbag.HavenBagProperties;
import heero.mc.mod.wakcraft.havenbag.HavenBagsManager;
import heero.mc.mod.wakcraft.util.HavenBagUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class BlockHavenBagLock extends BlockGeneric {
    public BlockHavenBagLock() {
        super(Material.wood, WCreativeTabs.tabSpecialBlock);

        setCanBePlacedManually(false);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        }

        IExtendedEntityProperties properties = player.getExtendedProperties(HavenBagProperty.IDENTIFIER);
        if (properties == null || !(properties instanceof HavenBagProperty)) {
            WLog.warning("Error while loading the player (%s) extended properties", player.getDisplayName());
            return true;
        }

        HavenBagProperty propertiesHB = (HavenBagProperty) properties;
        if (propertiesHB.getPlayerHavenBagId() != HavenBagUtil.getUIDFromCoord(pos)) {
            player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.notYourBag")));
            return true;
        }

        HavenBagProperties havenBagProperties = HavenBagsManager.getProperties(propertiesHB.getPlayerHavenBagId());
        if (havenBagProperties == null) {
            return true;
        }

        havenBagProperties.setLock(!havenBagProperties.isLocked());

        HavenBagsManager.setProperties(propertiesHB.getPlayerHavenBagId(), havenBagProperties);

        player.addChatMessage(new ChatComponentText(havenBagProperties.isLocked() ? StatCollector.translateToLocal("message.lockHavenBag") : StatCollector.translateToLocal("message.unlockHavenBag")));

        return true;
    }
}