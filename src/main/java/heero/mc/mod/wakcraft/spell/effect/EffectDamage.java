package heero.mc.mod.wakcraft.spell.effect;

public class EffectDamage implements IEffectDamage {
	private final int damageBase;
	private final int damageFactor;

	public EffectDamage(final int damageBase, final int damageFactor) {
		this.damageBase = damageBase;
		this.damageFactor = damageFactor;
	}

	@Override
	public int getDamageBase() {
		return damageBase;
	}

	@Override
	public int getDamageFactor() {
		return damageFactor;
	}
}
