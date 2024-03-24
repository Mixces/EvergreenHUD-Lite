package org.polyfrost.evergreenhud.hud

import org.polyfrost.evergreenhud.utils.decimalFormat
import cc.polyfrost.oneconfig.config.annotations.*
import cc.polyfrost.oneconfig.config.data.*
import cc.polyfrost.oneconfig.hud.SingleTextHud
import cc.polyfrost.oneconfig.utils.dsl.mc
import org.polyfrost.evergreenhud.config.HudConfig
import kotlin.math.sqrt

class Speed: HudConfig(Mod("Speed", ModType.HUD), "evergreenhud/speed.json", false) {
    @HUD(name = "Main")
    var hud = SpeedHud()

    init {
        initialize()
    }

    class SpeedHud: SingleTextHud("Speed", true, 0, 110) {

        @Switch(name = "Use X")
        var useX = true

        @Switch(name = "Use Y")
        var useY = true

        @Switch(name = "Use Z")
        var useZ = true

        @Dropdown(
            name = "Speed Unit",
            options = ["Meters per tick", "Meters per second", "Kilometers per hour", "Miles per hour"],
        )
        var speedUnit = 0

        @Slider(
            name = "Accuracy",
            min = 0F,
            max = 8F
        )
        var accuracy = 2

        @Switch(name = "Trailing Zeros")
        var trailingZeros = true

        @Switch(name = "Suffix")
        var suffix = true

        private fun convertSpeed(speed: Double): Double =
            when (speedUnit) {
                0 -> speed
                1 -> speed * 20
                2 -> speed * 3.6 * 20
                3 -> speed * 2.237 * 20
                else -> throw IllegalStateException()
            }

        private val Int.name: String
            get() {
                return when (this) {
                    0 -> "m/t"
                    1 -> "m/s"
                    2 -> "kph"
                    3 -> "mph"
                    else -> throw IllegalStateException()
                }
            }

        override fun getText(example: Boolean): String {
            var speed = 0.0

            if (mc.thePlayer != null) {
                val dx = if (useX) mc.thePlayer!!.posX - mc.thePlayer!!.prevPosX else 0.0
                val dy = if (useY) mc.thePlayer!!.posY - mc.thePlayer!!.prevPosY else 0.0
                val dz = if (useZ) mc.thePlayer!!.posZ - mc.thePlayer!!.prevPosZ else 0.0

                // I usually don't leave out whitespaces, but in this case it greatly improved readability
                speed = convertSpeed(sqrt(dx*dx + dy*dy + dz*dz))
            }

            var formattedSpeed = decimalFormat(accuracy, trailingZeros).format(speed)

            if (suffix) formattedSpeed += " ${speedUnit.name}"
            return formattedSpeed
        }

    }
}