package noobanidus.mods.miniatures.setup;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientInit {
  public static void init() {
    IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
    modBus.addListener(ClientSetup::init);
    modBus.addListener(ClientSetup::registerEntityRenders);
    modBus.addListener(ClientSetup::registerLayerDefinitions);
  }
}
