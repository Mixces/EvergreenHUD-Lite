package org.polyfrost.evergreenhud.hud

import org.polyfrost.evergreenhud.utils.ServerPinger
import cc.polyfrost.oneconfig.config.annotations.*
import cc.polyfrost.oneconfig.config.data.*
import cc.polyfrost.oneconfig.hud.SingleTextHud
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack
import cc.polyfrost.oneconfig.utils.dsl.mc
import org.polyfrost.evergreenhud.config.HudConfig

class Ping: HudConfig(Mod("Ping", ModType.HUD), "evergreenhud/ping.json", false) {
    @HUD(name = "Main")
    var hud = PingHud()

    init {
        initialize()
    }

    class PingHud : SingleTextHud("Ping", true, 60, 70) {

        @Slider(
            name = "Ping Period",
            min = 20F,
            max = 120F
        )
        var interval = 20

        @Switch(
            name = "Show in Single Player"
        )
        var showInSinglePlayer = true

        private val ping = ServerPinger.createListener({ interval * 20 }) { mc.currentServerData }
        override fun draw(matrices: UMatrixStack?, x: Float, y: Float, scale: Float, example: Boolean) {
            if (mc.isSingleplayer && !showInSinglePlayer && !example) return
            super.draw(matrices, x, y, scale, example)
        }

        override fun getText(example: Boolean): String {
            return ping.ping?.toString() ?: "N/A"
        }

    }
}