package heero.mc.mod.wakcraft.fight;

import heero.mc.mod.wakcraft.WBlocks;
import heero.mc.mod.wakcraft.WConfig;
import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.entity.creature.IFighter;
import heero.mc.mod.wakcraft.event.FightEvent;
import heero.mc.mod.wakcraft.fight.FightBlockCoordinates.TYPE;
import heero.mc.mod.wakcraft.fight.FightInfo.FightStage;
import heero.mc.mod.wakcraft.network.packet.fight.*;
import heero.mc.mod.wakcraft.util.FightUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
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

    protected static final int NBR_START_BLOCK = 6;
    protected static final int OFFSET_X[] = new int[]{-1, 1, 0, 0};
    protected static final int OFFSET_Z[] = new int[]{0, 0, -1, 1};

    protected final Map<World, Map<Integer, FightInfo>> _fights = new HashMap<>();

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

    protected Map<Integer, FightInfo> getFightsOfWorld(final World world) {
        final Map<Integer, FightInfo> fightsOfWorld = _fights.get(world);

        if (fightsOfWorld == null) {
            WLog.warning("Trying to get the fights for a world with no fights");
            return null;
        }

        return fightsOfWorld;
    }

    protected FightInfo getFightInfo(final World world, final Integer fightId) {
        final Map<Integer, FightInfo> fightsOfWorld = getFightsOfWorld(world);
        if (fightsOfWorld == null) {
            return null;
        }

        final FightInfo fight = fightsOfWorld.get(fightId);
        if (fight == null) {
            WLog.warning("Trying to get the fight information for a fight that does not exist (wrong id : " + fightId + ")");
            return null;
        }

        return fight;
    }

    /**
     * Create a fight (Client side)
     *
     * @param world       The world where the fight is
     * @param fightId     The fight id
     * @param fighters    The fighters, by team
     * @param startBlocks The start positions, by team
     */
    public void startClientFight(final World world, final int fightId, final List<List<EntityLivingBase>> fighters, final List<List<FightBlockCoordinates>> startBlocks) {
        initializeFight(fightId, world, fighters, startBlocks);

        addFightersToFight(fighters, fightId);

        Map<Integer, FightInfo> worldFights = _fights.get(world);
        if (worldFights == null) {
            worldFights = new HashMap<>();
            _fights.put(world, worldFights);
        }

        worldFights.put(fightId, new FightInfo(fighters, null, startBlocks));
    }

    /**
     * Create a fight (Server side)
     *
     * @param world  The world where the fight is
     * @param player The player who launched the fight
     * @param target The attacked target
     * @return False if the creation failed
     */
    public boolean startServerFight(final World world, final EntityPlayerMP player, final EntityLivingBase target) {
        final int posY = MathHelper.floor_double(player.posY);
        final int posX = MathHelper.floor_double(player.posX);
        final int posZ = MathHelper.floor_double(player.posZ);
        final Set<FightBlockCoordinates> fightBlocks = getFightBlocks(world, new BlockPos(posX, posY, posZ), 10);

        if (fightBlocks.size() < 100) {
            player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("cantFightHere")));
            return false;
        }

        final int fightId = world.getUniqueDataId("fightId");

        final List<List<FightBlockCoordinates>> startBlocks = getStartPositions(fightBlocks);
        final List<List<EntityLivingBase>> fighters = createTeams(fightId, player, target);

        initializeFight(fightId, world, fighters, startBlocks);

        addFightersToFight(fighters, fightId);

        createFightMap(world, fightBlocks);

        Map<Integer, FightInfo> worldFights = _fights.get(world);
        if (worldFights == null) {
            worldFights = new HashMap<>();
            _fights.put(world, worldFights);
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
        final Set<FightBlockCoordinates> fightBlocks = new HashSet<>();
        final ChunkCache chunks = new ChunkCache(world, blockPos.add(-radius, -radius, -radius), blockPos.add(radius, radius, radius), 2);

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
            // too high
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
            if (!visited.get(hashCoords(offsetX + FightManager.OFFSET_X[i], offsetY, offsetZ + FightManager.OFFSET_Z[i]))) {
                getMapAtPos_rec(world, centerPos, offsetX + FightManager.OFFSET_X[i], offsetY, offsetZ + FightManager.OFFSET_Z[i], fightBlocks, visited, radius2);
            }
        }
    }

    protected final int hashCoords(final int x, final int y, final int z) {
        return ((y & 0xFF) << 16) + ((x & 0xFF) << 8) + (z & 0xFF);
    }

    /**
     * Select the starting position among the fight blocks.
     *
     * @param fightBlocks Fight blocks.
     * @return The start positions, by team
     */
    protected List<List<FightBlockCoordinates>> getStartPositions(final Set<FightBlockCoordinates> fightBlocks) {
        final List<FightBlockCoordinates> fightBlocksList = new ArrayList<>(fightBlocks);

        final List<List<FightBlockCoordinates>> startBlocks = new ArrayList<>();
        startBlocks.add(new ArrayList<FightBlockCoordinates>());
        startBlocks.add(new ArrayList<FightBlockCoordinates>());

        final Random random = new Random();

        for (final List<FightBlockCoordinates> startBlocksOfTeam : startBlocks) {
            LOOP:
            while (startBlocksOfTeam.size() < NBR_START_BLOCK) {
                final int index = random.nextInt(fightBlocks.size());
                final FightBlockCoordinates coords = fightBlocksList.get(index);

                if (coords.getType() != TYPE.NORMAL) {
                    continue;
                }

                for (final List<FightBlockCoordinates> startBlocksOfTeamTmp : startBlocks) {
                    if (startBlocksOfTeamTmp.contains(coords)) {
                        continue LOOP;
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

    public List<List<FightBlockCoordinates>> getStartPositions(final World world, final int fightId) {
        final FightInfo fightInfo = getFightInfo(world, fightId);
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
    protected void createFightMap(final World world, final Set<FightBlockCoordinates> fightBlocks) {
        for (final FightBlockCoordinates block : fightBlocks) {
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
    protected void destroyFightMap(final World world, final Set<FightBlockCoordinates> fightBlocks) {
        if (fightBlocks == null) {
            return;
        }

        for (final FightBlockCoordinates blockCoords : fightBlocks) {
            final Block block = world.getBlockState(blockCoords).getBlock();
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
    protected List<List<EntityLivingBase>> createTeams(final int fightId, final EntityPlayerMP player, final EntityLivingBase opponent) {
        final ArrayList<List<EntityLivingBase>> fighters = new ArrayList<>();

        final ArrayList<EntityLivingBase> fighters1 = new ArrayList<>();
        final ArrayList<EntityLivingBase> fighters2 = new ArrayList<>();

        fighters1.add(player);

        if (opponent instanceof IFighter) {
            final Set<UUID> group = ((IFighter) opponent).getGroup();
            if (group == null) {
                fighters2.add(opponent);
            } else {
                for (final UUID fighterUUID : group) {
                    for (final Entity entity : opponent.worldObj.loadedEntityList) {
                        if (entity instanceof IFighter && entity.getUniqueID().equals(fighterUUID)) {
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
    protected void initializeFight(final int fightId, final World world, final List<List<EntityLivingBase>> fighters, final List<List<FightBlockCoordinates>> startBlocks) {
        MinecraftForge.EVENT_BUS.post(new FightEvent.FightStartEvent(world, fightId, fighters, startBlocks));

        for (final List<EntityLivingBase> team : fighters) {
            for (final EntityLivingBase fighter : team) {
                if (fighter instanceof EntityPlayerMP) {
                    Wakcraft.packetPipeline.sendTo(new PacketFightStart(fightId, fighters, startBlocks), (EntityPlayerMP) fighter);
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
    protected void terminateFight(final World world, final int fightId, final List<List<EntityLivingBase>> fighters) {
        MinecraftForge.EVENT_BUS.post(new FightEvent.FightStopEvent(world, fightId, fighters));

        for (final List<EntityLivingBase> team : fighters) {
            for (final EntityLivingBase fighter : team) {
                if (fighter instanceof EntityPlayerMP) {
                    Wakcraft.packetPipeline.sendTo(new PacketFightStop(fightId), (EntityPlayerMP) fighter);
                }
            }
        }
    }

    protected void setStartPositionOfAutonomousFighters(final List<List<EntityLivingBase>> fighters, final List<List<FightBlockCoordinates>> startBlocks) {
        for (int teamId = 0; teamId < fighters.size(); teamId++) {
            final List<EntityLivingBase> team = fighters.get(teamId);

            for (int fighterId = 0; fighterId < team.size(); fighterId++) {
                final EntityLivingBase fighter = team.get(fighterId);
                if (!FightUtil.isAutonomousFighter(fighter)) {
                    continue;
                }

                selectPosition(fighter, startBlocks.get(teamId).get(fighterId));
            }
        }
    }

    protected void setStartPositionOfRemainingFighters(final List<List<EntityLivingBase>> fighters, final List<List<FightBlockCoordinates>> startBlocks) {
        for (int teamId = 0; teamId < fighters.size(); teamId++) {
            final List<EntityLivingBase> team = fighters.get(teamId);

            final List<BlockPos> availablePositions = new ArrayList<BlockPos>(startBlocks.get(teamId));
            for (final EntityLivingBase fighter : team) {
                final BlockPos startPosition = FightUtil.getStartPosition(fighter);
                if (startPosition == null) {
                    continue;
                }

                availablePositions.remove(startPosition);
            }

            final Iterator<BlockPos> iterator = availablePositions.iterator();
            for (final EntityLivingBase fighter : team) {
                if (FightUtil.getStartPosition(fighter) != null) {
                    continue;
                }

                selectPosition(fighter, iterator.next());
            }
        }
    }

    protected void moveFighterToStartPosition(final List<List<EntityLivingBase>> fighters) {
        for (final List<EntityLivingBase> team : fighters) {
            for (final EntityLivingBase fighter : team) {
                final BlockPos startPosition = FightUtil.getStartPosition(fighter);
                if (startPosition == null) {
                    WLog.warning("The fighter " + fighter + " doesn't have a starting position");
                    continue;
                }

                fighter.setPositionAndUpdate(startPosition.getX() + 0.5, startPosition.getY(), startPosition.getZ() + 0.5);
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
    public int getDefeatedTeam(final World world, final int fightId) {
        final FightInfo fight = getFightInfo(world, fightId);
        if (fight == null) {
            return -1;
        }

        final List<List<EntityLivingBase>> fighters = fight.getFightersByTeam();
        for (int teamId = 0; teamId < fighters.size(); teamId++) {
            final List<EntityLivingBase> team = fighters.get(teamId);

            boolean living = false;
            for (final EntityLivingBase entity : team) {
                if (!entity.isEntityAlive()) {
                    continue;
                }

                living = true;
            }

            if (!living) {
                return teamId + 1;
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
    public void stopFight(final World world, final int fightId) {
        final Map<Integer, FightInfo> fightsOfWorld = getFightsOfWorld(world);
        if (fightsOfWorld == null) {
            return;
        }

        final FightInfo fight = fightsOfWorld.remove(fightId);
        if (fight == null) {
            WLog.warning("Trying to stop a fight that does not exist (wrong id)");
            return;
        }

        terminateFight(world, fightId, fight.getFightersByTeam());
        destroyFightMap(world, fight.getFightBlocks());
        removeFightersFromFight(fight.getFightersByTeam());
    }

    /**
     * Stop all the fight of the world.
     */
    public void stopFights() {
        for (final World world : _fights.keySet()) {
            for (final int fightId : getFightsOfWorld(world).keySet()) {
                stopFight(world, fightId);
            }
        }
    }

    /**
     * Inform fighters of their entering in a fight.
     *
     * @param fightersByTeam The fighters list.
     * @param fightId        Identifier of the fight.
     */
    public void addFightersToFight(final List<List<EntityLivingBase>> fightersByTeam, final int fightId) {
        for (int teamId = 0; teamId < fightersByTeam.size(); teamId++) {
            for (final EntityLivingBase entity : fightersByTeam.get(teamId)) {
                FightUtil.setProperties(entity, fightId, teamId);
            }
        }
    }

    /**
     * Inform fighters of their exiting of the fight
     *
     * @param fightersByTeam Fighters list.
     */
    public void removeFightersFromFight(final List<List<EntityLivingBase>> fightersByTeam) {
        for (final List<EntityLivingBase> team : fightersByTeam) {
            for (final EntityLivingBase fighter : team) {
                FightUtil.resetProperties(fighter);
                FightUtil.setStartPosition(fighter, null);
                FightUtil.resetDisplayName(fighter);
            }
        }
    }

    public void updateFights(final int tickCounter) {
        for (final World world : _fights.keySet()) {
            if (world.isRemote) {
                continue;
            }

            for (final int fightId : getFightsOfWorld(world).keySet()) {
                updateFight(world, fightId, tickCounter);
            }
        }
    }

    protected void updateFight(final World world, final int fightId, final int tickCounter) {
        final FightInfo fightInfo = getFightInfo(world, fightId);
        if (fightInfo == null || !fightInfo.tick()) {
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

                final EntityLivingBase nextFighter = getNextAvailableFighter(fightInfo);
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

    public void changeFightStage(final World world, final int fightId, final FightStage stage) {
        final FightInfo fight = getFightInfo(world, fightId);
        if (fight == null) {
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

    protected void updateFightStage(final World world, final int fightId, final FightStage stage) {
        MinecraftForge.EVENT_BUS.post(new FightEvent.FightChangeStageEvent(world, fightId, stage));

        final List<List<EntityLivingBase>> fighters = getFighters(world, fightId);
        for (int teamId = 0; teamId < 2; teamId++) {
            final List<EntityLivingBase> team = fighters.get(teamId);

            for (final EntityLivingBase entity : team) {
                if (entity instanceof EntityPlayerMP) {
                    Wakcraft.packetPipeline.sendTo(new PacketFightChangeStage(fightId, stage), (EntityPlayerMP) entity);
                }
            }
        }
    }

    public FightStage getFightStage(final World world, final int fightId) {
        final FightInfo fight = getFightInfo(world, fightId);
        if (fight == null) {
            return FightStage.UNKNOWN;
        }

        return fight.getStage();
    }

    public EntityLivingBase getCurrentFighter(final World world, final int fightId) {
        final FightInfo fight = getFightInfo(world, fightId);
        if (fight == null) {
            return null;
        }

        if (fight.getStage() != FightStage.FIGHT) {
            WLog.warning("Trying to get the current fighter in an incompatible stage ( " + fight.getStage() + " )");
            return null;
        }

        return fight.getCurrentFighter();
    }

    public void setCurrentFighter(final World world, final int fightId, final EntityLivingBase fighter) {
        final FightInfo fight = getFightInfo(world, fightId);
        if (fight == null) {
            return;
        }

        if (fight.getStage() != FightStage.FIGHT) {
            WLog.warning("Trying to set the current fighter in an incompatible stage ( " + fight.getStage() + " )");
            return;
        }

        startTurn(world, fightId, fighter);
        fight.setCurrentFighter(fighter);
    }

    public void selectPosition(final EntityLivingBase entity, @Nullable final BlockPos position) {
        final int teamId = FightUtil.getTeam(entity);
        final int fightId = FightUtil.getFightId(entity);
        final List<List<EntityLivingBase>> fightersByTeam = getFighters(entity.worldObj, fightId);

        if (position != null && !isStartPositionAvailable(entity.worldObj, fightId, teamId, fightersByTeam.get(teamId), position)) {
            return;
        }

        if (entity.worldObj.isRemote) {
            Wakcraft.packetPipeline.sendToServer(new PacketFightSelectPosition(fightId, entity, position));
        } else {
            FightUtil.setStartPosition(entity, position);

            final IMessage packet = new PacketFightSelectPosition(fightId, entity, position);
            for (final List<EntityLivingBase> team : fightersByTeam) {
                for (final EntityLivingBase fighter : team) {
                    if (fighter instanceof EntityPlayerMP) {
                        Wakcraft.packetPipeline.sendTo(packet, (EntityPlayerMP) fighter);
                    }
                }
            }
        }
    }

    protected boolean isStartPositionAvailable(final World world, final int fightId, final int teamId, final List<EntityLivingBase> fighters, final BlockPos position) {
        // Not a valid start position
        final List<List<FightBlockCoordinates>> startPositions = getStartPositions(world, fightId);
        if (startPositions == null || !startPositions.get(teamId).contains(position)) {
            return false;
        }

        // Already reserved position
        for (final EntityLivingBase fighter : fighters) {
            if (position.equals(FightUtil.getStartPosition(fighter))) {
                return false;
            }
        }

        return true;
    }

    protected EntityLivingBase getNextAvailableFighter(final FightInfo fightInfo) {
        final int nbFighter = fightInfo.getFightersCount();
        for (int i = 1; i <= nbFighter; i++) {
            final EntityLivingBase fighter = fightInfo.getNextFighter(i);
            if (!fighter.isEntityAlive()) {
                continue;
            }

            return fighter;
        }

        return null;
    }

    protected void initFightersCharacteristics(final List<List<EntityLivingBase>> fightersByTeam) {
        for (final List<EntityLivingBase> team : fightersByTeam) {
            for (final EntityLivingBase fighter : team) {
                initFighterCharacteristics(fightersByTeam, fighter);
            }
        }
    }

    protected void initFighterCharacteristics(final List<List<EntityLivingBase>> fightersByTeam, final EntityLivingBase currentFighter) {
        for (final Characteristic characteristic : Characteristic.values()) {
            FightUtil.resetFightCharacteristic(currentFighter, characteristic);
        }

        for (final List<EntityLivingBase> team : fightersByTeam) {
            for (final EntityLivingBase fighter : team) {
                if (fighter instanceof EntityPlayerMP) {
                    FightUtil.sendFightCharacteristicToClient((EntityPlayerMP) fighter, currentFighter);
                }
            }
        }
    }

    protected void resetFighterCharacteristics(final List<List<EntityLivingBase>> fightersByTeam, final EntityLivingBase currentFighter) {
        FightUtil.resetFightCharacteristic(currentFighter, Characteristic.ACTION);
        FightUtil.resetFightCharacteristic(currentFighter, Characteristic.MOVEMENT);

        for (final List<EntityLivingBase> team : fightersByTeam) {
            for (final EntityLivingBase fighter : team) {
                if (fighter instanceof EntityPlayerMP) {
                    FightUtil.sendFightCharacteristicToClient((EntityPlayerMP) fighter, currentFighter);
                }
            }
        }
    }

    protected void initFightersDisplayName(final List<List<EntityLivingBase>> fightersByTeam) {
        for (final List<EntityLivingBase> team : fightersByTeam) {
            for (final EntityLivingBase fighter : team) {
                FightUtil.updateDisplayName(fighter);
            }
        }
    }

    protected void initFightersCurrentPosition(final List<List<EntityLivingBase>> fightersByTeam) {
        for (final List<EntityLivingBase> team : fightersByTeam) {
            for (final EntityLivingBase fighter : team) {
                FightUtil.setCurrentPosition(fighter, FightUtil.getStartPosition(fighter));
            }
        }
    }

    public void startTurn(final World world, final int fightId, final EntityLivingBase fighter) {
        MinecraftForge.EVENT_BUS.post(new FightEvent.FightStartTurnEvent(world, fightId, fighter));

        final List<List<EntityLivingBase>> fightersByTeam = getFightsOfWorld(world).get(fightId).getFightersByTeam();
        for (final List<EntityLivingBase> team : fightersByTeam) {
            for (final EntityLivingBase entity : team) {
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
        if (currentPosition.getX() == previousPosition.getX() && currentPosition.getZ() == previousPosition.getZ()) {
            return;
        }

        final int usedMovementPoint = MathHelper.abs_int(currentPosition.getX() - previousPosition.getX()) + MathHelper.abs_int(currentPosition.getZ() - previousPosition.getZ());
        final Integer movementPoint = FightUtil.getFightCharacteristic(fighter, Characteristic.MOVEMENT);
        if (movementPoint == null) {
            return;
        }

        int remainingMovementPoint = movementPoint - usedMovementPoint;
        if (remainingMovementPoint < 0) {
            WLog.warning("Fighter " + fighter + " used more movement point than available");
            remainingMovementPoint = 0;
        }

        FightUtil.setFightCharacteristic(fighter, Characteristic.MOVEMENT, remainingMovementPoint);
        FightUtil.setCurrentPosition(fighter, currentPosition);

        if (!fighter.worldObj.isRemote) {
            FightUtil.sendFightCharacteristicToClient((EntityPlayerMP) fighter, fighter);
        }
    }

    public boolean isThereFighterInAABB(final World world, final int fightId, final Entity currentFighter, final AxisAlignedBB boundingBox) {
        final FightInfo fight = getFightInfo(world, fightId);
        if (fight == null) {
            return true;
        }

        for (final List<EntityLivingBase> team : fight.fightersByTeam) {
            for (final EntityLivingBase fighter : team) {
                if (fighter == currentFighter) {
                    continue;
                }

                final BlockPos position = FightUtil.getCurrentPosition(fighter);
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
    public List<List<EntityLivingBase>> getFighters(final World world, final int fightId) {
        final FightInfo fight = getFightInfo(world, fightId);
        if (fight == null) {
            return null;
        }

        return fight.getFightersByTeam();
    }
}
