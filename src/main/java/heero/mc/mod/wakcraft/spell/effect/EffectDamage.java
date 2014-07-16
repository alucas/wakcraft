package heero.mc.mod.wakcraft.spell.effect;


public class EffectDamage implements IEffectDamage {
	private final int damageBase;
	private final int damageFactor;
	private final IEffectArea effectArea;

	public EffectDamage(final int damageBase, final int damageFactor, IEffectArea effectArea) {
		this.damageBase = damageBase;
		this.damageFactor = damageFactor;
		this.effectArea = effectArea;
	}

	public EffectDamage(final int damageBase, final int damageFactor) {
		this(damageBase, damageFactor, EffectArea.POINT);
	}

	@Override
	public int getDamageBase() {
		return damageBase;
	}

	@Override
	public int getDamageFactor() {
		return damageFactor;
	}

	@Override
	public IEffectArea getZone() {
		return effectArea;
	}
}
