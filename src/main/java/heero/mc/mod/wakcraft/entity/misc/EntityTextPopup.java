package heero.mc.mod.wakcraft.entity.misc;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityTextPopup extends Entity {
	public String text;
	public float colorRed, colorGreen, colorBlue;
	private int age;

	public EntityTextPopup(World world, String text) {
		super(world);

		this.text = text;
	}

	public EntityTextPopup(World world, String text, int x, int y, int z, float colorRed, float colorGreen, float colorBlue) {
		super(world);

		this.text = text;
		this.setSize(1, 1);
		this.setPosition(x, y, z);
		this.setColor(colorRed, colorGreen, colorBlue);
	}

	@Override
	protected void entityInit() {
		age = 0;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound var1) {
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound var1) {
	}

	/**
	 * Gets called every tick from main Entity class
	 */
	public void onEntityUpdate() {
		age++;

		if (age > 30) {
			setDead();
		} else {
			posY += 0.04;
		}
	}

	protected void setColor(float colorRed, float colorGreen, float colorBlue) {
		this.colorRed = colorRed;
		this.colorGreen = colorGreen;
		this.colorBlue = colorBlue;
	}
}
