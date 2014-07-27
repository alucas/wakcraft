package heero.mc.mod.wakcraft.fight;

import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.spell.ISpell;
import heero.mc.mod.wakcraft.spell.effect.EffectElement;
import heero.mc.mod.wakcraft.spell.effect.IEffectDamage;
import heero.mc.mod.wakcraft.spell.effect.IEffectElement;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class DamageUtil {
	private DamageUtil() {
	}

	public static int computeDamage(final EntityLivingBase attacker, final EntityLivingBase target, final ItemStack stackSpell, final IEffectDamage effect) {
		if (!(stackSpell.getItem() instanceof ISpell)) {
			WLog.warning("The stackSpell parameter is not a spell stack");
			return 0;
		}

		int spellLevel = ((ISpell) stackSpell.getItem()).getLevel(stackSpell.getItemDamage());
		int damageValue = effect.getValue(spellLevel);
		IEffectElement damageElement = effect.getElement();

		if (damageElement == EffectElement.EARTH) {
			
		}

		return damageValue;
	}
}
