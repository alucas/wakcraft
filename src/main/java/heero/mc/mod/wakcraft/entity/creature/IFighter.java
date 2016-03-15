package heero.mc.mod.wakcraft.entity.creature;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import java.util.Set;
import java.util.UUID;

public interface IFighter extends IEntityWithCharacteristics {
    public void setGroup(Set<UUID> group);

    public Set<UUID> getGroup();

    public void onAttacked(EntityLivingBase attacker, ItemStack stack);
}
