package heero.mc.mod.wakcraft.util;

import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.entity.creature.IFighter;
import heero.mc.mod.wakcraft.entity.property.FightCharacteristicsProperty;
import heero.mc.mod.wakcraft.entity.property.FightProperty;
import heero.mc.mod.wakcraft.entity.property.SpellsProperty;
import heero.mc.mod.wakcraft.fight.FightBlockCoordinates;
import heero.mc.mod.wakcraft.fight.FightInfo.FightStage;
import heero.mc.mod.wakcraft.fight.FightManager;
import heero.mc.mod.wakcraft.network.packet.PacketExtendedEntityProperty;
import heero.mc.mod.wakcraft.spell.IActiveSpell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class FightUtil {
    protected static FightProperty getFightProperty(final Entity entity) {
        final FightProperty properties = (FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER);
        if (properties == null) {
            WLog.warning("Error while loading the entity's fight properties (%s)", entity.getDisplayName());
            return null;
        }

        return properties;
    }

    protected static FightCharacteristicsProperty getFightCharacteristicsProperty(final Entity entity) {
        final FightCharacteristicsProperty properties = (FightCharacteristicsProperty) entity.getExtendedProperties(FightCharacteristicsProperty.IDENTIFIER);
        if (properties == null) {
            WLog.warning("Error while loading the entity's fight characteristics properties (%s)", entity.getDisplayName());
            return null;
        }

        return properties;
    }

    protected static SpellsProperty getSpellsProperty(final Entity entity) {
        final SpellsProperty properties = (SpellsProperty) entity.getExtendedProperties(SpellsProperty.IDENTIFIER);
        if (properties == null) {
            WLog.warning("Error while loading the entity's spell properties (%s)", entity.getDisplayName());
            return null;
        }

        return properties;
    }

    public static boolean isFighting(Entity entity) {
        return ((FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER)).getFightId() != -1;
    }

    public static int getFightId(Entity entity) {
        return ((FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER)).getFightId();
    }

    public static boolean isFighter(Entity entity) {
        return (entity instanceof IFighter && entity instanceof EntityLivingBase) || (entity instanceof EntityPlayer);
    }

    public static boolean isAutonomousFighter(Entity entity) {
        return !(entity instanceof EntityPlayer);
    }

    public static void setStartPosition(Entity entity, @Nullable BlockPos startPosition) {
        ((FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER)).setStartPosition(startPosition);
    }

    public static BlockPos getStartPosition(Entity entity) {
        return ((FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER)).getStartPosition();
    }

    public static int getTeam(Entity entity) {
        return ((FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER)).getTeam();
    }

    public static void setProperties(Entity entity, int fightId, int teamId) {
        ((FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER)).setTeam(teamId);
        ((FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER)).setFightId(fightId);
    }

    public static void resetProperties(Entity entity) {
        ((FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER)).resetProperties();
    }

    public static Integer getFightCharacteristic(final Entity entity, final Characteristic characteristic) {
        final FightCharacteristicsProperty properties = getFightCharacteristicsProperty(entity);
        if (properties == null) {
            return null;
        }

        return properties.get(characteristic);
    }

    public static void setFightCharacteristic(final Entity entity, final Characteristic characteristic, int value) {
        final FightCharacteristicsProperty properties = getFightCharacteristicsProperty(entity);
        if (properties == null) {
            return;
        }

        properties.set(characteristic, value);
    }

    public static void resetFightCharacteristic(final Entity entity, final Characteristic characteristic) {
        final Integer normalValue = CharacteristicUtil.getCharacteristic(entity, characteristic);
        if (normalValue == null) {
            return;
        }

        setFightCharacteristic(entity, characteristic, normalValue);
    }

    public static void sendFightCharacteristicToClient(final EntityPlayerMP player, final Entity fighter) {
        Wakcraft.packetPipeline.sendTo(new PacketExtendedEntityProperty(fighter, FightCharacteristicsProperty.IDENTIFIER), player);
    }

    public static void setCurrentPosition(Entity entity, @Nullable BlockPos startPosition) {
        ((FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER)).setCurrentPosition(startPosition);
    }

    public static BlockPos getCurrentPosition(Entity entity) {
        return ((FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER)).getCurrentPosition();
    }

    public static IInventory getSpellsInventory(Entity entity) {
        return getSpellsProperty(entity).getSpellsInventory();
    }

    public static ItemStack getCurrentSpell(final EntityPlayer entity) {
        return getSpellsInventory(entity).getStackInSlot(25 + entity.inventory.currentItem);
    }

    public static boolean isActiveSpell(ItemStack spellStack) {
        return spellStack != null && spellStack.getItem() instanceof IActiveSpell;
    }

    public static void updateDisplayName(final Entity entity) {
        if (!(entity instanceof EntityLiving)) {
            return;
        }

        final Integer fullHealth = CharacteristicUtil.getCharacteristic(entity, Characteristic.HEALTH);
        final Integer fightHealth = getFightCharacteristic(entity, Characteristic.HEALTH);
        if (fullHealth == null || fightHealth == null) {
            entity.setCustomNameTag("? / ?");
            return;
        }

        entity.setCustomNameTag(fightHealth + " / " + fullHealth);
    }

    public static void resetDisplayName(final Entity entity) {
        if (!(entity instanceof EntityLiving)) {
            return;
        }

        entity.setCustomNameTag("");
    }

    public static FightStage getFightStage(World world, int fightId) {
        return FightManager.INSTANCE.getFightStage(world, fightId);
    }

    public static List<List<EntityLivingBase>> getFighters(World world, int fightId) {
        return FightManager.INSTANCE.getFighters(world, fightId);
    }

    public static EntityLivingBase getCurrentFighter(World world, int fightId) {
        return FightManager.INSTANCE.getCurrentFighter(world, fightId);
    }

    public static List<List<FightBlockCoordinates>> getStartPositions(World world, int fightId) {
        return FightManager.INSTANCE.getStartPositions(world, fightId);
    }

    public static void selectPosition(EntityLivingBase entity, @Nullable BlockPos position) {
        FightManager.INSTANCE.selectPosition(entity, position);
    }
}
