package heero.mc.mod.wakcraft.world;

import heero.mc.mod.wakcraft.WConfig;
import heero.mc.mod.wakcraft.entity.property.HavenBagProperty;
import heero.mc.mod.wakcraft.havenbag.HavenBagHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterHavenBag extends Teleporter {
	protected int uid;
	protected HavenBagProperty properties;

	public TeleporterHavenBag(WorldServer par1WorldServer, int uid) {
		super(par1WorldServer);

		this.uid = uid;
		this.properties = null;
	}

	public TeleporterHavenBag(WorldServer par1WorldServer, HavenBagProperty properties) {
		super(par1WorldServer);

		this.uid = -1;
		this.properties = properties;
	}

	/**
	 * Place an entity in a nearby portal, creating one if necessary.
	 */
	public void placeInPortal(Entity entity, double oldPosX, double oldPosY, double oldPosZ, float oldYaw) {
		if (entity.dimension == WConfig.HAVENBAG_DIMENSION_ID && uid != -1) {
			int[] coords = HavenBagHelper.getCoordFromUID(uid);

			entity.motionX = entity.motionY = entity.motionZ = 0;
			entity.rotationYaw = -90;
			entity.rotationPitch = 0;
		
			if (entity instanceof EntityLivingBase) {
				((EntityLivingBase) entity).setPositionAndUpdate(coords[0] + 0.5, coords[1], coords[2] + 7.5);
			}
		} else if (entity.dimension == 0 && properties != null && entity instanceof EntityLivingBase) {
			double[] oldCoords = properties.getOldCoords();
			((EntityLivingBase) entity).setPositionAndUpdate(oldCoords[0], oldCoords[1], oldCoords[2]);
		}
	}

	/**
	 * called periodically to remove out-of-date portal locations from the cache
	 * list. Argument par1 is a WorldServer.getTotalWorldTime() value.
	 */
	public void removeStalePortalLocations(long par1) {
		// no portal
	}
}