package heero.mc.mod.wakcraft.entity.npc;

import heero.mc.mod.wakcraft.quest.Quest;
import heero.mc.mod.wakcraft.quest.Task;
import heero.mc.mod.wakcraft.util.QuestUtil;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class EntityWNPC extends EntityLiving {
    public EntityWNPC(World world) {
        super(world);

        enablePersistence();

        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(20, new EntityAILookIdle(this));

        setCurrentItemOrArmor(0, new ItemStack(Items.fishing_rod));
    }

    /**
     * Can't be pushed by other entities
     */
    @Override
    public boolean canBePushed() {
        return false;
    }

    /**
     * Do not push other entities
     */
    @Override
    protected void collideWithNearbyEntities() {
    }

    @Override
    protected void damageEntity(DamageSource damageSrc, float damageAmount) {
        super.damageEntity(damageSrc, damageAmount);
    }

    @Override
    public boolean isEntityInvulnerable(DamageSource source) {
        return source != DamageSource.outOfWorld && !source.isCreativePlayer();
    }

    @Override
    protected boolean interact(EntityPlayer player) {
        if (player.getEntityWorld().isRemote) {
            return false;
        }

        Quest quest = QuestUtil.getCurrentQuest(player, this);
        if (quest == null) {
            quest = QuestUtil.getNewQuest(player, this);
            if (quest == null) {
                return false;
            }

            player.addChatMessage(new ChatComponentText("Start new Quest : " + quest.name));
        }

        final Task task = QuestUtil.getLastTask(player, quest);
        if (task == null) {
            return false;
        }

        for (final String dialog : task.getDialog()) {
            player.addChatMessage(new ChatComponentText(dialog));
        }

        if (task.action.equals("talk")) {
            QuestUtil.advance(player, quest);
        }

        return true;
    }

    @Override
    protected boolean isMovementBlocked() {
        return true;
    }
}
