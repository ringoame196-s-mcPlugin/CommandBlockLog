package com.github.ringoame196_s_mcPlugin.commands

import com.github.ringoame196_s_mcPlugin.Data
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class Command : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) return false

        val subCommand = args[0]

        when (subCommand) {
            CommandConst.START_LOG_COMMAND -> startLogCommand(sender)
            CommandConst.STOP_LOG_COMMAND -> stopLogCommand(sender)
            else -> {
                val errorMessage = "${ChatColor.RED}構文が間違っています"
                sender.sendMessage(errorMessage)
            }
        }

        return true
    }

    private fun startLogCommand(sender: CommandSender) {
        val startMessage = "${ChatColor.AQUA}ログ取得を開始しました"
        val errorMessage = "${ChatColor.RED}既に開始しています"

        if (!Data.inLogAcquisition) {
            Data.inLogAcquisition = true
            Data.commandBlockLog.clear() // ログ削除
            sender.sendMessage(startMessage)
        } else {
            sender.sendMessage(errorMessage)
        }
    }

    private fun stopLogCommand(sender: CommandSender) {
        val stopMessage = "${ChatColor.GOLD}ログ取得を停止しました"
        val errorMessage = "${ChatColor.RED}既に開始しています"

        if (Data.inLogAcquisition) {
            Data.inLogAcquisition = false
            sender.sendMessage(stopMessage)
        } else {
            sender.sendMessage(errorMessage)
        }
    }
}
