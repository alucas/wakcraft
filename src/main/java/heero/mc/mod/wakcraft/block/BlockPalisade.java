package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.client.renderer.block.RendererBlockPalisade;
import heero.mc.mod.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.mc.mod.wakcraft.util.RotationUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockPalisade extends BlockGeneric {
	protected static final String[] names = new String[] { "palisade1", "palisade2" };
    protected static final PropertyInteger PROP_MODEL = PropertyInteger.create("propertyModel", 0, names.length);

	public BlockPalisade() {
		super(Material.wood);

		setCreativeTab(WakcraftCreativeTabs.tabBlock);
		setUnlocalizedName(Reference.MODID + "_Palisade");
        setDefaultState(blockState.getBaseState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.SOUTH).withProperty(PROP_MODEL, 0));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < names.length; i++) {
			list.add(new ItemStack(item, 1, i << 2));
		}
	}

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumFacing yRotation = (EnumFacing) state.getValue(RotationUtil.PROP_Y_ROTATION);
        int model = (int) state.getValue(PROP_MODEL);

        return RotationUtil.getMetadataFromYRotation(yRotation) + ((model << 2) & 0b1100);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing yRotation = RotationUtil.getYRotationFromMetadata(meta);
        int model = (meta & 0b1100) >> 2;

        return getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, yRotation).withProperty(PROP_MODEL, model);
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, RotationUtil.PROP_Y_ROTATION, PROP_MODEL);
    }

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerBlockIcons(IIconRegister registerer) {
//		for (int i = 0; i < names.length; i++) {
//			icons[i] = registerer.registerIcon(Reference.MODID.toLowerCase()
//					+ ":" + names[i]);
//		}
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public IIcon getIcon(int side, int metadata) {
//		int index = (metadata >> 2) % names.length;
//
//		return icons[index];
//	}

//	/**
//	 * The type of render function that is called for this block
//	 */
//	@Override
//	@SideOnly(Side.CLIENT)
//	public int getRenderType() {
//		return RendererBlockPalisade.renderId;
//	}

	/**
	 * Determines the damage on the item the block drops. Used in cloth and
	 * wood.
	 */
	@Override
	public int damageDropped(IBlockState state) {
		return (int) state.getValue(PROP_MODEL);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos) {
		super.setBlockBoundsBasedOnState(world, pos);

        IBlockState state = world.getBlockState(pos);
        IBlockState stateE = world.getBlockState(pos.offsetEast());
        IBlockState stateW = world.getBlockState(pos.offsetWest());
        IBlockState stateN = world.getBlockState(pos.offsetNorth());
        IBlockState stateS = world.getBlockState(pos.offsetSouth());

		int m = (int) state.getValue(PROP_MODEL);
		int mE = (int) stateE.getValue(PROP_MODEL);
        int mW = (int) stateW.getValue(PROP_MODEL);
        int mN = (int) stateN.getValue(PROP_MODEL);
        int mS = (int) stateS.getValue(PROP_MODEL);

		Block block = state.getBlock();
		boolean tE = stateE.getBlock() == block;
		boolean tW = stateW.getBlock() == block;
		boolean tN = stateN.getBlock() == block;
		boolean tS = stateS.getBlock() == block;

		if ((tN && m == 1 && mN == 0) || (tE && m == 2 && mE == 0)
				|| (tS && m == 3 && mS == 1) || (tW && m == 0 && mW == 1)
				|| (tN && m == 1 && mN == 3) || (tE && m == 2 && mE == 3)
				|| (tS && m == 3 && mS == 2) || (tW && m == 0 && mW == 2)) {
			block.setBlockBounds(0, 0, 0, 1, 1, 1);
		} else {
			switch (m) {
			case 0:
				block.setBlockBounds(0, 0, 0, 1, 1, 0.1875f);
				break;
			case 1:
				block.setBlockBounds(0.8125f, 0, 0, 1, 1, 1);
				break;
			case 2:
				block.setBlockBounds(0, 0, 0, 0.1875f, 1, 1);
				break;
			default:
				block.setBlockBounds(0, 0, 0.8125f, 1, 1, 1);
			}
		}
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(world, pos, state, placer, stack);

		RotationUtil.setYRotationFromYaw(world, pos, state, placer.rotationYaw);
	}

	/**
	 * Adds all intersecting collision boxes to a list. (Be sure to only add
	 * boxes to the list if they intersect the mask.) Parameters: World, X, Y,
	 * Z, mask, list, colliding entity
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity entity) {
		setBlockBoundsBasedOnState(world, pos);

		super.addCollisionBoxesToList(world, pos, state, mask, list, entity);
	}
}
