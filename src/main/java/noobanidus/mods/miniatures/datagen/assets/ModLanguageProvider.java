package noobanidus.mods.miniatures.datagen.assets;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import noobanidus.mods.miniatures.Miniatures;
import noobanidus.mods.miniatures.init.ModBlocks;
import noobanidus.mods.miniatures.init.ModEntities;

public class ModLanguageProvider extends LanguageProvider {
  private final String locale;

  public ModLanguageProvider(PackOutput output, String locale) {
    super(output, Miniatures.MODID, locale);
    this.locale = locale;
  }

  @Override
  protected void addTranslations() {
    addBlock(ModBlocks.SENSOR_TORCH_BLOCK, "Sensor Torch");
    addEntityType(ModEntities.MAXIME, "Maxime");
    addEntityType(ModEntities.ME, "Me");
    addEntityType(ModEntities.MINIME, "Minime");

    add("miniatures.networking.client_validate.failed", "Failed to validate client data: %s");
  }

  // Generate upside-down if the locale is en_ud
  @Override
  public void add(String key, String value) {
    if (locale.equalsIgnoreCase("en_ud"))
      super.add(key, toUpsideDown(value));
    else
      super.add(key, value);
  }

  private static final String NORMAL_CHARS =
          /* lowercase */ "abcdefghijklmn\u00F1opqrstuvwxyz" +
          /* uppercase */ "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
          /*  numbers  */ "0123456789" +
          /*  special  */ "_,;.?!/\\'";
  private static final String UPSIDE_DOWN_CHARS =
          /* lowercase */ "\u0250q\u0254p\u01DD\u025Fb\u0265\u0131\u0638\u029E\u05DF\u026Fuuodb\u0279s\u0287n\u028C\u028Dx\u028Ez" +
          /* uppercase */ "\u2C6F\u15FA\u0186\u15E1\u018E\u2132\u2141HI\u017F\u029E\uA780WNO\u0500\u1F49\u1D1AS\u27D8\u2229\u039BMX\u028EZ" +
          /*  numbers  */ "0\u0196\u1105\u0190\u3123\u03DB9\u312586" +
          /*  special  */ "\u203E'\u061B\u02D9\u00BF\u00A1/\\,";

  private String toUpsideDown(String normal) {
    char[] ud = new char[normal.length()];
    for (int i = 0; i < normal.length(); i++) {
      char c = normal.charAt(i);
      if (c == '%') {
        String fmtArg = "";
        while (Character.isDigit(c) || c == '%' || c == '$' || c == 's' || c == 'd') { // TODO this is a bit lazy
          fmtArg += c;
          i++;
          c = i == normal.length() ? 0 : normal.charAt(i);
        }
        i--;
        for (int j = 0; j < fmtArg.length(); j++) {
          ud[normal.length() - 1 - i + j] = fmtArg.charAt(j);
        }
        continue;
      }
      int lookup = NORMAL_CHARS.indexOf(c);
      if (lookup >= 0) {
        c = UPSIDE_DOWN_CHARS.charAt(lookup);
      }
      ud[normal.length() - 1 - i] = c;
    }
    return new String(ud);
  }
}
