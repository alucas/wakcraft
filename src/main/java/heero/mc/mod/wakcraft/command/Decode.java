package heero.mc.mod.wakcraft.command;

import net.minecraft.entity.Entity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Decode {
    protected static final String BASE_FOLDER = "wakfu_map";
    protected static final String BLOCK_FILE_NAME = "block.properties";
    protected static final String DATA_JAR_NAME = "data.jar";
    protected static final String ELEMENTS_LIB_NAME = "elements.lib";
    protected static final String GFX_FOLDER_NAME = "gfx";
    protected static final int ELEMENT_TYPE_GROUND = 0;

    protected static void log(final Entity player, final String message) {
        player.addChatMessage(new ChatComponentText(message));
    }

    public static long getIdFromWakfuCoords(final int x, final int y, final short height) {
        return (((long) x) << 32) + (((long) y) << 16) + height;
    }

    protected static File getBaseFolder(final Entity player, final World world) {
        final String baseFolderName = world.getSaveHandler().getWorldDirectory().getParentFile().getAbsolutePath() + "/" + BASE_FOLDER;

        final File baseFolder = new File(baseFolderName);
        if (!baseFolder.exists()) {
            log(player, baseFolderName + " not found");

            return null;
        }

        return baseFolder;
    }

    public static Properties decodeBlockFile(final Entity player, final File baseFolder) {
        log(player, "Decode Block file");

        final String blockFileName = baseFolder.getAbsolutePath() + "/" + BLOCK_FILE_NAME;

        final File blockFile = new File(blockFileName);
        if (!blockFile.exists()) {
            log(player, blockFileName + " not found");

            return null;
        }

        final Properties blocksProperties = new Properties();

        InputStream input = null;
        try {
            input = new FileInputStream(blockFile);

            blocksProperties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            log(player, "Exception while reading the block file");

            return null;
        } finally {
            IOUtils.closeQuietly(input);
        }

        return blocksProperties;
    }

    public static Map<Integer, Integer> decodeElementsLib(final Entity player, final File baseFolder) {
        log(player, "Decode Elements");

        final String dataJarName = baseFolder.getAbsolutePath() + "/" + DATA_JAR_NAME;

        final Map<Integer, Integer> elementIdtoGfxId = new HashMap<>();
        JarFile dataJar = null;
        InputStream fileIS = null;
        ByteBuffer buffer = null;
        try {
            dataJar = new JarFile(dataJarName);

            final JarEntry elementsEntry = dataJar.getJarEntry(ELEMENTS_LIB_NAME);
            if (elementsEntry == null) {
                log(player, ELEMENTS_LIB_NAME + " not found in " + dataJarName);

                return null;
            }

            fileIS = dataJar.getInputStream(elementsEntry);
            buffer = ByteBuffer.wrap(IOUtils.toByteArray(fileIS));
            buffer.order(ByteOrder.LITTLE_ENDIAN);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log(player, dataJarName + " not found");

            return null;
        } catch (IOException e) {
            e.printStackTrace();
            log(player, "Error");

            return null;
        } finally {
            IOUtils.closeQuietly(fileIS);
            IOUtils.closeQuietly(dataJar);
        }

        int nb_element = buffer.getInt();

        System.out.println("Nb elements : " + nb_element);

        for (int i = 0; i < nb_element; i++) {
            int element_id = buffer.getInt();
            buffer.getShort();
            buffer.getShort();
            buffer.getShort();
            buffer.getShort();
            int gfx_id = buffer.getInt();
            buffer.get();
            buffer.get();
            buffer.get();
            buffer.get();
            buffer.get();
            int nb_image = buffer.get();
            if (nb_image != 0) {
                buffer.getInt(); // duration
                buffer.getShort();
                buffer.getShort();
                buffer.getShort();
                buffer.getShort();

                for (int j = 0; j < nb_image; j++) {
                    buffer.getShort();
                }

                for (int j = 0; j < nb_image; j++) {
                    buffer.getShort();
                    buffer.getShort();
                }
            }

            buffer.get();

            elementIdtoGfxId.put(element_id, gfx_id);
        }

        return elementIdtoGfxId;
    }

    public static Map<Long, Integer> decodeGfxFiles(final Entity player, final Map<Integer, Integer> elementIdToGfxId, final Properties blocks, final File baseFolder, final int mapId, final Integer printX, final Integer printZ) {
        log(player, "Decode gfx");

        final String gfxFolderName = baseFolder.getAbsolutePath() + "/" + GFX_FOLDER_NAME;
        final File gfxFolder = new File(gfxFolderName);
        if (!gfxFolder.exists()) {
            log(player, gfxFolder.getAbsolutePath() + " not found");

            return null;
        }

        final Map<Long, Integer> gfxsId = new HashMap<>();

        JarFile gfxMapJar = null;
        try {
            gfxMapJar = new JarFile(gfxFolder.getAbsolutePath() + "/" + mapId + ".jar");


            final Enumeration<JarEntry> entries = gfxMapJar.entries();
            while (entries.hasMoreElements()) {
                final JarEntry entry = entries.nextElement();
                final String fileName = entry.getName();

                final String[] coords = fileName.split("_");
                if (coords.length != 2) {
                    System.out.println("File name incorrect : " + fileName);

                    continue;
                }

//            System.out.println("Decode file : " + entry.getName());

                final InputStream fileIS = gfxMapJar.getInputStream(entry);
                final ByteBuffer buffer = ByteBuffer.wrap(IOUtils.toByteArray(fileIS));
                buffer.order(ByteOrder.LITTLE_ENDIAN);
                IOUtils.closeQuietly(fileIS);

                buffer.getInt();
                buffer.getInt();
                buffer.getShort();

                buffer.getInt();
                buffer.getInt();
                buffer.getShort();

                final int nbGroup = buffer.getShort();
                for (int j = 0; j < nbGroup; j++) {
                    buffer.getInt(); // var1
                    buffer.get(); // var2
                    buffer.getInt(); // var2
//                System.out.println("grp " + j + " : " + var1 + ", " + var2 + ", " + var3);
                }

                final int nbColor = buffer.getShort();
                for (int j = 0; j < nbColor; j++) {
                    final int colorType = buffer.get();

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

                final int minElementY = buffer.getInt();
                final int minElementX = buffer.getInt();
//            System.out.println("min element y : " + minElementY);
//            System.out.println("min element x : " + minElementX);

                final int nbElement = buffer.getShort();
//            System.out.println("nb element : " + nbElement);
                for (int i1 = 0; i1 < nbElement; i1++) {
                    final int min_y = minElementY + buffer.get();
                    final int max_y = minElementY + buffer.get();
                    final int min_x = minElementX + buffer.get();
                    final int max_x = minElementX + buffer.get();
//                System.out.println("New Element : (" + min_y + " -> " + max_y + "), (" + min_x + " -> " + max_x + ")");

                    for (int index_y = min_y; index_y < max_y; index_y++) {
                        for (int index_x = min_x; index_x < max_x; index_x++) {
                            final int nbHeight = buffer.get();
                            for (int index_height = 0; index_height < nbHeight; index_height++) {
                                final short tplg_height = buffer.getShort();
                                byte gfx_height = buffer.get();
                                final byte elementType = buffer.get();
                                buffer.get(); // flags
                                final int elementId = buffer.getInt();

                                buffer.getShort(); // indexGroup
                                buffer.getShort(); // indexColor

                                Integer gfxId = elementIdToGfxId.get(elementId);
                                if (gfxId == null) {
                                    System.out.println("Unknown gfx for element id : " + elementId);

                                    continue;
                                }

                                if (printX != null && printZ != null && index_x == -printX && index_y == printZ) {
                                    log(player, "x : " + printX + ", z = " + printZ + ", tplg_h : " + tplg_height + ", gfx_h : " + gfx_height + ", gfxId : " + gfxId + ", type : " + elementType);
                                }

                                String blockName = blocks.getProperty(gfxId.toString());
                                if (elementType == ELEMENT_TYPE_GROUND || blockName != null) {
                                    gfxsId.put(getIdFromWakfuCoords(index_x, index_y, tplg_height), gfxId);
                                }
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log(player, "Invalid map id : " + mapId);

            return null;
        } catch (IOException e) {
            e.printStackTrace();
            log(player, "Error");

            return null;
        } finally {
            IOUtils.closeQuietly(gfxMapJar);
        }

        return gfxsId;
    }
}
