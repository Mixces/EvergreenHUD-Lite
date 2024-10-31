package org.polyfrost.evergreenhud.hud

import cc.polyfrost.oneconfig.config.annotations.Color
import cc.polyfrost.oneconfig.config.annotations.Dropdown
import cc.polyfrost.oneconfig.config.annotations.HUD
import cc.polyfrost.oneconfig.config.annotations.Switch
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.events.EventManager
import cc.polyfrost.oneconfig.events.event.RenderEvent
import cc.polyfrost.oneconfig.events.event.Stage
import cc.polyfrost.oneconfig.hud.SingleTextHud
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack
import cc.polyfrost.oneconfig.utils.dsl.mc
import org.lwjgl.input.Mouse
import org.polyfrost.evergreenhud.config.HudConfig
import org.polyfrost.evergreenhud.utils.drawRectWithShadow

class CPS: HudConfig("CPS", "evergreenhud/cps.json", false) {
    @HUD(name = "Main")
    var hud = CPSHud()

    init {
        initialize()
    }

    class CPSHud: SingleTextHud("CPS", true, 0, 50) {

        @Switch(
            name = "Update Fast"
        )
        var updateFast = false

        @Color(name = "CPS Button Divider Color")
        var dividerColor: OneColor = OneColor(255, 255, 255)

        @Dropdown(
            name = "Button",
            options = ["Left", "Right", "Both"]
        )
        var button = 2

        private val dividerSpace = "  "

        private val left = ArrayDeque<Long>()
        private var leftPressed = false
        private val right = ArrayDeque<Long>()
        private var rightPressed = false

        init {
            EventManager.INSTANCE.register(this)
        }

        @Subscribe
        private fun onRenderTick(event: RenderEvent) {
            if (event.stage == Stage.END) {
                var pressed = Mouse.isButtonDown(mc.gameSettings.keyBindAttack.keyCode + 100)

                if (pressed != leftPressed) {
                    leftPressed = pressed
                    if (pressed) left.add(System.currentTimeMillis())
                }

                pressed = Mouse.isButtonDown(mc.gameSettings.keyBindUseItem.keyCode + 100)

                if (pressed != rightPressed) {
                    rightPressed = pressed
                    if (pressed) right.add(System.currentTimeMillis())
                }

                val currentTime = System.currentTimeMillis()
                if (!left.isEmpty()) {
                    left.removeIf { (currentTime - it) > 1000 }
                }
                if (!right.isEmpty()) {
                    right.removeIf { (currentTime - it) > 1000 }
                }
            }
        }

        override fun getText(example: Boolean): String {
            return when (button) {
                0 -> left.size.toString()
                1 -> right.size.toString()
                2 -> "${left.size}${dividerSpace}${right.size}"
                else -> throw IllegalStateException()
            }
        }

        override fun getTextFrequent(example: Boolean): String? {
            return if (updateFast) {
                getText(example)
            } else {
                null
            }
        }

        override fun draw(matrices: UMatrixStack?, x: Float, y: Float, scale: Float, example: Boolean) {
            super.draw(matrices, x, y, scale, example)
            // cps button divider
            if (button == 2) {
                // i hope im not missing anything
                val bracketWidth = if (brackets) mc.fontRendererObj.getStringWidth("[") else 0
                val titleLocation = if (titleLocation == 0) mc.fontRendererObj.getStringWidth("$title: ") else 0
                val cpsWidth = mc.fontRendererObj.getStringWidth(left.size.toString())
                val dividerSpaceWidth = mc.fontRendererObj.getStringWidth(dividerSpace) / 2 - 0.4f
                val xOffset = x.toInt() + bracketWidth + titleLocation + cpsWidth + dividerSpaceWidth.toInt()

                drawRectWithShadow(xOffset, y.toInt(), xOffset + 1, y.toInt() + 7, dividerColor.rgb, textType)
            }
        }
    }
}