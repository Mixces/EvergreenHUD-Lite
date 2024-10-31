package org.polyfrost.evergreenhud.utils

import net.minecraft.client.gui.Gui
import java.awt.Color

fun drawRectWithShadow(
    x: Int, y: Int, x2: Int, y2: Int, color: Int, shadow: Int
) {
    if (shadow == 1) {
        Gui.drawRect(x + 1, y + 1, x2 + 1, y2 + 1,
            Color(
                (color shr 16 and 255) / 4,
                (color shr 8 and 255) / 4,
                (color and 255) / 4,
                color shr 24 and 255
            ).rgb
        )
    }
    Gui.drawRect(x, y, x2, y2, color)
}