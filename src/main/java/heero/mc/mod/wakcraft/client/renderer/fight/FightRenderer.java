package heero.mc.mod.wakcraft.client.renderer.fight;

import heero.mc.mod.wakcraft.WBlocks;
import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.fight.FightBlockCoordinates;
import heero.mc.mod.wakcraft.fight.FightHelper;
import heero.mc.mod.wakcraft.fight.FightInfo.FightStage;
import heero.mc.mod.wakcraft.spell.IActiveSpell;
import heero.mc.mod.wakcraft.spell.IRangeMode;
import heero.mc.mod.wakcraft.spell.RangeMode;
import heero.mc.mod.wakcraft.spell.effect.EffectArea;
import heero.mc.mod.wakcraft.spell.effect.IEffectArea;
import heero.mc.mod.wakcraft.util.FightUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class FightRenderer extends IRenderHandler {
    @Override
    @SideOnly(Side.CLIENT)
    public void render(float partialTicks, WorldClient world, Minecraft mc) {
        EntityPlayer player = mc.thePlayer;

        if (!FightUtil.isFighter(player)) {
            return;
        }

        if (!FightUtil.isFighting(player)) {
            return;
        }

        int fightId = FightUtil.getFightId(player);
        FightStage fightStage = FightUtil.getFightStage(world, fightId);

        if (fightStage == FightStage.PRE_FIGHT) {
            List<List<FightBlockCoordinates>> startBlocks = FightUtil.getStartPositions(world, fightId);
            if (startBlocks != null) {
                renderStartPositions(partialTicks, world, mc, player, startBlocks);
            }
        } else if (fightStage == FightStage.FIGHT) {
            EntityLivingBase currentFighter = FightUtil.getCurrentFighter(world, FightUtil.getFightId(player));
            if (currentFighter != player) {
                return;
            }

            renderMovement(partialTicks, world, mc, player);
//            renderDirection(partialTicks, world, mc, player);
            renderSpellRange(partialTicks, world, mc, player);
        }
    }

    public WorldRenderer initWorldRenderer(float partialTicks, Minecraft mc, EntityPlayer player) {
        double deltaX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) partialTicks;
        double deltaY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) partialTicks;
        double deltaZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) partialTicks;

        mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();

        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        worldRenderer.setTranslation(-deltaX, -deltaY, -deltaZ);
        worldRenderer.noColor();

        GlStateManager.doPolygonOffset(-3.0F, -3.0F);
        GlStateManager.enablePolygonOffset();
        GlStateManager.pushMatrix();

        return worldRenderer;
    }

    public void closeWorldRenderer() {
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.draw();
        tessellator.getWorldRenderer().setTranslation(0, 0, 0);

        GlStateManager.doPolygonOffset(0.0F, 0.0F);
        GlStateManager.disablePolygonOffset();
        GlStateManager.popMatrix();
    }

    public void renderStartPositions(float partialTicks, WorldClient world, Minecraft mc, EntityPlayer player, List<List<FightBlockCoordinates>> startBlocks) {
        WorldRenderer worldRenderer = initWorldRenderer(partialTicks, mc, player);
        BlockRendererDispatcher blockRendererDispatcher = mc.getBlockRendererDispatcher();

        for (int i = 0; i < startBlocks.size(); i++) {
            List<FightBlockCoordinates> startBlocksOfTeam = startBlocks.get(i);

            for (FightBlockCoordinates startBlock : startBlocksOfTeam) {
                blockRendererDispatcher.renderBlock(((i == 0) ? WBlocks.fightStart : WBlocks.fightStart2).getDefaultState(), startBlock, world, worldRenderer);
            }
        }

        closeWorldRenderer();
    }

    public void renderMovement(final float partialTicks, final WorldClient world, final Minecraft mc, final EntityPlayer player) {
        final WorldRenderer worldRenderer = initWorldRenderer(partialTicks, mc, player);
        final BlockRendererDispatcher blockRendererDispatcher = mc.getBlockRendererDispatcher();

        final BlockPos currentPosition = FightUtil.getCurrentPosition(player);
        final Integer movement = FightUtil.getFightCharacteristic(player, Characteristic.MOVEMENT);
        if (movement == null) {
            return;
        }

        for (int x = currentPosition.getX() - movement; x <= currentPosition.getX() + movement; x++) {
            for (int z = currentPosition.getZ() - movement; z <= currentPosition.getZ() + movement; z++) {
                if (Math.abs(currentPosition.getX() - x) + Math.abs(currentPosition.getZ() - z) > movement) continue;

                BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();

                int y = currentPosition.getY() - 1;
                for (; y < world.getHeight() && !world.isAirBlock(blockPos.set(x, y + 1, z)); ++y) ;
                for (; y >= 0 && world.isAirBlock(blockPos.set(x, y, z)); --y) ;

                if (!world.getBlockState(blockPos.set(x, y + 1, z)).getBlock().equals(WBlocks.fightInsideWall)) {
                    continue;
                }

                blockRendererDispatcher.renderBlock(WBlocks.fightMovement.getDefaultState(), blockPos.set(x, y, z), world, worldRenderer);
            }
        }

        closeWorldRenderer();
    }

    public void renderDirection(float partialTicks, WorldClient world, Minecraft mc, EntityPlayer player) {
        int posX = (int) Math.floor(player.posX);
        int posY = (int) (player.posY - player.getYOffset());
        int posZ = (int) Math.floor(player.posZ);

        WorldRenderer worldRenderer = initWorldRenderer(partialTicks, mc, player);
        BlockRendererDispatcher blockRendererDispatcher = mc.getBlockRendererDispatcher();

        BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();
        int size = 3;
        for (int x = posX - size; x <= posX + size; x++) {
            int y = posY - 1;
            for (; y < world.getHeight() && !world.isAirBlock(blockPos.set(x, y + 1, posZ)); ++y) ;
            for (; y >= 0 && world.isAirBlock(blockPos.set(x, y, posZ)); --y) ;

            if (!world.getBlockState(blockPos.set(x, y + 1, posZ)).getBlock().equals(WBlocks.fightInsideWall)) {
                continue;
            }

            blockRendererDispatcher.renderBlock(WBlocks.fightDirection.getDefaultState(), blockPos.set(x, y, posZ), world, worldRenderer);
        }

        for (int z = posZ - size; z <= posZ + size; z++) {
            int y = posY;
            for (; y < world.getHeight() && !world.isAirBlock(blockPos.set(posX, y + 1, z)); ++y) ;
            for (; y >= 0 && world.isAirBlock(blockPos.set(posX, y, z)); --y) ;

            if (!world.getBlockState(blockPos.set(posX, y + 1, z)).getBlock().equals(WBlocks.fightInsideWall)) {
                continue;
            }

            blockRendererDispatcher.renderBlock(WBlocks.fightDirection.getDefaultState(), blockPos.set(posX, y, z), world, worldRenderer);
        }

        closeWorldRenderer();
    }

    private void renderSpellRange(float partialTicks, WorldClient world, Minecraft mc, EntityPlayer player) {
        WorldRenderer worldRenderer = initWorldRenderer(partialTicks, mc, player);
        BlockRendererDispatcher blockRendererDispatcher = mc.getBlockRendererDispatcher();

        BlockPos currentPosition = FightUtil.getCurrentPosition(player);
        ItemStack spellStack = FightUtil.getCurrentSpell(player);
        int rangeMin = 1;
        int rangeMax = 1;
        IRangeMode rangeMode = RangeMode.DEFAULT;
        IEffectArea effectArea = EffectArea.POINT;

        if (spellStack != null && spellStack.getItem() instanceof IActiveSpell) {
            IActiveSpell spell = (IActiveSpell) spellStack.getItem();
            int spellLevel = spell.getLevel(spellStack.getItemDamage());
            rangeMin = spell.getRangeMin(spellLevel);
            rangeMax = spell.getRangeMax(spellLevel);
            rangeMode = spell.getRangeMode();
            effectArea = spell.getDisplayEffectArea();
        }

        if (rangeMode == RangeMode.LINE) {
            displayBlockCross(blockRendererDispatcher, WBlocks.fightRange, world, worldRenderer, currentPosition.getX(), currentPosition.getY(), currentPosition.getZ(), rangeMin, rangeMax);
        } else {
            displayBlocksArea(blockRendererDispatcher, WBlocks.fightRange, world, worldRenderer, currentPosition.getX(), currentPosition.getY(), currentPosition.getZ(), rangeMin, rangeMax);
        }

        MovingObjectPosition target = player.rayTrace(rangeMax + 2, partialTicks);
        if (target != null && FightHelper.isAimingPositionValid(currentPosition, target, spellStack)) {
            displayBlocks(blockRendererDispatcher, WBlocks.fightDirection, world, worldRenderer, effectArea.getEffectCoors(currentPosition, target.getBlockPos().getX(), target.getBlockPos().getY(), target.getBlockPos().getZ()));
        }

        closeWorldRenderer();
    }

    private int getFirstAirBlockY(final World world, final BlockPos.MutableBlockPos blockPos) {
        int posX = blockPos.getX();
        int posY = blockPos.getY();
        int posZ = blockPos.getZ();

        while (posY < world.getHeight() && !world.isAirBlock(blockPos.set(posX, posY + 1, posZ))) {
            posY++;
        }

        while (posY >= 0 && world.isAirBlock(blockPos.set(posX, posY, posZ))) {
            posY--;
        }

        return posY + 1;
    }

    private void displayBlocksArea(BlockRendererDispatcher renderBlocks, Block displayBlock, World world, WorldRenderer worldRenderer, int centerX, int centerY, int centerZ, int rangeMin, int rangeMax) {
        BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();
        for (int x = centerX - rangeMax; x <= centerX + rangeMax; x++) {
            for (int z = centerZ - rangeMax; z <= centerZ + rangeMax; z++) {
                if (Math.abs(centerX - x) + Math.abs(centerZ - z) > rangeMax) continue;
                if (Math.abs(centerX - x) + Math.abs(centerZ - z) < rangeMin) continue;

                int y = getFirstAirBlockY(world, blockPos.set(x, centerY - 1, z)) - 1;

                // Do not display block outside the fighting zone
                if (!world.getBlockState(blockPos.set(x, y + 1, z)).getBlock().equals(WBlocks.fightInsideWall)) {
                    continue;
                }

                renderBlocks.renderBlock(displayBlock.getDefaultState(), blockPos.set(x, y, z), world, worldRenderer);
            }
        }
    }

    private void displayBlockCross(BlockRendererDispatcher renderBlocks, Block displayBlock, World world, WorldRenderer worldRenderer, int posX, int posY, int posZ, int rangeMin, int rangeMax) {
        BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();
        for (int x = posX - rangeMax; x <= posX + rangeMax; x++) {
            if (Math.abs(posX - x) > rangeMax) continue;
            if (Math.abs(posX - x) < rangeMin) continue;

            int y = getFirstAirBlockY(world, blockPos.set(x, posY - 1, posZ)) - 1;

            // Do not display block outside the fighting zone
            if (!world.getBlockState(blockPos.set(x, y + 1, posZ)).getBlock().equals(WBlocks.fightInsideWall)) {
                continue;
            }

            renderBlocks.renderBlock(displayBlock.getDefaultState(), blockPos.set(x, y, posZ), world, worldRenderer);
        }

        for (int z = posZ - rangeMax; z <= posZ + rangeMax; z++) {
            if (Math.abs(posZ - z) > rangeMax) continue;
            if (Math.abs(posZ - z) < rangeMin) continue;

            int y = getFirstAirBlockY(world, blockPos.set(posX, posY - 1, z)) - 1;

            // Do not display block outside the fighting zone
            if (!world.getBlockState(blockPos.set(posX, y + 1, z)).getBlock().equals(WBlocks.fightInsideWall)) {
                continue;
            }

            renderBlocks.renderBlock(displayBlock.getDefaultState(), blockPos.set(posX, y, z), world, worldRenderer);
        }
    }

    private void displayBlocks(BlockRendererDispatcher renderBlocks, Block displayBlock, World world, WorldRenderer worldRenderer, List<BlockPos> coordinatesList) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (BlockPos blockPos : coordinatesList) {
            int y = getFirstAirBlockY(world, mutableBlockPos.set(blockPos.getX(), blockPos.getY() - 1, blockPos.getZ())) - 1;

            // Do not display block outside the fighting zone
            if (!world.getBlockState(mutableBlockPos.set(blockPos.getX(), y + 1, blockPos.getZ())).getBlock().equals(WBlocks.fightInsideWall)) {
                continue;
            }

            renderBlocks.renderBlock(displayBlock.getDefaultState(), mutableBlockPos.set(blockPos.getX(), y, blockPos.getZ()), world, worldRenderer);
        }
    }
}
