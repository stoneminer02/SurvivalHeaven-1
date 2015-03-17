/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.uendeligdropper;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.LivingEntity;

/**
 * The Class Util.
 */
@SuppressWarnings("deprecation")
public class Util {

	/**
	 * Gets the block target.
	 *
	 * @param entity
	 *            the entity
	 * @return the block target
	 */
	public static Block getBlockTarget(final LivingEntity entity) {
		final Block block = entity.getTargetBlock(STANDING_MATERIALS_TARGET,
				300);
		return block;
	}

	/**
	 * Gets the sign.
	 *
	 * @param block
	 *            the block
	 * @return the sign
	 */
	public static boolean getSign(final Block block) {
		if (block.getRelative(BlockFace.EAST).getType() == Material.WALL_SIGN) {
			final Sign sign = (Sign) block.getRelative(BlockFace.EAST)
					.getState();
			if (sign.getLines()[0].equalsIgnoreCase("[infdisp]"))
				return true;
		}
		if (block.getRelative(BlockFace.WEST).getType() == Material.WALL_SIGN) {
			final Sign sign = (Sign) block.getRelative(BlockFace.WEST)
					.getState();
			if (sign.getLines()[0].equalsIgnoreCase("[infdisp]"))
				return true;
		}
		if (block.getRelative(BlockFace.NORTH).getType() == Material.WALL_SIGN) {
			final Sign sign = (Sign) block.getRelative(BlockFace.NORTH)
					.getState();
			if (sign.getLines()[0].equalsIgnoreCase("[infdisp]"))
				return true;
		}
		if (block.getRelative(BlockFace.SOUTH).getType() == Material.WALL_SIGN) {
			final Sign sign = (Sign) block.getRelative(BlockFace.SOUTH)
					.getState();
			if (sign.getLines()[0].equalsIgnoreCase("[infdisp]"))
				return true;
		}
		return false;
	}

	/**
	 * Gets the target.
	 *
	 * @param entity
	 *            the entity
	 * @return the target
	 */
	public static Location getTarget(final LivingEntity entity) {
		final Block block = entity.getTargetBlock(STANDING_MATERIALS_TARGET,
				300);
		return block.getLocation();
	}

	/** The Constant STANDING_MATERIALS. */
	private static final Set<Integer> STANDING_MATERIALS = new HashSet<Integer>();

	/** The Constant STANDING_MATERIALS_TARGET. */
	private static final HashSet<Byte> STANDING_MATERIALS_TARGET = new HashSet<Byte>();

	static {
		STANDING_MATERIALS.add(Integer.valueOf(Material.AIR.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.SAPLING.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.POWERED_RAIL.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.DETECTOR_RAIL.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.LONG_GRASS.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.DEAD_BUSH.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.YELLOW_FLOWER.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.RED_ROSE.getId()));
		STANDING_MATERIALS
				.add(Integer.valueOf(Material.BROWN_MUSHROOM.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.RED_MUSHROOM.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.TORCH.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.REDSTONE_WIRE.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.SEEDS.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.SIGN_POST.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.WOODEN_DOOR.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.LADDER.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.RAILS.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.WALL_SIGN.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.LEVER.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.STONE_PLATE.getId()));
		STANDING_MATERIALS
				.add(Integer.valueOf(Material.IRON_DOOR_BLOCK.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.WOOD_PLATE.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.REDSTONE_TORCH_OFF
				.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.REDSTONE_TORCH_ON
				.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.STONE_BUTTON.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.SNOW.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.SUGAR_CANE_BLOCK
				.getId()));
		STANDING_MATERIALS
				.add(Integer.valueOf(Material.DIODE_BLOCK_OFF.getId()));
		STANDING_MATERIALS
				.add(Integer.valueOf(Material.DIODE_BLOCK_ON.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.TRAP_DOOR.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.PUMPKIN_STEM.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.MELON_STEM.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.VINE.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.FENCE_GATE.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.WATER_LILY.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.NETHER_FENCE.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.NETHER_WARTS.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.TRIPWIRE_HOOK.getId()));
		STANDING_MATERIALS.add(Integer.valueOf(Material.TRIPWIRE.getId()));
		for (final Integer integer : STANDING_MATERIALS) {
			STANDING_MATERIALS_TARGET.add(Byte.valueOf(integer.byteValue()));
		}
		STANDING_MATERIALS_TARGET.add(Byte.valueOf((byte) Material.WATER
				.getId()));
		STANDING_MATERIALS_TARGET.add(Byte
				.valueOf((byte) Material.STATIONARY_WATER.getId()));
	}
}
