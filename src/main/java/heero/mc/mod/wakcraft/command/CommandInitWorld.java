package heero.mc.mod.wakcraft.command;

import heero.mc.mod.wakcraft.WBlocks;
import net.minecraft.block.Block;
import net.minecraft.command.*;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class CommandInitWorld extends CommandBase implements ICommand {
    private static final int MC_OFFSET_Y = 51;
    private static final int WAKFU_OFFSET_Y = MC_OFFSET_Y * 4;
    private static final int WAKFU_SEA_LEVEL_DEFAULT = 0;
    private static final int MAP_W = 18;
    private static final int MAP_H = 18;

    private static boolean isMapping = false;
    private static int wakfuOffsetY = 0;
    private static Map<Long, Integer> gfxsId;
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
//        player.addChatMessage(new ChatComponentText(message));
    }

    private static void mappingStart(final World world, final Entity player, final int mapId) {
        if (world.isRemote) {
            return;
        }

        if (isMapping) {
            log(player, "Already mapping...");
            return;
        }

        File baseFolder = new File(world.getSaveHandler().getWorldDirectory().getParentFile().getAbsolutePath() + "/wakfu_map");
        if (!baseFolder.exists()) {
            log(player, "No '" + baseFolder.getAbsolutePath() + "' folder");
            return;
        }

        File tplgFolder = new File(baseFolder.getAbsolutePath() + "/tplg");
        if (!tplgFolder.exists()) {
            log(player, "No '" + tplgFolder.getAbsolutePath() + "' folder");
            return;
        }

        File gfxFolder = new File(baseFolder.getAbsolutePath() + "/gfx");
        if (!gfxFolder.exists()) {
            log(player, "No '" + gfxFolder.getAbsolutePath() + "' folder");
            return;
        }

        try {
            tplgMapJar = new JarFile(tplgFolder.getAbsolutePath() + "/" + mapId + ".jar");
            JarFile gfxMapJar = new JarFile(gfxFolder.getAbsolutePath() + "/" + mapId + ".jar");

            tplgMapEntries = tplgMapJar.entries();

            log(player, "Load gfx");

            gfxsId = decodeGfxFiles(player, world, gfxMapJar);
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
        System.out.println("mapping end"); // TODO remove
        isMapping = false;

        try {
            tplgMapJar.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        tplgMapEntries = null;
        tplgMapJar = null;
    }

    private static Map<Long, Integer> decodeGfxFiles(final Entity entity, final World world, final JarFile gfxMapJar) {
        Map<Long, Integer> gfxsId = new HashMap<>();
        byte[] bufferTmp = new byte[30000];

        Enumeration<JarEntry> entries = gfxMapJar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String fileName = entry.getName();

            String[] coords = fileName.split("_");
            if (coords.length != 2) {
                continue;
            }

//            System.out.println("Decode file : " + entry.getName());

            ByteBuffer buffer;
            try {
                InputStream fileIS = gfxMapJar.getInputStream(entry);
                int size = fileIS.read(bufferTmp);
                fileIS.close();
                buffer = ByteBuffer.wrap(bufferTmp);
                buffer.order(ByteOrder.LITTLE_ENDIAN);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            buffer.getInt();
            buffer.getInt();
            buffer.getShort();

            buffer.getInt();
            buffer.getInt();
            buffer.getShort();

            int nbGroup = buffer.getShort();
            for (int j = 0; j < nbGroup; j++) {
                int var1 = buffer.getInt();
                int var2 = buffer.get();
                int var3 = buffer.getInt();
//                System.out.println("grp " + j + " : " + var1 + ", " + var2 + ", " + var3);
            }

            int nbColor = buffer.getShort();
            for (int j = 0; j < nbColor; j++) {
                int colorType = buffer.get();

                if ((colorType & 0x1) == 1) {
                    buffer.get();
                    buffer.get();
                    buffer.get();
                }

                if ((colorType & 0x2) == 2) {
                    buffer.get();
                }

                if ((colorType & 0x4) == 4) {
                    if ((colorType & 0x1) == 1) {
                        buffer.get();
                        buffer.get();
                        buffer.get();
                    }

                    if ((colorType & 0x2) == 2) {
                        buffer.get();
                    }
                }
            }

            int minElementY = buffer.getInt();
            int minElementX = buffer.getInt();
//            System.out.println("min element y : " + minElementY);
//            System.out.println("min element x : " + minElementX);

            int nbElement = buffer.getShort();
//            System.out.println("nb element : " + nbElement);
            for (int i1 = 0; i1 < nbElement; i1++) {
                int min_y = minElementY + buffer.get();
                int max_y = minElementY + buffer.get();
                int min_x = minElementX + buffer.get();
                int max_x = minElementX + buffer.get();
//                System.out.println("New Element : (" + min_y + " -> " + max_y + "), (" + min_x + " -> " + max_x + ")");

                for (int index_y = min_y; index_y < max_y; index_y++) {
                    for (int index_x = min_x; index_x < max_x; index_x++) {
                        int nbHeight = buffer.get();
                        for (int index_height = 0; index_height < nbHeight; index_height++) {
                            short tplg_height = buffer.getShort();
                            byte gfx_height = buffer.get();
                            byte elementType = buffer.get();
                            byte flags = buffer.get();
                            int elementId = buffer.getInt();

                            short indexGroup = buffer.getShort();
                            short indexColor = buffer.getShort();

                            if (elementType == 0) { // ground
//                            System.out.println("Element ID : " + elementId + ", z : " + tplg_height + ", height : " + gfx_height + ", flags : " + flags + ", grp : " + indexGroup);
                                gfxsId.put(getIdFromWakfuCoords(index_x, index_y, tplg_height), elementId);
                            }
                        }
                    }
                }
            }
        }

        return gfxsId;
    }

    private static long getIdFromWakfuCoords(final int x, final int y, final short height) {
        if (x == -397 && y == 126) {
            System.out.println("h : " + height);
        }
        return (((long) x) << 32) + (((long) y) << 16) + height;
    }

    private static void decodeTplgFile(final Entity entity, final World world, final ZipEntry fileEntry, final int mapX, final int mapY) {
//        if (!(mapX == 29 && mapY == -4)) {
//            return;
//        }

//        log(entity, "Decode file map (" + mapX + ", " + mapY + ")");

        try {
            InputStream fileIS = tplgMapJar.getInputStream(fileEntry);
            byte bufferTmp[] = new byte[3000];
            int size = fileIS.read(bufferTmp);
            fileIS.close();
            ByteBuffer buffer = ByteBuffer.wrap(bufferTmp);
            buffer.order(ByteOrder.LITTLE_ENDIAN);

            generateMap(buffer, entity, world, mapX, mapY);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private static void setFullBlock(final World world, final int x, final int y, final int z) {
        long coordId = getIdFromWakfuCoords((short) x, (short) z, (short) (y * 4 - wakfuOffsetY));
        Integer elementId = gfxsId.get(coordId);
        setBlock(world, x, y, z, (elementId != null && elementId == 33451) ? WBlocks.grass : WBlocks.debug, 0, 0);
    }

    private static void setSlabBlock(final World world, final int x, final int y, final int z, final int metadata) {
        long coordId = getIdFromWakfuCoords((short) x, (short) z, (short) (y * 4 - wakfuOffsetY + (metadata >> 2)));
        Integer elementId = gfxsId.get(coordId);
        setBlock(world, x, y, z, (elementId != null && elementId == 33451) ? WBlocks.grassSlab : WBlocks.debugSlab, metadata, 0);
    }

    private static void generateBaseColumn(final World world, final int x, final int y, final int z, final int blockHeight) {
        if (y < 5) { // It's really deep, so it's probably a tile that is not visible in 2D
            return;
        }

        if (blockHeight == 3) {
            setFullBlock(world, x, y, z);
        } else {
            setSlabBlock(world, x, y, z, blockHeight << 2);
        }

        for (int y2 = y + 1; y2 <= MC_OFFSET_Y - 1; y2++) {
            setAirBlock(world, x, y2, z);
        }

        for (int y2 = y - 1; y2 >= MC_OFFSET_Y - 3; y2--) {
            setFullBlock(world, x, y2, z);
        }
    }

    private static int getBlockYFromWakfuY(final int height) {
        return (wakfuOffsetY + height) / 4;
//        final int mcBlockY = (wakfuOffsetY + height) / 4;
//        return (height != -3) ? (height != -2) ? (height != -1) ? mcBlockY : MC_OFFSET_Y - 2 : MC_OFFSET_Y - 3 : MC_OFFSET_Y - 4;
    }

    private static int getBlockHFromWakfuY(final int height) {
        return (wakfuOffsetY + height) % 4;
//        final int mcBlockH = (wakfuOffsetY + height) % 4;
//        return (height != -3) ? (height != -2) ? (height != -1) ? mcBlockH : 3 : 3 : 3;
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

        log(entity, "Flat map, PosX : " + mapX + ", PosY : " + mapY + ", Default height : " + defaultHeight);

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

        log(entity, "Flat map (2), PosX : " + mapX + ", PosY : " + mapY + ", Default height : " + defaultHeight);

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

        log(entity, "Flat map (3), PosX : " + mapX + ", PosY : " + mapY + ", Default height : " + defaultHeight);

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

        log(entity, "Simple map, PosX : " + mapX + ", PosY : " + mapY);

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

        log(entity, "Simple map (2), PosX : " + mapX + ", PosY : " + mapY);

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

        log(entity, "Complex map, PosX : " + mapX + ", PosY : " + mapY);

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

            setSlabBlock(world, mapX * MAP_W + x, (wakfuOffsetY + height) / 4, mapY * MAP_H + y, (wakfuOffsetY + height) % 4);
        }
    }
}
