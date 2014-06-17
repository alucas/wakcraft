package heero.mc.mod.wakcraft.helper;

import heero.mc.mod.wakcraft.ability.AbilityManager.ABILITY;
import heero.mc.mod.wakcraft.entity.property.AbilitiesProperty;
import net.minecraft.entity.EntityLivingBase;

public class AbilityHelper {
	public static int getAbility(EntityLivingBase entity, ABILITY ability) {
		return ((AbilitiesProperty) entity.getExtendedProperties(AbilitiesProperty.IDENTIFIER)).get(ability);
	}
}
