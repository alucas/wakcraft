package heero.mc.mod.wakcraft.helper;

import heero.mc.mod.wakcraft.ability.AbilityManager.ABILITY;
import heero.mc.mod.wakcraft.entity.creature.IEntityWithAbility;
import heero.mc.mod.wakcraft.entity.property.AbilitiesProperty;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class AbilityHelper {
	public static boolean haveAbilities(Entity entity) {
		return (entity instanceof EntityPlayer || entity instanceof IEntityWithAbility);
	}

	public static int getAbility(Entity entity, ABILITY ability) {
		return ((AbilitiesProperty) entity.getExtendedProperties(AbilitiesProperty.IDENTIFIER)).get(ability);
	}

	public static void init(Entity entity) {
		AbilitiesProperty property = (AbilitiesProperty) entity.getExtendedProperties(AbilitiesProperty.IDENTIFIER);

		if (entity instanceof IEntityWithAbility) {
			((IEntityWithAbility) entity).initAbility(property);
		} else if (entity instanceof EntityPlayer) {
			property.enablePersistence(true);

			property.set(ABILITY.ACTION, 6);
			property.set(ABILITY.MOVEMENT, 3);
			property.set(ABILITY.HEALTH, 49);
			property.set(ABILITY.CRITICAL, 3);

			property.set(ABILITY.WAKFU, 6);
			property.set(ABILITY.CONTROL, 1);
		}
	}
}
