package com.github.ringoame196_s_mcPlugin.datas

import org.bukkit.Location
import org.bukkit.Material

interface CommandBlockLog {
    fun acquisitionType(): Material
    fun acquisitionLocation(): Location
    fun acquisitionCommand(): String
}
