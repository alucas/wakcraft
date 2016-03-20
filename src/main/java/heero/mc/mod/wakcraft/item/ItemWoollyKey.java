package heero.mc.mod.wakcraft.item;

import heero.mc.mod.wakcraft.WBlocks;
import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
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
    public final static int OFFSET_Y = 51 * 4;

    public boolean isCreatingMap = false;
    public boolean isPause = false;
    public File[] files;
    public int fileIndex;

    public ItemWoollyKey(final int level) {
        super(level);

        setCreativeTab(WCreativeTabs.tabResource);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (world.isRemote) {
            return itemStack;
        }

        log(player, "Start mapping");

        if (!isCreatingMap) {
            File folder = new File(world.getSaveHandler().getWorldDirectory().getAbsolutePath() + "/map");
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

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {
        super.onUpdate(stack, world, entity, par4, par5);

        if (world.isRemote || !isCreatingMap || isPause) {
            return;
        }

        if (fileIndex >= files.length) {
            isCreatingMap = false;

            System.out.println("");
            log(entity, "Generation completed");
            return;
        }

        fileIndex++;
        File file = files[fileIndex];
        String[] coord = file.getName().split("_");
        if (coord.length != 2) {
            log(entity, "Wrong file name : " + file.getName());
            return;
        }

        Integer mapY = new Integer(coord[0]);
        Integer mapX = new Integer(coord[1]);

        log(entity, "Create map (" + mapX + ", " + mapY + ") File n°" + fileIndex + " / " + files.length);

        try {
            FileInputStream fileIS = new FileInputStream(file);
            byte bufferTmp[] = new byte[3000];
            fileIS.read(bufferTmp);
            fileIS.close();
            ByteBuffer buffer = ByteBuffer.wrap(bufferTmp);
            buffer.order(ByteOrder.LITTLE_ENDIAN);

            byte encodingType = buffer.get();
            log(entity, "File encoding : " + encodingType);
            switch (encodingType) {
                case 5:
                    decode5(buffer, entity, world, mapX, mapY);
                    break;
                case 4:
                    decode4(buffer, entity, world, mapX, mapY);
                    break;
                case 0:
                    decode0(buffer, entity, world, mapX, mapY);
                    break;
                default:
                    log(entity, "File encoding " + encodingType + " not supported yet!!!!!!!!!!!!");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
                isCreatingMap = false;
                log(entity, "Generation abort");
            }
        }

        // Pause every 10 files
        if (fileIndex % 100 == 0) {
            log(entity, "Generation pause");
            isPause = true;
        }
    }

    public static int getBitRequired(int nbTilesPlus1) {
        if (nbTilesPlus1 <= 0) {
            throw new AssertionError();
        }

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

    public static void decode0(final ByteBuffer buffer, final Entity entity, final World world, final int mapX, final int mapY) {
        short posY = buffer.getShort();
        short posX = buffer.getShort();
        short defaultHeight = buffer.getShort();

        log(entity, "PosX : " + posX + ", PosY : " + posY + ", Default height : " + defaultHeight);

        // create map
        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 18; j++) {
                if (((defaultHeight + OFFSET_Y) % 4) == 3) {
                    setBlock(world, -(mapX * 18 + i), (OFFSET_Y + defaultHeight) / 4, mapY * 18 + j, WBlocks.debug, 0, 0);
                } else {
                    setBlock(world, -(mapX * 18 + i), (OFFSET_Y + defaultHeight) / 4, mapY * 18 + j, WBlocks.debugSlab, ((defaultHeight + OFFSET_Y) % 4) << 2, 0);
                }

                for (int k = defaultHeight - 4; k > -40; k -= 4) {
                    setBlock(world, -(mapX * 18 + i), (OFFSET_Y + k) / 4, mapY * 18 + j, WBlocks.debug, 0, 0);
                }
            }
        }
    }

    public static void decode4(final ByteBuffer buffer, final Entity entity, final World world, final int mapX, final int mapY) {
        short posY = buffer.getShort();
        short posX = buffer.getShort();
        short defaultHeight = buffer.getShort();

        log(entity, "PosX : " + posX + ", PosY : " + posY + ", Default height : " + defaultHeight);

        byte[] unknowArray1 = new byte[100];
        buffer.get(unknowArray1, 0, 41);

        log(entity, "Skip unknow array");

        byte nbTilesType = buffer.get();

        log(entity, "Nb tiles type : " + nbTilesType);

        byte[] fHy = new byte[nbTilesType];
        byte[] fHz = new byte[nbTilesType];
        byte[] fHA = new byte[nbTilesType];
        short[] tilesHeight = new short[nbTilesType];
        byte[] fHC = new byte[nbTilesType];
        byte[] fHD = new byte[nbTilesType];

        for (int j = 0; j < nbTilesType; j++) {
            fHy[j] = buffer.get();
            fHz[j] = buffer.get();
            fHA[j] = buffer.get();
            tilesHeight[j] = buffer.getShort();
            fHC[j] = buffer.get();
            fHD[j] = buffer.get();
        }

        int nbTiles = buffer.get();

        log(entity, "Nb tiles : " + nbTiles);

        long[] tilesArray = new long[nbTiles];
        for (int i = 0; i < nbTiles; i++) {
            tilesArray[i] = buffer.getLong();
        }

        // create map
        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 18; j++) {
                int tileType = getTileType(tilesArray, i * 18 + j, nbTilesType);
                int height = tilesHeight[tileType];
                if (height > -180 && height < 180) {
                    if (((height + OFFSET_Y) % 4) == 3) {
                        setBlock(world, -(mapX * 18 + i), (OFFSET_Y + height) / 4, mapY * 18 + j, WBlocks.debug, 0, 0);
                    } else {
                        setBlock(world, -(mapX * 18 + i), (OFFSET_Y + height) / 4, mapY * 18 + j, WBlocks.debugSlab, ((height + OFFSET_Y) % 4) << 2, 0);
                    }

                    for (int k = height - 4; k > -40; k -= 4) {
                        setBlock(world, -(mapX * 18 + i), (OFFSET_Y + k) / 4, mapY * 18 + j, WBlocks.debug, 0, 0);
                    }
                }
            }
        }
    }

    public static void decode5(final ByteBuffer buffer, final Entity entity, final World world, final int mapX, final int mapY) {
        short posY = buffer.getShort();
        short posX = buffer.getShort();
        short defaultHeight = buffer.getShort();

        log(entity, "PosX : " + posX + ", PosY : " + posY + ", Default height : " + defaultHeight);

        byte[] unknowArray1 = new byte[100];
        buffer.get(unknowArray1, 0, 41);

        log(entity, "Skip unknow array");

        byte nbTilesType = buffer.get();

        log(entity, "Nb tiles type : " + nbTilesType);

        byte[] fHy = new byte[nbTilesType];
        byte[] fHz = new byte[nbTilesType];
        byte[] fHA = new byte[nbTilesType];
        short[] tilesHeight = new short[nbTilesType];
        byte[] fHC = new byte[nbTilesType];
        byte[] fHD = new byte[nbTilesType];

        for (int j = 0; j < nbTilesType; j++) {
            fHy[j] = buffer.get();
            fHz[j] = buffer.get();
            fHA[j] = buffer.get();
            tilesHeight[j] = buffer.getShort();
            fHC[j] = buffer.get();
            fHD[j] = buffer.get();
        }

        int nbTiles = buffer.get();

        log(entity, "Nb tiles : " + nbTiles);

        long[] tilesArray = new long[nbTiles];
        for (int i = 0; i < nbTiles; i++) {
            tilesArray[i] = buffer.getLong();
        }

        int nbIntegers = buffer.getShort();

        int[] integerArray = new int[nbIntegers];
        for (int i = 0; i < nbIntegers; i++) {
            integerArray[i] = buffer.getInt();
        }

        // create map
        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 18; j++) {
                int tileType = getTileType(tilesArray, i * 18 + j, nbTilesType + 1);
                if (tileType != 0) {
                    int height = tilesHeight[tileType - 1];
                    if (height > -180 && height < 180) {
                        if (((height + OFFSET_Y) % 4) == 3) {
                            setBlock(world, -(mapX * 18 + i), (OFFSET_Y + height) / 4, mapY * 18 + j, WBlocks.debug, 0, 0);
                        } else {
                            setBlock(world, -(mapX * 18 + i), (OFFSET_Y + height) / 4, mapY * 18 + j, WBlocks.debugSlab, ((height + OFFSET_Y) % 4) << 2, 0);
                        }

                        for (int k = height - 4; k > -40; k -= 4) {
                            setBlock(world, -(mapX * 18 + i), (OFFSET_Y + k) / 4, mapY * 18 + j, WBlocks.debug, 0, 0);
                        }
                    }
                }
            }
        }

        // special tiles
        for (int k = 0; k < integerArray.length; k++) {
            int y = (integerArray[k] >> 0) & 0xFF;
            int x = (integerArray[k] >> 8) & 0xFF;
            int tileType = (integerArray[k] >> 16) & 0xFFFF;

            int height = tilesHeight[tileType];
            if (height > -180 && height < 180) {
                setBlock(world, -(mapX * 18 + x), (OFFSET_Y + height) / 4, mapY * 18 + y, WBlocks.debugSlab, (height + OFFSET_Y) % 4, 0);
            }
        }
    }

    public static void setBlock(final World world, final int x, final int y, final int z, final Block block, final int meta, final int updateFlags) {
        world.setBlockState(new BlockPos(x, y, z), block.getStateFromMeta(meta), updateFlags);
    }

    public static void log(final Entity player, final String message) {
//        System.out.println(message);
        player.addChatMessage(new ChatComponentText(message));
    }
}