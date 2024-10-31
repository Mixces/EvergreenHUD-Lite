package org.polyfrost.evergreenhud

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import org.polyfrost.evergreenhud.config.ModConfig

@Mod(modid = EvergreenHUD.MODID, name = EvergreenHUD.NAME, version = EvergreenHUD.VERSION)
class EvergreenHUD {

    @Mod.EventHandler
    fun onFMLInitialization(event: FMLInitializationEvent) {
        ModConfig.initialize()
    }

    companion object {
        const val MODID = "@ID@"
        const val NAME = "@NAME@"
        const val VERSION = "@VER@"
    }
}
