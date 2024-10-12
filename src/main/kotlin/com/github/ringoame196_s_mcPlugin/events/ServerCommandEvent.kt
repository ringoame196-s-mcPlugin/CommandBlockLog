package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.datas.CommandBlockDedicatedLog
import com.github.ringoame196_s_mcPlugin.datas.CommandBlockLog
import com.github.ringoame196_s_mcPlugin.datas.CommandBlockMinecartDedicatedLog
import com.github.ringoame196_s_mcPlugin.datas.Data
import org.bukkit.command.BlockCommandSender
import org.bukkit.entity.minecart.CommandMinecart
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerCommandEvent

class ServerCommandEvent : Listener {
    @EventHandler
    fun onServerCommand(e: ServerCommandEvent) {
        if (!Data.inLogAcquisition) return

        val commandBlock = e.sender
        val command = e.command
        val commandBlockLog: CommandBlockLog

        when (commandBlock) {
            is BlockCommandSender -> {
                val block = commandBlock.block
                commandBlockLog = CommandBlockDedicatedLog(block, command)
            }
            is CommandMinecart -> {
                commandBlockLog = CommandBlockMinecartDedicatedLog(commandBlock, command)
            }
            else -> return
        }

        if (!Data.commandBlockLog.contains(commandBlockLog)) {
            Data.commandBlockLog.add(commandBlockLog) // ログ保存
        }
    }
}
