package com.github.ringoame196_s_mcPlugin.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class TabCompleter : TabCompleter {
    override fun onTabComplete(commandSender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        return when(args.size) {
            1 -> mutableListOf(CommandConst.START_LOG_COMMAND, CommandConst.STOP_LOG_COMMAND, CommandConst.SHOW_LOG_COMMAND, CommandConst.SEE_INFO_COMMAND_BLOCK)
            else -> {
                when(args[0]) {
                    // ログの情報を見るときのみ 検索値で指定ができるようにする
                    CommandConst.SHOW_LOG_COMMAND -> mutableListOf(CommandConst.SEARCH_IMPULSE, CommandConst.SEARCH_CHAIN, CommandConst.SEARCH_REPEAT, CommandConst.SEARCH_COMMAND_PREFIX)
                    else -> mutableListOf()
                }
            }
        }
    }
}
