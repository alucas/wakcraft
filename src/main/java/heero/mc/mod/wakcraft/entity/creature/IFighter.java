package heero.mc.mod.wakcraft.entity.creature;

import java.util.Set;
import java.util.UUID;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IFighter extends IEntityWithCharacteristics {
	public void setGroup(Set<UUID> group);
	public Set<UUID> getGroup();

	public void onAttacked(EntityLivingBase attacker, ItemStack stack);
}
