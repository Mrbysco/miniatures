package noobanidus.mods.miniatures.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import noobanidus.mods.miniatures.Miniatures;
import noobanidus.mods.miniatures.entity.MiniMeEntity;
import noobanidus.mods.miniatures.init.ModEntities;

@Mod.EventBusSubscriber(modid = Miniatures.MODID)
public class EntityEvents {
  private static boolean isMiniature(Entity entity) {
    EntityType type = entity.getType();
    return type == ModEntities.MINIME.get() || type == ModEntities.MAXIME.get() || type == ModEntities.ME.get();
  }

  @SubscribeEvent
  public static void onSizeChange(EntityEvent.Size event) {
    if (event.getEntity().isAddedToWorld()) {
      if (isMiniature(event.getEntity())) {
        MiniMeEntity mini = (MiniMeEntity) event.getEntity();
        EntitySize oldSize = event.getOldSize();
        event.setNewSize(EntitySize.scalable(oldSize.width * mini.getScale(), oldSize.height * mini.getScale()));
        event.setNewEyeHeight(event.getOldEyeHeight() * mini.getScale());
      }
    }
  }

  @SubscribeEvent
  public static void onEntityDismount(EntityMountEvent event) {
    if (event.isDismounting()) {
      Entity mounted = event.getEntityBeingMounted();
      if (isMiniature(event.getEntityBeingMounted())) {
        MiniMeEntity mini = (MiniMeEntity) mounted;
        mini.setPickupCooldown(mini.level.random.nextInt(800) + 600);
      }
    }
  }
}
