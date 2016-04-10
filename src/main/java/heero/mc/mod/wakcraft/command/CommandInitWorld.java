package heero.mc.mod.wakcraft.command;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.block.BlockSlab;
import net.minecraft.block.Block;
import net.minecraft.command.*;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.commons.io.IOUtils;
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class CommandInitWorld extends CommandBase implements ICommand {
    private static final String BLOCK_DEFAULT_NAME = "default";
    private static final int MC_OFFSET_Y = 51;
    private static final int WAKFU_OFFSET_Y = MC_OFFSET_Y * 4;
    private static final int WAKFU_SEA_LEVEL_DEFAULT = 0;
    private static final int MAP_W = 18;
    private static final int MAP_H = 18;

    private static boolean isMapping = false;
    private static int wakfuOffsetY = 0;
    private static Properties blocks = null;
    private static Map<Long, Integer> gfxsId;
    private static Map<Integer, Integer> unknownGfxsId = new TreeMap<>();
    private static JarFile tplgMapJar = null;
    private static Enumeration<JarEntry> tplgMapEntries = null;

    @Override
    public String getCommandName() {
        return "initWorld";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "initWorld <map id> <wakfu sea level>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1 && args.length != 2) {
            throw new WrongUsageException(getCommandUsage(sender));
        }

        try {
            final int mapId = Integer.parseInt(args[0]);

            wakfuOffsetY = WAKFU_OFFSET_Y + ((args.length == 2) ? Integer.parseInt(args[1]) : WAKFU_SEA_LEVEL_DEFAULT);

            mappingStart(sender.getEntityWorld(), sender.getCommandSenderEntity(), mapId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        mappingUpdate(event.player, event.player.worldObj);
    }

    private static void log(final Entity player, final String message) {
//        System.out.println(message);
        player.addChatMessage(new ChatComponentText(message));
    }

    private static void mappingStart(final World world, final Entity player, final int mapId) {
        if (world.isRemote) {
            return;
        }

        if (isMapping) {
            log(player, "Already mapping...");
            return;
        }

        File baseFolder = Decode.getBaseFolder(player, world);
        if (baseFolder == null) {
            return;
        }

        blocks = Decode.decodeBlockFile(player, baseFolder);
        if (blocks == null) {
            return;
        }

        Map<Integer, Integer> elementsIdToGfxId = Decode.decodeElementsLib(player, baseFolder);
        if (elementsIdToGfxId == null) {
            return;
        }

        gfxsId = Decode.decodeGfxFiles(player, elementsIdToGfxId, blocks, baseFolder, mapId, null, null);
        if (gfxsId == null) {
            return;
        }

        log(player, "Load TPLG");

        File tplgFolder = new File(baseFolder.getAbsolutePath() + "/tplg");
        if (!tplgFolder.exists()) {
            log(player, tplgFolder.getAbsolutePath() + " not found");

            return;
        }

        try {
            tplgMapJar = new JarFile(tplgFolder.getAbsolutePath() + "/" + mapId + ".jar");

            tplgMapEntries = tplgMapJar.entries();
        } catch (Exception e) {
            e.printStackTrace();

            log(player, "Invalid map id : " + mapId);
            return;
        }

        log(player, "Start mapping");

        isMapping = true;
    }

    private static void mappingUpdate(final Entity entity, final World world) {
        if (world.isRemote || !isMapping) {
            return;
        }

        if (!tplgMapEntries.hasMoreElements()) {
            mappingStop();

            log(entity, "Mapping completed");
            return;
        }

        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
                mappingStop();

                log(entity, "Mapping aborted");
                return;
            }
        }

        JarEntry entry = tplgMapEntries.nextElement();

        String[] coord = entry.getName().split("_");
        if (coord.length != 2) {
            log(entity, "Incorrect file name : " + entry.getName());
            return;
        }

        decodeTplgFile(entity, world, entry, Integer.parseInt(coord[1]), Integer.parseInt(coord[0]));
    }

    private static void mappingStop() {
        isMapping = false;

        for (Integer gfxId : unknownGfxsId.keySet()) {
            System.out.println(gfxId + " not found, used " + unknownGfxsId.get(gfxId) + " times");
        }

        try {
            tplgMapJar.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        tplgMapEntries = null;
        tplgMapJar = null;
    }

    private static void decodeTplgFile(final Entity entity, final World world, final ZipEntry fileEntry, final int mapX, final int mapY) {
//        log(entity, "Decode file map (" + mapX + ", " + mapY + ")");

        InputStream fileIS = null;
        byte bufferBytes[] = null;
        try {
            fileIS = tplgMapJar.getInputStream(fileEntry);
            bufferBytes = IOUtils.toByteArray(fileIS);
        } catch (IOException e) {
            e.printStackTrace();
            log(entity, "Error");

            return;
        } finally {
            IOUtils.closeQuietly(fileIS);
        }

        ByteBuffer buffer = ByteBuffer.wrap(bufferBytes);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        generateMap(buffer, entity, world, mapX, mapY);
    }

    private static int getBitRequired(final int nbTilesPlus1) {
        int i = 1;
        int j = 0;
        while (nbTilesPlus1 > i) {
            i *= 2;
            j++;
        }

        return j;
    }

    private static int getTileType(final long[] tilesArray, final int tileIndex, final int nbTilesPlus1) {
        int nbBitRequired = getBitRequired(nbTilesPlus1);
        int j = 64 / nbBitRequired;
        int mask = (1 << nbBitRequired) - 1;

        long l2 = tilesArray[(tileIndex / j)];
        l2 >>= nbBitRequired * (tileIndex % j);
        return (int) (l2 & mask);
    }

    private static void setBlock(final World world, final int x, final int y, final int z, final Block block, final int meta, final int updateFlags) {
        world.setBlockState(new BlockPos(-x, y, z), block.getStateFromMeta(meta), updateFlags);
    }

    private static void setAirBlock(final World world, final int x, final int y, final int z) {
        setBlock(world, x, y, z, Blocks.air, 0, 0);
    }

    private static boolean setWaterBlock(final World world, final int x, final int y, final int z, final int blockHeight) {
        int pos = getWakfuYFromBlockY(y);
        for (int i = 3; i >= 0; i--) {
            long coordId = Decode.getIdFromWakfuCoords((short) x, (short) z, (short) (pos + i));
            Integer gfxId = gfxsId.get(coordId);
            if (blocks == null || gfxId == null) {
                continue;
            }

            String blockName = blocks.getProperty(gfxId.toString());
            if (blockName == null || !blockName.startsWith("water")) {
                continue;
            }

            int depth = Integer.parseInt(blockName.substring("water".length()));
            for (int j = 0; j < 3; j++) {
                if (j < depth) {
                    setBlock(world, x, y - j, z, Blocks.water, 0, 0);
                } else {
                    setBlock(world, x, y - j, z, Block.getBlockFromName("wakcraft:" + blocks.getProperty(BLOCK_DEFAULT_NAME)), 0, 0);
                }
            }

            return true;
        }

        return false;
    }

    private static void setSlabBlock(final World world, final int x, final int y, final int z, final int bottomPos, final int height) {
        Block block = getBlockName(x, y, z, bottomPos, height);

        setBlock(world, x, y, z, block, (block instanceof BlockSlab) ? height << 2 + bottomPos : 0, 0);
    }

    private static void generateBaseColumn(final World world, final int x, final int y, final int z, final int blockHeight) {
        if (y < 5) { // It's really deep, so it's probably a tile that is not visible in 2D
            return;
        }

        if (!setWaterBlock(world, x, y, z, blockHeight)) {
            setSlabBlock(world, x, y, z, 0, blockHeight);

            for (int y2 = y - 1; y2 >= MC_OFFSET_Y - 3; y2--) {
                setSlabBlock(world, x, y2, z, 0, 3);
            }
        }

        for (int y2 = y + 1; y2 <= MC_OFFSET_Y - 1; y2++) {
            setAirBlock(world, x, y2, z);
        }
    }

    private static Block getBlockName(final int x, final int y, final int z, final int blockBottomPos, final int blockHeight) {
        int blockTop = blockBottomPos + blockHeight;
        long coordId = Decode.getIdFromWakfuCoords((short) x, (short) z, (short) (getWakfuYFromBlockY(y) + blockTop));

        do {
            Integer gfxId = gfxsId.get(coordId);
            if (blocks == null || gfxId == null) {
                break;
            }

            String blockName = blocks.getProperty(gfxId.toString());
            if (blockName == null) {
//                System.out.println("x : " + x + ", y : " + z + ", h : " + blockHeight + ", gfxId : " + gfxId); // TODO remove
                final Integer count = unknownGfxsId.get(gfxId);
                unknownGfxsId.put(gfxId, (count == null) ? 1 : count + 1);

                break;
            }

            if (blockName.startsWith("water")) {
                // Water block not at the bottom
                break;
            }

            Block block;
            if (blockTop != 3) {
                block = Block.getBlockFromName(Reference.MODID + ":" + blockName + "_slab");
            } else if (blockName.endsWith("_north") || blockName.endsWith("_east") || blockName.endsWith("_south") || blockName.endsWith("_west")) {
                block = Block.getBlockFromName(Reference.MODID + ":" + blockName.substring(0, blockName.lastIndexOf('_')));
            } else {
                block = Block.getBlockFromName(Reference.MODID + ":" + blockName);
            }

            if (block == null) {
                System.out.println("Incorrect block name " + blockName);
                break;
            }

            return block;
        } while (true);

        return blockTop == 3 ? Block.getBlockFromName("wakcraft:" + blocks.getProperty(BLOCK_DEFAULT_NAME)) : Block.getBlockFromName("wakcraft:" + blocks.getProperty(BLOCK_DEFAULT_NAME) + "_slab");
    }

    private static int getBlockYFromWakfuY(final int height) {
        return (wakfuOffsetY + height) / 4;
    }

    private static int getBlockHFromWakfuY(final int height) {
        return (wakfuOffsetY + height) % 4;
    }

    private static int getWakfuYFromBlockY(final int height) {
        return height * 4 - wakfuOffsetY;
    }

    private static void generateMap(final ByteBuffer buffer, final Entity entity, final World world, final int mapX, final int mapY) {
        final byte encodingType = buffer.get();
        switch (encodingType) {
            case 0:
                generateFlatMap(buffer, entity, world, mapX, mapY);
                break;
            case 1:
                generateFlatMap2(buffer, entity, world, mapX, mapY);
                break;
            case 2:
                generateFlatMap3(buffer, entity, world, mapX, mapY);
                break;
            case 3:
                generateSimpleMap(buffer, entity, world, mapX, mapY);
                break;
            case 4:
                generateSimpleMap2(buffer, entity, world, mapX, mapY);
                break;
            case 5:
                generateComplexMap(buffer, entity, world, mapX, mapY);
                break;
            default:
                log(entity, "Unknown file encoding " + encodingType);
                break;
        }
    }

    private static boolean testCoords(final ByteBuffer buffer, final Entity entity, final int mapX, final int mapY) {
        final int mapYControl = buffer.getShort();
        final int mapXControl = buffer.getShort();
        if (mapXControl != mapX || mapYControl != mapY) {
            log(entity, "Map coordinates control failed, expected : (" + mapX + ", " + mapY + "), found (" + mapXControl + ", " + mapYControl + ")");
            return false;
        }

        return true;
    }

    /*
    Basic case, all the map is at the same height value
     */
    private static void generateFlatMap(final ByteBuffer buffer, final Entity entity, final World world, final int mapX, final int mapY) {
        if (!testCoords(buffer, entity, mapX, mapY)) {
            return;
        }

        final short defaultHeight = buffer.getShort();

        byte var1 = buffer.get();
        byte var2 = buffer.get();
        byte vat3 = buffer.get();

//        log(entity, "Flat map, PosX : " + mapX + ", PosY : " + mapY + ", Default height : " + defaultHeight);

        final int mcBlockY = getBlockYFromWakfuY(defaultHeight);
        final int mcBlockH = getBlockHFromWakfuY(defaultHeight);

        // create map
        for (int i = 0; i < MAP_W; i++) {
            for (int j = 0; j < MAP_H; j++) {
                final int x = mapX * MAP_W + i;
                final int z = mapY * MAP_H + j;
                generateBaseColumn(world, x, mcBlockY, z, mcBlockH);
            }
        }
    }

    /*
    All the map is at the same height value, but each block has extra information
     */
    private static void generateFlatMap2(final ByteBuffer buffer, final Entity entity, final World world, final int mapX, final int mapY) {
        if (!testCoords(buffer, entity, mapX, mapY)) {
            return;
        }

        final short defaultHeight = buffer.getShort();

//        log(entity, "Flat map (2), PosX : " + mapX + ", PosY : " + mapY + ", Default height : " + defaultHeight);

        if (defaultHeight == -3) {
            return;
        }

        final byte[] unknownArray = new byte[100];
        buffer.get(unknownArray, 0, ((MAP_H * MAP_W) + 7) >> 3); // 41

        final int mcBlockY = getBlockYFromWakfuY(defaultHeight);
        final int mcBlockH = getBlockHFromWakfuY(defaultHeight);

        // create map
        for (int i = 0; i < MAP_W; i++) {
            for (int j = 0; j < MAP_H; j++) {
                byte var1 = buffer.get();
                byte var2 = buffer.get();
                byte vat3 = buffer.get();

                final int x = mapX * MAP_W + i;
                final int z = mapY * MAP_H + j;
                generateBaseColumn(world, x, mcBlockY, z, mcBlockH);
            }
        }
    }

    /*
    All the map is at the same height value, but each block has extra information.
     */
    private static void generateFlatMap3(final ByteBuffer buffer, final Entity entity, final World world, final int mapX, final int mapY) {
        if (!testCoords(buffer, entity, mapX, mapY)) {
            return;
        }

        final short defaultHeight = buffer.getShort();

//        log(entity, "Flat map (3), PosX : " + mapX + ", PosY : " + mapY + ", Default height : " + defaultHeight);

        if (defaultHeight == -3) {
            return;
        }

        final byte[] unknownArray = new byte[100];
        buffer.get(unknownArray, 0, ((MAP_H * MAP_W) + 7) >> 3); // 41

        final byte nbTilesType = buffer.get();
//        log(entity, "Nb tiles type : " + nbTilesType);

        final byte[] var1 = new byte[nbTilesType];
        final byte[] var2 = new byte[nbTilesType];
        final byte[] var3 = new byte[nbTilesType];

        for (int j = 0; j < nbTilesType; j++) {
            var1[j] = buffer.get();
            var2[j] = buffer.get();
            var3[j] = buffer.get();
        }

        final int nbInt = buffer.get() & 0xFF;
        for (int j = 0; j < nbTilesType; j++) {
            buffer.getInt();
        }

        final int mcBlockY = getBlockYFromWakfuY(defaultHeight);
        final int mcBlockH = getBlockHFromWakfuY(defaultHeight);

        // create map
        for (int i = 0; i < MAP_W; i++) {
            for (int j = 0; j < MAP_H; j++) {
                final int x = mapX * MAP_W + i;
                final int z = mapY * MAP_H + j;
                generateBaseColumn(world, x, mcBlockY, z, mcBlockH);
            }
        }
    }

    /*
    Simple case, each block can has a different height value.
    */
    private static void generateSimpleMap(final ByteBuffer buffer, final Entity entity, final World world, final int mapX, final int mapY) {
        if (!testCoords(buffer, entity, mapX, mapY)) {
            return;
        }

        final short defaultHeight = buffer.getShort();

//        log(entity, "Simple map, PosX : " + mapX + ", PosY : " + mapY);

        final byte[] unknownArray = new byte[100];
        buffer.get(unknownArray, 0, ((MAP_H * MAP_W) + 7) >> 3); // 41

        final byte nbTilesType = buffer.get();
//        log(entity, "Nb tiles type : " + nbTilesType);

        final byte[] var1 = new byte[nbTilesType];
        final byte[] var2 = new byte[nbTilesType];
        final byte[] var3 = new byte[nbTilesType];
        final short[] tilesHeight = new short[nbTilesType];
        final byte[] fHC = new byte[nbTilesType];
        final byte[] fHD = new byte[nbTilesType];

        for (int j = 0; j < nbTilesType; j++) {
            var1[j] = buffer.get();
            var2[j] = buffer.get();
            var3[j] = buffer.get();
            tilesHeight[j] = buffer.getShort();
            fHC[j] = buffer.get();
            fHD[j] = buffer.get();
        }

        // create map
        for (int i = 0; i < MAP_W; i++) {
            for (int j = 0; j < MAP_H; j++) {
                final int height = tilesHeight[j * MAP_W + j];
                final int mcBlockY = getBlockYFromWakfuY(height);
                final int mcBlockH = getBlockHFromWakfuY(height);

                final int x = mapX * MAP_W + i;
                final int z = mapY * MAP_H + j;
                generateBaseColumn(world, x, mcBlockY, z, mcBlockH);
            }
        }
    }

    /*
    Simple case, each block can has a different height value.
     */
    private static void generateSimpleMap2(final ByteBuffer buffer, final Entity entity, final World world, final int mapX, final int mapY) {
        if (!testCoords(buffer, entity, mapX, mapY)) {
            return;
        }

        final short defaultHeight = buffer.getShort();

//        log(entity, "Simple map (2), PosX : " + mapX + ", PosY : " + mapY);

        final byte[] unknownArray = new byte[100];
        buffer.get(unknownArray, 0, ((MAP_H * MAP_W) + 7) >> 3); // 41

        final byte nbTilesType = buffer.get();
//        log(entity, "Nb tiles type : " + nbTilesType);

        final byte[] fHy = new byte[nbTilesType];
        final byte[] fHz = new byte[nbTilesType];
        final byte[] fHA = new byte[nbTilesType];
        final short[] tilesHeight = new short[nbTilesType];
        final byte[] fHC = new byte[nbTilesType];
        final byte[] fHD = new byte[nbTilesType];

        for (int j = 0; j < nbTilesType; j++) {
            fHy[j] = buffer.get();
            fHz[j] = buffer.get();
            fHA[j] = buffer.get();
            tilesHeight[j] = buffer.getShort();
            fHC[j] = buffer.get();
            fHD[j] = buffer.get();
        }

        final int nbTiles = buffer.get();
//        log(entity, "Nb tiles : " + nbTiles);

        final long[] tilesArray = new long[nbTiles];
        for (int i = 0; i < nbTiles; i++) {
            tilesArray[i] = buffer.getLong();
        }

        // create map
        for (int i = 0; i < MAP_W; i++) {
            for (int j = 0; j < MAP_H; j++) {
                final int tileType = getTileType(tilesArray, i * 18 + j, nbTilesType);
                final int height = tilesHeight[tileType];
                final int mcBlockY = getBlockYFromWakfuY(height);
                final int mcBlockH = getBlockHFromWakfuY(height);

                final int x = mapX * MAP_W + i;
                final int z = mapY * MAP_H + j;
                generateBaseColumn(world, x, mcBlockY, z, mcBlockH);
            }
        }
    }

    /*
    Complex case, each position can has different height value,
     */
    private static void generateComplexMap(final ByteBuffer buffer, final Entity entity, final World world, final int mapX, final int mapY) {
        if (!testCoords(buffer, entity, mapX, mapY)) {
            return;
        }

        final short defaultHeight = buffer.getShort();

//        log(entity, "Complex map, PosX : " + mapX + ", PosY : " + mapY);

        final byte[] unknownArray = new byte[100];
        buffer.get(unknownArray, 0, ((MAP_H * MAP_W) + 7) >> 3); // 41

        final byte nbTilesType = buffer.get();
//        log(entity, "Nb tiles type : " + nbTilesType);

        final byte[] fHy = new byte[nbTilesType];
        final byte[] fHz = new byte[nbTilesType];
        final byte[] fHA = new byte[nbTilesType];
        final short[] tilesHeight = new short[nbTilesType];
        final byte[] fHC = new byte[nbTilesType];
        final byte[] fHD = new byte[nbTilesType];

        for (int j = 0; j < nbTilesType; j++) {
            fHy[j] = buffer.get();
            fHz[j] = buffer.get();
            fHA[j] = buffer.get();
            tilesHeight[j] = buffer.getShort();
            fHC[j] = buffer.get();
            fHD[j] = buffer.get();
        }

        final int nbTiles = buffer.get();
//        log(entity, "Nb tiles : " + nbTiles);

        final long[] tilesArray = new long[nbTiles];
        for (int i = 0; i < nbTiles; i++) {
            tilesArray[i] = buffer.getLong();
        }

        final int nbIntegers = buffer.getShort();

        final int[] integerArray = new int[nbIntegers];
        for (int i = 0; i < nbIntegers; i++) {
            integerArray[i] = buffer.getInt();
        }

        // create map
        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 18; j++) {
                final int tileType = getTileType(tilesArray, i * 18 + j, nbTilesType + 1);
                if (tileType == 0) {
                    continue;
                }

                final int height = tilesHeight[tileType - 1];
                final int mcBlockY = getBlockYFromWakfuY(height);
                final int mcBlockH = getBlockHFromWakfuY(height);

                final int x = mapX * MAP_W + i;
                final int z = mapY * MAP_H + j;
                generateBaseColumn(world, x, mcBlockY, z, mcBlockH);
            }
        }

        // special tiles
        for (int k = 0; k < integerArray.length; k++) {
            int y = (integerArray[k] >> 0) & 0xFF;
            int x = (integerArray[k] >> 8) & 0xFF;
            int tileType = (integerArray[k] >> 16) & 0xFFFF;

            int height = tilesHeight[tileType];

            setSlabBlock(world, mapX * MAP_W + x, (wakfuOffsetY + height) / 4, mapY * MAP_H + y, (wakfuOffsetY + height) % 4, 0);
        }
    }
}
