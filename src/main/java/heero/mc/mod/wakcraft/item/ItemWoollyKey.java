package heero.mc.mod.wakcraft.item;

import heero.mc.mod.wakcraft.WBlocks;
import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ItemWoollyKey extends ItemWithLevel {
    public static final int MC_OFFSET_Y = 51;
    public static final int OFFSET_Y = MC_OFFSET_Y * 4;
    public static final int MAP_W = 18;
    public static final int MAP_H = 18;

    public boolean isCreatingMap = false;
    public boolean isPause = false;
    public File[] files;
    public int fileIndex;

    public ItemWoollyKey(final int level) {
        super(level);

        setCreativeTab(WCreativeTabs.tabResource);
    }

    public static void log(final Entity player, final String message) {
//        System.out.println(message);
        player.addChatMessage(new ChatComponentText(message));
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (world.isRemote) {
            return itemStack;
        }

        log(player, "Start mapping");

        if (!isCreatingMap) {
            File folder = new File(world.getSaveHandler().getWorldDirectory().getParentFile().getAbsolutePath() + "/wakfu_map");
            if (!folder.exists()) {
                log(player, "no '" + folder.getAbsolutePath() + "' folder");
                return itemStack;
            }

            isCreatingMap = true;
            files = folder.listFiles();
            fileIndex = 0;
        }

        isPause = false;

        return itemStack;
    }

    /*
    Nous utilisons ici les fichier de topologie de Wakfu, ces fichier contiennent entre autres les information
    sur la hauteur heuteur/position de chaque block. Chacun de ces fichier represente une map de 18x18
    */
    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {
        super.onUpdate(stack, world, entity, par4, par5);

        if (world.isRemote || !isCreatingMap || isPause) {
            return;
        }

        if (fileIndex >= files.length) {
            isCreatingMap = false;

            log(entity, "Generation completed");
            return;
        }

        decodeNextFile(files, fileIndex++, entity, world);

        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
                isCreatingMap = false;
                log(entity, "Generation abort");
            }
        }

        // Pause every X files
//        if (mapFilesIndex % 100 == 0) {
//            log(entity, "Generation pause");
//            isPause = true;
//        }
    }

    public static void decodeNextFile(final File[] files, final int fileIndex, final Entity entity, final World world) {
        File file = files[fileIndex];
        String[] coord = file.getName().split("_");
        if (coord.length != 2) {
            log(entity, "Incorrect file name : " + file.getName());
            return;
        }

        Integer mapY = new Integer(coord[0]);
        Integer mapX = new Integer(coord[1]);

//        if (!(mapX == 29 && mapY == -4)) {
//            return;
//        }

        log(entity, "Decode file map (" + mapX + ", " + mapY + "), n°" + fileIndex + " / " + files.length);

        try {
            FileInputStream fileIS = new FileInputStream(file);
            byte bufferTmp[] = new byte[3000];
            System.out.println("Size : " + fileIS.read(bufferTmp));
            fileIS.close();
            ByteBuffer buffer = ByteBuffer.wrap(bufferTmp);
            buffer.order(ByteOrder.LITTLE_ENDIAN);

            generateMap(buffer, entity, world, mapX, mapY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getBitRequired(int nbTilesPlus1) {
        int i = 1;
        int j = 0;
        while (nbTilesPlus1 > i) {
            i *= 2;
            j++;
        }

        return j;
    }

    public static int getTileType(long[] tilesArray, int tileIndex, int nbTilesPlus1) {
        int nbBitRequired = getBitRequired(nbTilesPlus1);
        int j = 64 / nbBitRequired;
        int mask = (1 << nbBitRequired) - 1;

        long l2 = tilesArray[(tileIndex / j)];
        l2 >>= nbBitRequired * (tileIndex % j);
        return (int) (l2 & mask);
    }

    public static void setBlock(final World world, final int x, final int y, final int z, final Block block, final int meta, final int updateFlags) {
        world.setBlockState(new BlockPos(-x, y, z), block.getStateFromMeta(meta), updateFlags);
    }

    public static void setAirBlock(final World world, final int x, final int y, final int z) {
        setBlock(world, x, y, z, Blocks.air, 0, 0);
    }

    public static void setFullBlock(final World world, final int x, final int y, final int z) {
        setBlock(world, x, y, z, WBlocks.debug, 0, 0);
    }

    public static void setSlabBlock(final World world, final int x, final int y, final int z, final int metadata) {
        setBlock(world, x, y, z, WBlocks.debugSlab, metadata, 0);
    }

    public static void generateBaseColumn(final World world, final int x, final int y, final int z, final int blockHeight) {
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

    public static int getBlockYFromWakfuY(final int height) {
        final int mcBlockY = (OFFSET_Y + height) / 4;
        return mcBlockY;
//        return (height != -3) ? (height != -2) ? (height != -1) ? mcBlockY : MC_OFFSET_Y - 2 : MC_OFFSET_Y - 3 : MC_OFFSET_Y - 4;
    }

    public static int getBlockHFromWakfuY(final int height) {
        final int mcBlockH = (height + OFFSET_Y) % 4;
        return mcBlockH;
//        return (height != -3) ? (height != -2) ? (height != -1) ? mcBlockH : 3 : 3 : 3;
    }


    public static void generateMap(final ByteBuffer buffer, final Entity entity, final World world, final int mapX, final int mapY) {
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

    public static boolean readAndControlCoords(final ByteBuffer buffer, final Entity entity, final int mapX, final int mapY) {
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
    public static void generateFlatMap(final ByteBuffer buffer, final Entity entity, final World world, final int mapX, final int mapY) {
        if (!readAndControlCoords(buffer, entity, mapX, mapY)) {
            return;
        }

        final short defaultHeight = buffer.getShort();

        byte var1 = buffer.get();
        byte var2 = buffer.get();
        byte vat3 = buffer.get();

        log(entity, "Flat map, PosX : " + mapX + ", PosY : " + mapY + ", Default height : " + defaultHeight);

        if (defaultHeight == -3) {
            return;
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
    All the map is at the same height value, but each block has extra information
     */
    public static void generateFlatMap2(final ByteBuffer buffer, final Entity entity, final World world, final int mapX, final int mapY) {
        if (!readAndControlCoords(buffer, entity, mapX, mapY)) {
            return;
        }

        final short defaultHeight = buffer.getShort();

        log(entity, "Flat map (2), PosX : " + mapX + ", PosY : " + mapY + ", Default height : " + defaultHeight);

        if (defaultHeight == -3) {
            return;
        }

        final byte[] unknowArray1 = new byte[100];
        buffer.get(unknowArray1, 0, ((MAP_H * MAP_W) + 7) >> 3);

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
    public static void generateFlatMap3(final ByteBuffer buffer, final Entity entity, final World world, final int mapX, final int mapY) {
        if (!readAndControlCoords(buffer, entity, mapX, mapY)) {
            return;
        }

        final short defaultHeight = buffer.getShort();

        log(entity, "Flat map (3), PosX : " + mapX + ", PosY : " + mapY + ", Default height : " + defaultHeight);

        if (defaultHeight == -3) {
            return;
        }

        final byte[] unknowArray1 = new byte[100];
        buffer.get(unknowArray1, 0, ((MAP_H * MAP_W) + 7) >> 3);

        final byte nbTilesType = buffer.get();
        log(entity, "Nb tiles type : " + nbTilesType);

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
    public static void generateSimpleMap(final ByteBuffer buffer, final Entity entity, final World world, final int mapX, final int mapY) {
        if (!readAndControlCoords(buffer, entity, mapX, mapY)) {
            return;
        }

        final short defaultHeight = buffer.getShort();

        log(entity, "Simple map, PosX : " + mapX + ", PosY : " + mapY);

        final byte[] unknowArray1 = new byte[100];
        buffer.get(unknowArray1, 0, ((MAP_H * MAP_W) + 7) >> 3);

        final byte nbTilesType = buffer.get();
        log(entity, "Nb tiles type : " + nbTilesType);

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
    public static void generateSimpleMap2(final ByteBuffer buffer, final Entity entity, final World world, final int mapX, final int mapY) {
        if (!readAndControlCoords(buffer, entity, mapX, mapY)) {
            return;
        }

        final short defaultHeight = buffer.getShort();

        log(entity, "Simple map (2), PosX : " + mapX + ", PosY : " + mapY);

        final byte[] unknowArray1 = new byte[100];
        buffer.get(unknowArray1, 0, ((MAP_H * MAP_W) + 7) >> 3);

        final byte nbTilesType = buffer.get();
        log(entity, "Nb tiles type : " + nbTilesType);

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
        log(entity, "Nb tiles : " + nbTiles);

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
    public static void generateComplexMap(final ByteBuffer buffer, final Entity entity, final World world, final int mapX, final int mapY) {
        if (!readAndControlCoords(buffer, entity, mapX, mapY)) {
            return;
        }

        final short defaultHeight = buffer.getShort();

        log(entity, "Complex map, PosX : " + mapX + ", PosY : " + mapY);

        final byte[] unknowArray1 = new byte[100];
        buffer.get(unknowArray1, 0, ((MAP_H * MAP_W) + 7) >> 3);

        final byte nbTilesType = buffer.get();
        log(entity, "Nb tiles type : " + nbTilesType);

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
        log(entity, "Nb tiles : " + nbTiles);

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
            if (height > -180 && height < 180) {
                setSlabBlock(world, mapX * MAP_W + x, (OFFSET_Y + height) / 4, mapY * MAP_H + y, (height + OFFSET_Y) % 4);
            }
        }
    }
}