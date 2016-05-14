package heero.mc.mod.wakcraft.fight;

import heero.mc.mod.wakcraft.WBlocks;
import heero.mc.mod.wakcraft.WConfig;
import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.entity.creature.IFighter;
import heero.mc.mod.wakcraft.entity.property.FightCharacteristicsProperty;
import heero.mc.mod.wakcraft.event.FightEvent;
import heero.mc.mod.wakcraft.fight.FightBlockCoordinates.TYPE;
import heero.mc.mod.wakcraft.fight.FightInfo.FightStage;
import heero.mc.mod.wakcraft.network.packet.PacketExtendedEntityProperty;
import heero.mc.mod.wakcraft.network.packet.fight.*;
import heero.mc.mod.wakcraft.util.FightUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import javax.annotation.Nullable;
import java.util.*;

public enum FightManager {
    INSTANCE;

    protected static final int offsetX[] = new int[]{-1, 1, 0, 0};
    protected static final int offsetZ[] = new int[]{0, 0, -1, 1};
    protected Map<World, Map<Integer, FightInfo>> fights = new HashMap<World, Map<Integer, FightInfo>>();

    /**
     * Initialize the fight manager.
     */
    public void setUp() {
    }

    /**
     * Do the clean up before stopping the server.
     */
    public void teardown() {
        stopFights();
    }

    /**
     * Create a fight (Client side)
     *
     * @param world
     * @param fightId
     * @param fighters
     * @param startBlocks
     * @return
     */
    public void startClientFight(World world, int fightId, List<List<EntityLivingBase>> fighters, List<List<FightBlockCoordinates>> startBlocks) {
        initializeFight(fightId, world, fighters, startBlocks);

        addFightersToFight(fighters, fightId);

        Map<Integer, FightInfo> worldFights = fights.get(world);
        if (worldFights == null) {
            worldFights = new HashMap<>();
            fights.put(world, worldFights);
        }

        worldFights.put(fightId, new FightInfo(fighters, null, startBlocks));
    }

    /**
     * Create a fight (Server side)
     *
     * @param world
     * @param player
     * @param target
     * @return False if the creation failed
     */
    public boolean startServerFight(World world, EntityPlayerMP player, EntityLivingBase target) {
        int posX = MathHelper.floor_double(player.posX);
        int posY = MathHelper.floor_double(player.posY);
        int posZ = MathHelper.floor_double(player.posZ);
        Set<FightBlockCoordinates> fightBlocks = getFightBlocks(world, new BlockPos(posX, posY, posZ), 10);

        if (fightBlocks.size() < 100) {
            player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("cantFightHere")));
            return false;
        }

        int fightId = world.getUniqueDataId("fightId");

        List<List<FightBlockCoordinates>> startBlocks = getSartPositions(fightBlocks);
        List<List<EntityLivingBase>> fighters = createTeams(fightId, player, target);

        initializeFight(fightId, world, fighters, startBlocks);

        addFightersToFight(fighters, fightId);

        createFightMap(world, fightBlocks);

        Map<Integer, FightInfo> worldFights = fights.get(world);
        if (worldFights == null) {
            worldFights = new HashMap<>();
            fights.put(world, worldFights);
        }

        worldFights.put(fightId, new FightInfo(fighters, fightBlocks, startBlocks));

        setStartPositionOfAutonomousFighters(fighters, startBlocks);

        return true;
    }

    /**
     * Analyze the surrounding world and select the available Air blocks to create the fight map.
     *
     * @param world    The world of the fight.
     * @param blockPos Center coordinate of the fight map.
     * @param radius   Maximal distance from the center of the selected blocks.
     * @return The selected fight blocks.
     */
    protected Set<FightBlockCoordinates> getFightBlocks(final World world, final BlockPos blockPos, final int radius) {
        Set<FightBlockCoordinates> fightBlocks = new HashSet<FightBlockCoordinates>();
        ChunkCache chunks = new ChunkCache(world, blockPos.add(-radius, -radius, -radius), blockPos.add(radius, radius, radius), 2);

        BlockPos tmpBlockPos = blockPos;
        while (chunks.getBlockState(tmpBlockPos).equals(Blocks.air) && tmpBlockPos.getY() > 0) {
            tmpBlockPos = tmpBlockPos.down();
        }

        getMapAtPos_rec(chunks, tmpBlockPos, 0, 0, 0, fightBlocks, new BitSet(), radius * radius);

        return fightBlocks;
    }

    protected void getMapAtPos_rec(IBlockAccess world, BlockPos centerPos, int offsetX, int offsetY, int offsetZ, Set<FightBlockCoordinates> fightBlocks, BitSet visited, int radius2) {
        visited.set(hashCoords(offsetX, offsetY, offsetZ));

        // too far
        if (offsetX * offsetX + offsetZ * offsetZ > radius2) {
            FightBlockCoordinates blockPos = new FightBlockCoordinates(centerPos.getX() + offsetX, centerPos.getY() + offsetY + 1, centerPos.getZ() + offsetZ, TYPE.WALL);
            if (world.getBlockState(blockPos).getBlock().equals(Blocks.air)) {
                fightBlocks.add(blockPos);
            }

            blockPos = new FightBlockCoordinates(centerPos.getX() + offsetX, centerPos.getY() + offsetY + 2, centerPos.getZ() + offsetZ, TYPE.WALL);
            if (world.getBlockState(blockPos).getBlock().equals(Blocks.air)) {
                fightBlocks.add(blockPos);
            }

            return;
        }

        int direction = 0;
        // up
        //
        // ??
        // o?
        // |X
        // X?
        if (!world.getBlockState(new BlockPos(centerPos.getX() + offsetX, centerPos.getY() + offsetY + 1, centerPos.getZ() + offsetZ)).getBlock().equals(Blocks.air)) {
            // too hight
            //
            // ??
            // oX
            // |X
            // X?
            if (!world.getBlockState(new BlockPos(centerPos.getX() + offsetX, centerPos.getY() + offsetY + 2, centerPos.getZ() + offsetZ)).getBlock().equals(Blocks.air)) {
                return;
            }

            // not enough space
            //
            // ?X
            // o
            // |X
            // X?
            if (!world.getBlockState(new BlockPos(centerPos.getX() + offsetX, centerPos.getY() + offsetY + 3, centerPos.getZ() + offsetZ)).getBlock().equals(Blocks.air)) {
                fightBlocks.add(new FightBlockCoordinates(centerPos.getX() + offsetX, centerPos.getY() + offsetY + 2, centerPos.getZ() + offsetZ, TYPE.WALL));
                return;
            }

            direction = 1;
            // same height
            //
            // ??
            // o?
            // |
            // XX
        } else if (!world.getBlockState(new BlockPos(centerPos.getX() + offsetX, centerPos.getY() + offsetY, centerPos.getZ() + offsetZ)).getBlock().equals(Blocks.air)) {
            // not enough space
            //
            // ??
            // oX
            // |
            // XX
            if (!world.getBlockState(new BlockPos(centerPos.getX() + offsetX, centerPos.getY() + offsetY + 2, centerPos.getZ() + offsetZ)).getBlock().equals(Blocks.air)) {
                fightBlocks.add(new FightBlockCoordinates(centerPos.getX() + offsetX, centerPos.getY() + offsetY + 1, centerPos.getZ() + offsetZ, TYPE.WALL));
                return;
            }
            // down
            //
            // ??
            // o?
            // |
            // X
            // ??
        } else {
            // too deep
            //
            // ??
            // o?
            // |
            // X
            // ?
            // ??
            if (world.getBlockState(new BlockPos(centerPos.getX() + offsetX, centerPos.getY() + offsetY - 1, centerPos.getZ() + offsetZ)).getBlock().equals(Blocks.air)) {
                fightBlocks.add(new FightBlockCoordinates(centerPos.getX() + offsetX, centerPos.getY() + offsetY + 1, centerPos.getZ() + offsetZ, TYPE.WALL));

                // so the player can't jump on the wall block
                //
                // ??
                // o
                // |
                // X
                // ?
                // ??
                if (world.getBlockState(new BlockPos(centerPos.getX() + offsetX, centerPos.getY() + offsetY + 2, centerPos.getZ() + offsetZ)).getBlock().equals(Blocks.air)) {
                    fightBlocks.add(new FightBlockCoordinates(centerPos.getX() + offsetX, centerPos.getY() + offsetY + 2, centerPos.getZ() + offsetZ, TYPE.WALL));
                }

                return;
            }

            // ceiling to low
            //
            // ??
            // oX
            // |
            // X
            // ?X
            if (!world.getBlockState(new BlockPos(centerPos.getX() + offsetX, centerPos.getY() + offsetY + 2, centerPos.getZ() + offsetZ)).getBlock().equals(Blocks.air)) {
                return;
            }

            direction = -1;
        }

        for (int i = 0; i < 4; i++) {
            // Direction == -1 : 0, 1, 2
            // Direction ==  0 :    1, 2
            // Direction ==  1 :       2, 3
            if ((direction == -1 && i < 3) || (direction == 0 && (i == 1 || i == 2)) || (direction == 1 && i > 1)) {
                FightBlockCoordinates blockCoords = new FightBlockCoordinates(centerPos.getX() + offsetX, centerPos.getY() + offsetY + i, centerPos.getZ() + offsetZ, TYPE.NORMAL, (direction + 1 == i) ? 1 : 0);
                if (!fightBlocks.add(blockCoords)) {
                    fightBlocks.remove(blockCoords);
                    fightBlocks.add(blockCoords);
                }
            }
        }

        offsetY += direction;
        for (int i = 0; i < 4; i++) {
            if (!visited.get(hashCoords(offsetX + FightManager.offsetX[i], offsetY, offsetZ + FightManager.offsetZ[i]))) {
                getMapAtPos_rec(world, centerPos, offsetX + FightManager.offsetX[i], offsetY, offsetZ + FightManager.offsetZ[i], fightBlocks, visited, radius2);
            }
        }
    }

    protected final int hashCoords(int x, int y, int z) {
        return ((y & 0xFF) << 16) + ((x & 0xFF) << 8) + (z & 0xFF);
    }

    /**
     * Select the starting position among the fight blocks.
     *
     * @param fightBlocks Fight blocks.
     * @return
     */
    protected List<List<FightBlockCoordinates>> getSartPositions(Set<FightBlockCoordinates> fightBlocks) {
        List<FightBlockCoordinates> fightBlocksList = new ArrayList<FightBlockCoordinates>(fightBlocks);

        List<List<FightBlockCoordinates>> startBlocks = new ArrayList<List<FightBlockCoordinates>>();
        startBlocks.add(new ArrayList<FightBlockCoordinates>());
        startBlocks.add(new ArrayList<FightBlockCoordinates>());

        Random random = new Random();

        int maxStartBlock = 6;
        for (List<FightBlockCoordinates> startBlocksOfTeam : startBlocks) {
            while (startBlocksOfTeam.size() < maxStartBlock) {
                int index = random.nextInt(fightBlocks.size());
                FightBlockCoordinates coords = fightBlocksList.get(index);

                if (coords.getType() != TYPE.NORMAL) {
                    continue;
                }

                for (List<FightBlockCoordinates> startBlocksOfTeamTmp : startBlocks) {
                    if (startBlocksOfTeamTmp.contains(coords)) {
                        continue;
                    }
                }

                if (fightBlocksList.contains(coords.down())) {
                    continue;
                }

                startBlocksOfTeam.add(coords);
            }
        }

        return startBlocks;
    }

    public List<List<FightBlockCoordinates>> getSartPositions(World world, int FightId) {
        Map<Integer, FightInfo> worldFights = fights.get(world);
        if (worldFights == null) {
            return null;
        }

        FightInfo fightInfo = worldFights.get(FightId);
        if (fightInfo == null) {
            return null;
        }

        return fightInfo.getStartBlocks();
    }

    /**
     * Add the fight blocks to the world.
     *
     * @param world       The world of the fight.
     * @param fightBlocks The block coordinates list.
     */
    protected void createFightMap(World world, Set<FightBlockCoordinates> fightBlocks) {
        for (FightBlockCoordinates block : fightBlocks) {
            if (!world.getBlockState(block).getBlock().equals(Blocks.air)) {
                WLog.warning("Trying to replace a block different from Air");
                continue;
            }

            switch (block.getType()) {
                case NORMAL:
                    world.setBlockState(block, WBlocks.fightInsideWall.getDefaultState());
                    break;
                case WALL:
                    world.setBlockState(block, WBlocks.fightWall.getDefaultState());
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Remove the fight blocks from the world.
     *
     * @param world       The world of the fight.
     * @param fightBlocks The block coordinates list.
     */
    protected void destroyFightMap(World world, Set<FightBlockCoordinates> fightBlocks) {
        if (fightBlocks == null) {
            return;
        }

        for (FightBlockCoordinates blockCoords : fightBlocks) {
            Block block = world.getBlockState(blockCoords).getBlock();
            if (!block.equals(WBlocks.fightWall) && !block.equals(WBlocks.fightInsideWall) && !block.equals(WBlocks.fightStart) && !block.equals(WBlocks.fightStart2)) {
                WLog.warning("Trying to restore a block different of fight blocks");
                continue;
            }

            world.setBlockToAir(blockCoords);
        }
    }

    /**
     * Create fighter' teams
     *
     * @param fightId
     * @param player
     * @param opponent
     * @return
     */
    protected List<List<EntityLivingBase>> createTeams(int fightId, EntityPlayerMP player, EntityLivingBase opponent) {
        ArrayList<List<EntityLivingBase>> fighters = new ArrayList<List<EntityLivingBase>>();

        ArrayList<EntityLivingBase> fighters1 = new ArrayList<EntityLivingBase>();
        ArrayList<EntityLivingBase> fighters2 = new ArrayList<EntityLivingBase>();

        fighters1.add(player);

        if (opponent instanceof IFighter) {
            Set<UUID> group = ((IFighter) opponent).getGroup();
            if (group == null) {
                fighters2.add(opponent);
            } else {
                for (UUID fighterUUID : group) {
                    for (Object entity : opponent.worldObj.loadedEntityList) {
                        if (entity instanceof IFighter && ((EntityLivingBase) entity).getUniqueID().equals(fighterUUID)) {
                            fighters2.add((EntityLivingBase) entity);
                        }
                    }
                }
            }
        } else {
            fighters2.add(opponent);
        }

        fighters.add(fighters1);
        fighters.add(fighters2);

        return fighters;
    }

    /**
     * Initialize the fight.
     *
     * @param fightId     Identifier of the fight.
     * @param world       The world.
     * @param fighters    The fighters list.
     * @param startBlocks The stat blocks list.
     * @return The fighter list.
     */
    protected void initializeFight(int fightId, World world, List<List<EntityLivingBase>> fighters, List<List<FightBlockCoordinates>> startBlocks) {
        MinecraftForge.EVENT_BUS.post(new FightEvent.FightStartEvent(world, fightId, fighters, startBlocks));

        for (int teamId = 0; teamId < 2; teamId++) {
            List<EntityLivingBase> team = fighters.get(teamId);

            for (EntityLivingBase entity : team) {
                if (entity instanceof EntityPlayerMP) {
                    Wakcraft.packetPipeline.sendTo(new PacketFightStart(fightId, fighters, startBlocks), (EntityPlayerMP) entity);
                }
            }
        }
    }

    /**
     * Terminate the fight.
     *
     * @param fightId  Identifier of the fight.
     * @param world    World of the fight.
     * @param fighters The fighters list.
     */
    protected void terminateFight(int fightId, World world, List<List<EntityLivingBase>> fighters) {
        MinecraftForge.EVENT_BUS.post(new FightEvent.FightStopEvent(world, fightId, fighters));

        for (int teamId = 0; teamId < 2; teamId++) {
            List<EntityLivingBase> team = fighters.get(teamId);

            for (EntityLivingBase entity : team) {
                if (entity instanceof EntityPlayerMP) {
                    Wakcraft.packetPipeline.sendTo(new PacketFightStop(fightId), (EntityPlayerMP) entity);
                }
            }
        }
    }

    protected void setStartPositionOfAutonomousFighters(List<List<EntityLivingBase>> fighters, List<List<FightBlockCoordinates>> startBlocks) {
        for (int j = 0; j < fighters.size(); j++) {
            List<EntityLivingBase> team = fighters.get(j);

            for (int i = 0; i < team.size(); i++) {
                if (!FightUtil.isAutonomousFighter(team.get(i))) {
                    continue;
                }

                selectPosition(team.get(i), startBlocks.get(j).get(i));
            }
        }
    }

    protected void setStartPositionOfRemainingFighters(List<List<EntityLivingBase>> fighters, List<List<FightBlockCoordinates>> startBlocks) {
        for (int j = 0; j < fighters.size(); j++) {
            List<EntityLivingBase> team = fighters.get(j);

            List<BlockPos> availablePositions = new ArrayList<BlockPos>(startBlocks.get(j));
            for (EntityLivingBase fighter : team) {
                BlockPos startPosition = FightUtil.getStartPosition(fighter);
                if (startPosition == null) {
                    continue;
                }

                availablePositions.remove(startPosition);
            }

            Iterator<BlockPos> iterator = availablePositions.iterator();
            for (EntityLivingBase fighter : team) {
                if (FightUtil.getStartPosition(fighter) != null) {
                    continue;
                }

                selectPosition(fighter, iterator.next());
            }
        }
    }

    protected void moveFighterToStartPosition(List<List<EntityLivingBase>> fighters) {
        for (int j = 0; j < fighters.size(); j++) {
            List<EntityLivingBase> team = fighters.get(j);

            for (int i = 0; i < team.size(); i++) {
                BlockPos startPosition = FightUtil.getStartPosition(team.get(i));
                if (startPosition == null) {
                    WLog.warning("The fighter " + team.get(i) + " doesn't have a starting position");
                    continue;
                }

                team.get(i).setPositionAndUpdate(startPosition.getX() + 0.5, startPosition.getY(), startPosition.getZ() + 0.5);
            }
        }
    }

    /**
     * Return the defeated team (if there is one).
     *
     * @param world   World of the fight.
     * @param fightId Identifier of the fight.
     * @return Return the defeated team, -1 if an error occurred, 0 if there is
     * no defeated team yet.
     */
    public int getDefeatedTeam(World world, int fightId) {
        FightInfo fight = fights.get(world).get(fightId);

        for (int teamId = 1; teamId <= 2; teamId++) {
            List<EntityLivingBase> team = fight.getFightersByTeam().get(teamId - 1);

            boolean living = false;
            for (EntityLivingBase entity : team) {
                if (!entity.isEntityAlive()) {
                    continue;
                }

                living = true;
            }

            if (!living) {
                return teamId;
            }
        }

        return 0;
    }

    /**
     * Terminate and clean the fight.
     *
     * @param world   World of the fight.
     * @param fightId Identifier of the fight.
     */
    public void stopFight(World world, int fightId) {
        Map<Integer, FightInfo> fightsOfWorld = fights.get(world);
        if (fightsOfWorld == null) {
            WLog.warning("Trying to stop a fight that does not exist (wrong world)");
            return;
        }

        FightInfo fight = fightsOfWorld.remove(fightId);
        if (fight == null) {
            WLog.warning("Trying to stop a fight that does not exist (wrong id)");
            return;
        }

        terminateFight(fightId, world, fight.getFightersByTeam());
        destroyFightMap(world, fight.getFightBlocks());
        removeFightersFromFight(fight.getFightersByTeam());
    }

    /**
     * Stop all the fight of the world.
     */
    public void stopFights() {
        for (World world : fights.keySet()) {
            for (int fightId : fights.get(world).keySet()) {
                stopFight(world, fightId);
            }
        }
    }

    /**
     * Inform fighters of their entering in a fight.
     *
     * @param fighters The fighters list.
     * @param fightId  Identifier of the fight.
     */
    public void addFightersToFight(List<List<EntityLivingBase>> fighters, int fightId) {
        for (int teamId = 0; teamId < fighters.size(); teamId++) {
            Iterator<EntityLivingBase> entities = fighters.get(teamId).iterator();
            while (entities.hasNext()) {
                EntityLivingBase entity = entities.next();

                FightUtil.setProperties(entity, fightId, teamId);
            }
        }
    }

    /**
     * Inform fighters of their exiting of the fight
     *
     * @param fightersByTeam Fighters list.
     */
    public void removeFightersFromFight(List<List<EntityLivingBase>> fightersByTeam) {
        for (List<EntityLivingBase> team : fightersByTeam) {
            for (EntityLivingBase fighter : team) {
                FightUtil.resetProperties(fighter);
                FightUtil.setStartPosition(fighter, null);
                FightUtil.resetDisplayName(fighter);
            }
        }
    }

    public void updateFights(int tickCounter) {
        for (World world : fights.keySet()) {
            if (world.isRemote) {
                continue;
            }

            for (int fightId : fights.get(world).keySet()) {
                updateFight(world, fightId, tickCounter);
            }
        }
    }

    protected void updateFight(World world, int fightId, int tickCounter) {
        FightInfo fightInfo = fights.get(world).get(fightId);
        if (!fightInfo.tick()) {
            return;
        }

        switch (fightInfo.getStage()) {
            case PRE_FIGHT:
                setStartPositionOfRemainingFighters(fightInfo.getFightersByTeam(), fightInfo.getStartBlocks());
                moveFighterToStartPosition(fightInfo.getFightersByTeam());
                initFightersCurrentPosition(fightInfo.getFightersByTeam());
                initFightersCharacteristics(fightInfo.getFightersByTeam());
                initFightersDisplayName(fightInfo.getFightersByTeam());

                updateFightStage(world, fightId, FightStage.FIGHT);
                fightInfo.setStage(FightStage.FIGHT, WConfig.getWakfuFightTurnDuration());

                startTurn(world, fightId, fightInfo.getCurrentFighter());

                break;

            case FIGHT:
                resetFighterCharacteristics(fightInfo.fightersByTeam, fightInfo.getCurrentFighter());

                EntityLivingBase nextFighter = getNextAvailableFighter(fightInfo);
                if (nextFighter == null) {
                    WLog.warning("Can't find a living fighter for the next turn");
                    stopFight(fightInfo.getCurrentFighter().worldObj, FightUtil.getFightId(fightInfo.getCurrentFighter()));
                    return;
                }

                WLog.info("Start new turn for : " + nextFighter.getName());

                fightInfo.setCurrentFighter(nextFighter);
                fightInfo.updateStageDuration(WConfig.getWakfuFightTurnDuration());

                startTurn(world, fightId, fightInfo.getCurrentFighter());

                break;

            default:
                break;
        }
    }

    public void changeFightStage(World world, int fightId, FightStage stage) {
        Map<Integer, FightInfo> fightsOfWorld = fights.get(world);
        if (fightsOfWorld == null) {
            WLog.warning("Trying update the stage of a fight that does not exist (wrong world)");
            return;
        }

        FightInfo fight = fightsOfWorld.get(fightId);
        if (fight == null) {
            WLog.warning("Trying to update the stage of a fight that does not exist (wrong id)");
            return;
        }

        switch (stage) {
            case FIGHT:
                initFightersCurrentPosition(fight.fightersByTeam);
                break;

            default:
                break;
        }

        updateFightStage(world, fightId, stage);
        fight.setStage(stage);
    }

    protected void updateFightStage(World world, int fightId, FightStage stage) {
        MinecraftForge.EVENT_BUS.post(new FightEvent.FightChangeStageEvent(world, fightId, stage));

        List<List<EntityLivingBase>> fighters = fights.get(world).get(fightId).getFightersByTeam();
        for (int teamId = 0; teamId < 2; teamId++) {
            List<EntityLivingBase> team = fighters.get(teamId);

            for (EntityLivingBase entity : team) {
                if (entity instanceof EntityPlayerMP) {
                    Wakcraft.packetPipeline.sendTo(new PacketFightChangeStage(fightId, stage), (EntityPlayerMP) entity);
                }
            }
        }
    }

    public FightStage getFightStage(World world, int fightId) {
        Map<Integer, FightInfo> fightsOfWorld = fights.get(world);
        if (fightsOfWorld == null) {
            WLog.warning("Trying to get the stage of a fight that does not exist (wrong world)");
            return FightStage.UNKNOWN;
        }

        FightInfo fight = fightsOfWorld.get(fightId);
        if (fight == null) {
            WLog.warning("Trying to get the stage of a fight that does not exist (wrong id)");
            return FightStage.UNKNOWN;
        }

        return fight.getStage();
    }

    public EntityLivingBase getCurrentFighter(World world, int fightId) {
        Map<Integer, FightInfo> fightsOfWorld = fights.get(world);
        if (fightsOfWorld == null) {
            WLog.warning("Trying to get the current fighter of a fight that does not exist (wrong world)");
            return null;
        }

        FightInfo fight = fightsOfWorld.get(fightId);
        if (fight == null) {
            WLog.warning("Trying to get the current fighter of a fight that does not exist (wrong id)");
            return null;
        }

        if (fight.getStage() != FightStage.FIGHT) {
            WLog.warning("Trying to get the current fighter in an incompatible stage ( " + fight.getStage() + " )");
            return null;
        }

        return fight.getCurrentFighter();
    }

    public void setCurrentFighter(World world, int fightId, EntityLivingBase fighter) {
        Map<Integer, FightInfo> fightsOfWorld = fights.get(world);
        if (fightsOfWorld == null) {
            WLog.warning("Trying to set the current fighter of a fight that does not exist (wrong world)");
            return;
        }

        FightInfo fight = fightsOfWorld.get(fightId);
        if (fight == null) {
            WLog.warning("Trying to set the current fighter of a fight that does not exist (wrong id)");
            return;
        }

        if (fight.getStage() != FightStage.FIGHT) {
            WLog.warning("Trying to set the current fighter in an incompatible stage ( " + fight.getStage() + " )");
            return;
        }

        startTurn(world, fightId, fighter);
        fight.setCurrentFighter(fighter);
    }

    public void selectPosition(EntityLivingBase entity, @Nullable BlockPos position) {
        int teamId = FightUtil.getTeam(entity);
        int fightId = FightUtil.getFightId(entity);
        List<List<EntityLivingBase>> fightersByTeam = fights.get(entity.worldObj).get(fightId).getFightersByTeam();

        if (position != null && !isStartPositionAvailable(entity.worldObj, fightId, teamId, fightersByTeam.get(teamId), position)) {
            return;
        }

        if (entity.worldObj.isRemote) {
            Wakcraft.packetPipeline.sendToServer(new PacketFightSelectPosition(fightId, entity, position));
        } else {
            FightUtil.setStartPosition(entity, position);

            IMessage packet = new PacketFightSelectPosition(fightId, entity, position);
            for (List<EntityLivingBase> team : fightersByTeam) {
                for (EntityLivingBase fighter : team) {
                    if (fighter instanceof EntityPlayerMP) {
                        Wakcraft.packetPipeline.sendTo(packet, (EntityPlayerMP) fighter);
                    }
                }
            }
        }
    }

    protected boolean isStartPositionAvailable(World world, int fightId, int teamId, List<EntityLivingBase> fighters, BlockPos position) {
        // Not a valid start position
        List<List<FightBlockCoordinates>> startPositions = getSartPositions(world, fightId);
        if (startPositions == null || !startPositions.get(teamId).contains(position)) {
            return false;
        }

        // Already reserved position
        for (EntityLivingBase fighter : fighters) {
            if (position.equals(FightUtil.getStartPosition(fighter))) {
                return false;
            }
        }

        return true;
    }

    protected EntityLivingBase getNextAvailableFighter(FightInfo fightInfo) {
        int nbFighter = fightInfo.getFightersCount();
        for (int i = 1; i <= nbFighter; i++) {
            EntityLivingBase fighter = fightInfo.getNextFighter(i);
            if (!fighter.isEntityAlive()) {
                continue;
            }

            return fighter;
        }

        return null;
    }

    protected void initFightersCharacteristics(List<List<EntityLivingBase>> fightersByTeam) {
        for (List<EntityLivingBase> team : fightersByTeam) {
            for (EntityLivingBase fighter : team) {
                initFighterCharacteristics(fightersByTeam, fighter);
            }
        }
    }

    protected void initFighterCharacteristics(List<List<EntityLivingBase>> fightersByTeam, EntityLivingBase currentFighter) {
        for (Characteristic characteristic : Characteristic.values()) {
            FightUtil.resetFightCharacteristic(currentFighter, characteristic);
        }

        for (List<EntityLivingBase> team : fightersByTeam) {
            for (EntityLivingBase fighter : team) {
                if (fighter instanceof EntityPlayerMP) {
                    Wakcraft.packetPipeline.sendTo(new PacketExtendedEntityProperty(currentFighter, FightCharacteristicsProperty.IDENTIFIER), (EntityPlayerMP) fighter);
                }
            }
        }
    }

    protected void resetFighterCharacteristics(List<List<EntityLivingBase>> fightersByTeam, EntityLivingBase currentFighter) {
        FightUtil.resetFightCharacteristic(currentFighter, Characteristic.ACTION);
        FightUtil.resetFightCharacteristic(currentFighter, Characteristic.MOVEMENT);

        for (List<EntityLivingBase> team : fightersByTeam) {
            for (EntityLivingBase fighter : team) {
                if (fighter instanceof EntityPlayerMP) {
                    // TODO : Don't send all characteristics
                    Wakcraft.packetPipeline.sendTo(new PacketExtendedEntityProperty(currentFighter, FightCharacteristicsProperty.IDENTIFIER), (EntityPlayerMP) fighter);
                }
            }
        }
    }

    protected void initFightersDisplayName(List<List<EntityLivingBase>> fightersByTeam) {
        for (List<EntityLivingBase> team : fightersByTeam) {
            for (EntityLivingBase fighter : team) {
                FightUtil.updateDisplayName(fighter);
            }
        }
    }

    protected void initFightersCurrentPosition(List<List<EntityLivingBase>> fightersByTeam) {
        for (List<EntityLivingBase> team : fightersByTeam) {
            for (EntityLivingBase fighter : team) {
                FightUtil.setCurrentPosition(fighter, FightUtil.getStartPosition(fighter));
            }
        }
    }

    public void startTurn(World world, int fightId, EntityLivingBase fighter) {
        MinecraftForge.EVENT_BUS.post(new FightEvent.FightStartTurnEvent(world, fightId, fighter));

        List<List<EntityLivingBase>> fightersByTeam = fights.get(world).get(fightId).getFightersByTeam();
        for (List<EntityLivingBase> team : fightersByTeam) {
            for (EntityLivingBase entity : team) {
                if (entity instanceof EntityPlayerMP) {
                    Wakcraft.packetPipeline.sendTo(new PacketFightStartTurn(fightId, fighter), (EntityPlayerMP) entity);
                }
            }
        }
    }

    public void updateFights(final EntityLivingBase fighter) {
        final BlockPos previousPosition = FightUtil.getCurrentPosition(fighter);
        final double distance = fighter.getDistance(previousPosition.getX() + 0.5, fighter.posY, previousPosition.getZ() + 0.5);
        if (distance < MathHelper.sqrt_double(2) / 2 + 0.1) {
            return;
        }

        final BlockPos currentPosition = new BlockPos(MathHelper.floor_double(fighter.posX), MathHelper.floor_double(fighter.posY), MathHelper.floor_double(fighter.posZ));
        final int usedMovementPoint = MathHelper.abs_int(currentPosition.getX() - previousPosition.getX()) + MathHelper.abs_int(currentPosition.getZ() - previousPosition.getZ());
        final Integer movementPoint = FightUtil.getFightCharacteristic(fighter, Characteristic.MOVEMENT);
        if (movementPoint == null) {
            return;
        }

        int remainingMovementPoint = movementPoint - usedMovementPoint;
        if (remainingMovementPoint < 0) {
            WLog.warning("Fighter " + fighter + " used more movement point that disponible");
            remainingMovementPoint = 0;
        }

        FightUtil.setFightCharacteristic(fighter, Characteristic.MOVEMENT, remainingMovementPoint);
        FightUtil.setCurrentPosition(fighter, currentPosition);
    }

    public boolean isThereFighterInAABB(World world, int fightId, AxisAlignedBB boundingBox) {
        Map<Integer, FightInfo> fightsOfWorld = fights.get(world);
        if (fightsOfWorld == null) {
            WLog.warning("Trying to get the fighters of a fight that does not exist (wrong world)");
            return true;
        }

        FightInfo fight = fightsOfWorld.get(fightId);
        if (fight == null) {
            WLog.warning("Trying to get the fighters of a fight that does not exist (wrong id)");
            return true;
        }

        for (List<EntityLivingBase> team : fight.fightersByTeam) {
            for (EntityLivingBase fighter : team) {
                BlockPos position = FightUtil.getCurrentPosition(fighter);
                if (position.getX() + 1 > boundingBox.minX && position.getX() < boundingBox.maxX && position.getY() + 1 > boundingBox.minY && position.getY() < boundingBox.maxY && position.getZ() + 1 > boundingBox.minZ && position.getZ() < boundingBox.maxZ) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Returns the fighters list.
     *
     * @param world   The work of the fight.
     * @param fightId The id of the fight.
     * @return The fighters list, null if the fight is not found.
     */
    public List<List<EntityLivingBase>> getFighters(World world, int fightId) {
        Map<Integer, FightInfo> fightsOfWorld = fights.get(world);
        if (fightsOfWorld == null) {
            WLog.warning("Trying to get the fighters of a fight that does not exist (wrong world)");
            return null;
        }

        FightInfo fight = fightsOfWorld.get(fightId);
        if (fight == null) {
            WLog.warning("Trying to get the fighters of a fight that does not exist (wrong id)");
            return null;
        }

        return fight.getFightersByTeam();
    }
}
