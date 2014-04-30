[1mdiff --git a/src/main/java/heero/wakcraft/block/BlockHavenBag.java b/src/main/java/heero/wakcraft/block/BlockHavenBag.java[m
[1mindex abfc248..bb41a9f 100644[m
[1m--- a/src/main/java/heero/wakcraft/block/BlockHavenBag.java[m
[1m+++ b/src/main/java/heero/wakcraft/block/BlockHavenBag.java[m
[36m@@ -34,20 +34,7 @@[m [mpublic class BlockHavenBag extends BlockContainer {[m
 					return true;[m
 				}[m
 [m
[31m-				HavenBagProperty properties = (HavenBagProperty) player.getExtendedProperties(HavenBagProperty.IDENTIFIER);[m
[31m-				if (properties == null) {[m
[31m-					System.err.println("Error while loading player properties");[m
[31m-					return true;[m
[31m-				}[m
[31m-[m
[31m-				properties.posX = player.posX;[m
[31m-				properties.posY = player.posY;[m
[31m-				properties.posZ = player.posZ;[m
[31m-[m
[31m-				int[] coords = HavenBagManager.getCoordFromUID(tileHavenBag.uid);[m
[31m-				player.rotationYaw = -90;[m
[31m-				player.rotationPitch = 0;[m
[31m-				player.setPositionAndUpdate(coords[0] + 0.5, coords[1], coords[2] + 0.5);[m
[32m+[m				[32mHavenBagManager.teleportPlayerToHavenBag(player, tileHavenBag.uid);[m
 			}[m
 		}[m
 [m
[1mdiff --git a/src/main/java/heero/wakcraft/entity/property/HavenBagProperty.java b/src/main/java/heero/wakcraft/entity/property/HavenBagProperty.java[m
[1mindex da20e80..6e5dadd 100644[m
[1m--- a/src/main/java/heero/wakcraft/entity/property/HavenBagProperty.java[m
[1m+++ b/src/main/java/heero/wakcraft/entity/property/HavenBagProperty.java[m
[36m@@ -10,17 +10,24 @@[m [mpublic class HavenBagProperty implements IExtendedEntityProperties {[m
 [m
 	private static final String TAG_HAVENBAG = "HavenBag";[m
 	private static final String TAG_UID = "UID";[m
[32m+[m	[32mprivate static final String TAG_IN_HAVENBAG = "InHavenBag";[m
 	private static final String TAG_POS_X = "PosX";[m
 	private static final String TAG_POS_Y = "PosY";[m
 	private static final String TAG_POS_Z = "PosZ";[m
 [m
 	public int uid;[m
[32m+[m	[32mpublic boolean inHavenBag;[m
 	public double posX;[m
 	public double posY;[m
 	public double posZ;[m
 [m
 	@Override[m
 	public void init(Entity entity, World world) {[m
[32m+[m		[32muid = 0;[m
[32m+[m		[32minHavenBag = false;[m
[32m+[m		[32mposX = 0;[m
[32m+[m		[32mposY = 0;[m
[32m+[m		[32mposZ = 0;[m
 	}[m
 [m
 	@Override[m
[36m@@ -28,6 +35,7 @@[m [mpublic class HavenBagProperty implements IExtendedEntityProperties {[m
 		NBTTagCompound tagHavenBag = new NBTTagCompound();[m
 [m
 		tagHavenBag.setInteger(TAG_UID, uid);[m
[32m+[m		[32mtagHavenBag.setBoolean(TAG_IN_HAVENBAG, inHavenBag);[m
 		tagHavenBag.setDouble(TAG_POS_X, posX);[m
 		tagHavenBag.setDouble(TAG_POS_Y, posY);[m
 		tagHavenBag.setDouble(TAG_POS_Z, posZ);[m
[36m@@ -40,6 +48,7 @@[m [mpublic class HavenBagProperty implements IExtendedEntityProperties {[m
 		NBTTagCompound tagHavenBag = tagRoot.getCompoundTag(TAG_HAVENBAG);[m
 [m
 		uid = tagHavenBag.getInteger(TAG_UID);[m
[32m+[m		[32minHavenBag = tagHavenBag.getBoolean(TAG_IN_HAVENBAG);[m
 		posX = tagHavenBag.getDouble(TAG_POS_X);[m
 		posY = tagHavenBag.getDouble(TAG_POS_Y);[m
 		posZ = tagHavenBag.getDouble(TAG_POS_Z);[m
[1mdiff --git a/src/main/java/heero/wakcraft/havenbag/HavenBagManager.java b/src/main/java/heero/wakcraft/havenbag/HavenBagManager.java[m
[1mindex 443b9ae..54a1513 100644[m
[1m--- a/src/main/java/heero/wakcraft/havenbag/HavenBagManager.java[m
[1m+++ b/src/main/java/heero/wakcraft/havenbag/HavenBagManager.java[m
[36m@@ -1,7 +1,9 @@[m
 package heero.wakcraft.havenbag;[m
 [m
 import heero.wakcraft.WakcraftBlocks;[m
[32m+[m[32mimport heero.wakcraft.entity.property.HavenBagProperty;[m
 import net.minecraft.block.Block;[m
[32m+[m[32mimport net.minecraft.entity.player.EntityPlayer;[m
 import net.minecraft.init.Blocks;[m
 import net.minecraft.world.World;[m
 [m
[36m@@ -69,4 +71,22 @@[m [mpublic class HavenBagManager {[m
 			}[m
 		}[m
 	}[m
[32m+[m[41m	[m
[32m+[m	[32mpublic static void teleportPlayerToHavenBag(EntityPlayer player, int havenBagUID) {[m
[32m+[m		[32mHavenBagProperty properties = (HavenBagProperty) player.getExtendedProperties(HavenBagProperty.IDENTIFIER);[m
[32m+[m		[32mif (properties == null) {[m
[32m+[m			[32mSystem.err.println("Error while loading player properties");[m
[32m+[m			[32mreturn;[m
[32m+[m		[32m}[m
[32m+[m
[32m+[m		[32mproperties.posX = player.posX;[m
[32m+[m		[32mproperties.posY = player.posY;[m
[32m+[m		[32mproperties.posZ = player.posZ;[m
[32m+[m		[32mproperties.inHavenBag = true;[m
[32m+[m
[32m+[m		[32mint[] coords = HavenBagManager.getCoordFromUID(havenBagUID);[m
[32m+[m		[32mplayer.rotationYaw = -90;[m
[32m+[m		[32mplayer.rotationPitch = 0;[m
[32m+[m		[32mplayer.setPositionAndUpdate(coords[0] + 0.5, coords[1], coords[2] + 0.5);[m
[32m+[m	[32m}[m
 }[m
[1mdiff --git a/src/main/java/heero/wakcraft/network/packet/HavenBagPacket.java b/src/main/java/heero/wakcraft/network/packet/HavenBagPacket.java[m
[1mindex 905e768..3d4da4e 100644[m
[1m--- a/src/main/java/heero/wakcraft/network/packet/HavenBagPacket.java[m
[1m+++ b/src/main/java/heero/wakcraft/network/packet/HavenBagPacket.java[m
[36m@@ -9,6 +9,7 @@[m [mimport io.netty.channel.ChannelHandlerContext;[m
 import net.minecraft.block.Block;[m
 import net.minecraft.entity.player.EntityPlayer;[m
 import net.minecraft.tileentity.TileEntity;[m
[32m+[m[32mimport net.minecraft.world.Teleporter;[m
 [m
 public class HavenBagPacket implements IPacket {[m
 	public HavenBagPacket() {[m
[36m@@ -68,7 +69,6 @@[m [mpublic class HavenBagPacket implements IPacket {[m
 		tileHavenBag.markDirty();[m
 		tileHavenBag.updateEntity();[m
 [m
[31m-		int[] coords = HavenBagManager.getCoordFromUID(properties.uid);[m
[31m-		player.setPositionAndUpdate(coords[0] + 0.5, coords[1], coords[2] + 0.5);[m
[32m+[m		[32mHavenBagManager.teleportPlayerToHavenBag(player, properties.uid);[m
 	}[m
 }[m
\ No newline at end of file[m
