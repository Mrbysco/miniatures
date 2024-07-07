package noobanidus.mods.miniatures.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import noobanidus.mods.miniatures.Miniatures;

import java.nio.file.Path;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Miniatures.MODID)
public class ConfigManager {
  private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

  public static final ForgeConfigSpec COMMON_CONFIG;

  private static final ForgeConfigSpec.DoubleValue MAX_HEALTH;
  private static final ForgeConfigSpec.DoubleValue MOVEMENT_SPEED;
  private static final ForgeConfigSpec.DoubleValue ATTACK_DAMAGE;
  private static final ForgeConfigSpec.DoubleValue ARMOR_VALUE;
  private static final ForgeConfigSpec.BooleanValue HOSTILE;
  private static final ForgeConfigSpec.BooleanValue IMMUNE;
  private static final ForgeConfigSpec.BooleanValue DESTROYS_BLOCKS;
  private static final ForgeConfigSpec.BooleanValue BREAKS_BLOCKS;
  private static final ForgeConfigSpec.BooleanValue PICKUP_GOAL;
  private static final ForgeConfigSpec.BooleanValue OWNER_RIDER;
  private static final ForgeConfigSpec.DoubleValue DISTRACTION_CHANCE;
  private static final ForgeConfigSpec.IntValue BASE_RUN_DELAY;
  private static final ForgeConfigSpec.IntValue RANDOM_RUN_DELAY;
  private static final ForgeConfigSpec.BooleanValue SKIP_NULL_CHECK;

  static {
    COMMON_BUILDER.comment("options relating to miniatures").push("miniatures");
    MAX_HEALTH = COMMON_BUILDER.comment("the maximum health of a miniature").defineInRange("max_health", 16, 0, Double.MAX_VALUE);
    MOVEMENT_SPEED = COMMON_BUILDER.comment("the base movement speed of a miniature").defineInRange("movement_speed", 0.3, 0, Double.MAX_VALUE);
    ATTACK_DAMAGE = COMMON_BUILDER.comment("if hostile, the amount of damage an attack from a miniature does [1 = 1 full heart, 0.5 = half a heart]").defineInRange("attack_damage", 2.0, 0, Double.MAX_VALUE);
    ARMOR_VALUE = COMMON_BUILDER.comment("how much armor miniatures should have").defineInRange("armor_value", 0, 0, Double.MAX_VALUE);
    HOSTILE = COMMON_BUILDER.comment("whether or not miniatures are hostile to players").define("hostile", false);
    IMMUNE = COMMON_BUILDER.comment("whether or not miniatures are immune to damage that does not originate from a player").define("non_player_immune", true);
    BREAKS_BLOCKS = COMMON_BUILDER.comment("whether or not the miniatures will break blocks in the default tag (miniatures:break_blocks)").define("breaks_blocks", true);
    DISTRACTION_CHANCE = COMMON_BUILDER.comment("the percentage chance per tick that a miniature will get distracted from breaking a block (0 for no distraction)").defineInRange("distraction_chance", 0.05, 0, Double.MAX_VALUE);
    BASE_RUN_DELAY = COMMON_BUILDER.comment("the minimum delay in ticks before a miniature begins running to a block (200)").defineInRange("base_run_delay", 200, 0, Integer.MAX_VALUE);
    RANDOM_RUN_DELAY = COMMON_BUILDER.comment("the maximum value (0 to value-1) added to the run delay (200)").defineInRange("random_run_delay", 200, 0, Integer.MAX_VALUE);
    DESTROYS_BLOCKS = COMMON_BUILDER.comment("whether blocks in the default tag (miniatures:break_blocks) will be destroyed (true) or instead dropped when broken (false)").define("destroys_blocks", false);
    PICKUP_GOAL = COMMON_BUILDER.comment("whether or not non-hostile miniatures will try to pick up players").define("pickup_goal", true);
    OWNER_RIDER = COMMON_BUILDER.comment("whether or not only the owner entity of the miniature will attempt to pick up a player, or whether it will pick up any player").define("owner_rider", false);
    SKIP_NULL_CHECK = COMMON_BUILDER.comment("whether or the null profile cache should be consulted; setting this to false may cause lag when miniatures with non-existent skins are spawned").define("skip_null_check", false);
    COMMON_BUILDER.pop();
    COMMON_CONFIG = COMMON_BUILDER.build();
  }

  public static void loadConfig(ForgeConfigSpec spec, Path path) {
    CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();
    configData.load();
    spec.setConfig(configData);
  }

  @SubscribeEvent
  public static void onConfigEvent(ModConfigEvent event) {
    //Miniatures.LOG.info("Config reload for [Miniatures]!");
    COMMON_CONFIG.setConfig(event.getConfig().getConfigData());
  }

  public static double getArmorValue() {
    return ARMOR_VALUE.get();
  }

  public static double getAttackDamage() {
    return ATTACK_DAMAGE.get();
  }

  public static double getMaxHealth() {
    return MAX_HEALTH.get();
  }

  public static double getMovementSpeed() {
    return MOVEMENT_SPEED.get();
  }

  public static boolean getHostile() {
    return HOSTILE.get();
  }

  public static boolean getImmune() {
    return IMMUNE.get();
  }

  public static boolean getDestroysBlocks() {
    return DESTROYS_BLOCKS.get();
  }

  public static boolean getBreaksBlocks() {
    return BREAKS_BLOCKS.get();
  }

  public static boolean getDoesPickup() {
    return PICKUP_GOAL.get();
  }

  public static boolean getOwnerRider() {
    return OWNER_RIDER.get();
  }

  public static int getRandomRunDelay() {
    return RANDOM_RUN_DELAY.get();
  }

  public static int getBaseRunDelay() {
    return BASE_RUN_DELAY.get();
  }

  public static double getDistractionValue() {
    return DISTRACTION_CHANCE.get();
  }

  public static boolean shouldSkipNullCheck() {
    return SKIP_NULL_CHECK.get();
  }
}
