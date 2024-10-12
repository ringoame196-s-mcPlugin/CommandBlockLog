package com.github.ringoame196_s_mcPlugin.commands

import com.github.ringoame196_s_mcPlugin.Data
import com.github.ringoame196_s_mcPlugin.managers.LogManager
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class Command : CommandExecutor {
    private val logManager = LogManager()

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) return false

        val subCommand = args[0]

        when (subCommand) {
            CommandConst.START_LOG_COMMAND -> startLogCommand(sender)
            CommandConst.STOP_LOG_COMMAND -> stopLogCommand(sender)
            CommandConst.SHOW_LOG_COMMAND -> showLogCommand(sender, args)
            else -> {
                val errorMessage = "${ChatColor.RED}構文が間違っています"
                sender.sendMessage(errorMessage)
            }
        }

        return true
    }

    private fun startLogCommand(sender: CommandSender) {
        if (!Data.inLogAcquisition) {
            val startMessage = "${ChatColor.AQUA}ログ取得を開始しました"
            Data.inLogAcquisition = true
            Data.commandBlockLog.clear() // ログ削除
            sender.sendMessage(startMessage)
        } else {
            val errorMessage = "${ChatColor.RED}既に開始しています"
            sender.sendMessage(errorMessage)
        }
    }

    private fun stopLogCommand(sender: CommandSender) {
        if (Data.inLogAcquisition) {
            val stopMessage = "${ChatColor.GOLD}ログ取得を停止しました"
            Data.inLogAcquisition = false
            sender.sendMessage(stopMessage)
        } else {
            val errorMessage = "${ChatColor.RED}既に開始しています"
            sender.sendMessage(errorMessage)
        }
    }

    private fun showLogCommand(sender: CommandSender, args: Array<out String>) {
        if (Data.inLogAcquisition) {
            val acquisitionWithinMessage = "${ChatColor.RED}ログ取得中に表示することはできません"
            sender.sendMessage(acquisitionWithinMessage)
            return
        }

        // フィルター設定
        val blockTypeFilter = logManager.setBlockTypeFilter(args)
        val commandFilter = logManager.setCommandFilter(args)

        val logTexts = logManager.convertingLog(blockTypeFilter, commandFilter)

        if (logTexts.isEmpty()) {
            val message = "${ChatColor.RED}ログがありません"
            sender.sendMessage(message)
        } else {
            val startMessage = "${ChatColor.YELLOW} ----- コマンドブロックログ -----"
            sender.sendMessage(startMessage)
            for (logText in logTexts) {
                sender.spigot().sendMessage(logText)
            }
        }
    }
}
