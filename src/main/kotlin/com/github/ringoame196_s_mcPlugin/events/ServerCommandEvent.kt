package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.Data
import org.bukkit.command.BlockCommandSender
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerCommandEvent

class ServerCommandEvent : Listener {
    @EventHandler
    fun onServerCommand(e: ServerCommandEvent) {
        if (!Data.inLogAcquisition) return

        val commandBlock = e.sender as? BlockCommandSender ?: return
        val block = commandBlock.block
        val command = e.command
        Data.commandBlockLog[block] = command // ログ保存
    }
}
