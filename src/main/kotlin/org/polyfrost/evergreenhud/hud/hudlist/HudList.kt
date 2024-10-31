package org.polyfrost.evergreenhud.hud.hudlist

import cc.polyfrost.oneconfig.hud.Hud

abstract class HudList<T : Hud> : ArrayList<T>() {
    abstract fun newHud(): T
    abstract fun getHudName(hud: T): String
}