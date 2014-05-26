package heero.mc.mod.wakcraft.entity.property;

import net.minecraft.nbt.NBTTagCompound;

public interface ISynchProperties {
	/**
	 * Returns the data to send to the clients
	 * 
	 * @return
	 */
	NBTTagCompound getClientPacket();

	/**
	 * Call when the client receive an entity properties packet
	 * 
	 * @param tagRoot
	 */
	void onClientPacket(NBTTagCompound tagRoot);
}
