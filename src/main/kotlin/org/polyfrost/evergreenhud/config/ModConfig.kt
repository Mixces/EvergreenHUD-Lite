package org.polyfrost.evergreenhud.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.SubConfig
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import org.polyfrost.evergreenhud.EvergreenHUD
import org.polyfrost.evergreenhud.hud.*

object ModConfig : Config(Mod(EvergreenHUD.NAME, ModType.HUD, "/assets/evergreenhud/evergreenhud.svg"), "${EvergreenHUD.MODID}.json") {

    @SubConfig var armour = Armour()
    @SubConfig var cps = CPS()
    @SubConfig var fps = FPS()
}
