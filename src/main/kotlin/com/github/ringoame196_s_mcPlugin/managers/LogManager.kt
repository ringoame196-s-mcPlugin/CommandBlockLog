package com.github.ringoame196_s_mcPlugin.managers

import com.github.ringoame196_s_mcPlugin.Data
import com.github.ringoame196_s_mcPlugin.commands.CommandConst
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.block.Block

class LogManager {
    fun setBlockTypeFilter(args: Array<out String>): MutableList<Material> {
        val blockTypeFilter = mutableListOf<Material>()

        if (args.contains(CommandConst.SEARCH_IMPULSE)) {
            blockTypeFilter.add(Material.COMMAND_BLOCK)
        } else if (args.contains(CommandConst.SEARCH_CHAIN)) {
            blockTypeFilter.add(Material.CHAIN_COMMAND_BLOCK)
        } else if (args.contains(CommandConst.SEARCH_REPEAT)) {
            blockTypeFilter.add(Material.REPEATING_COMMAND_BLOCK)
        }
        return blockTypeFilter
    }

    fun setCommandFilter(args: Array<out String>): MutableList<String> {
        val commandFilter = mutableListOf<String>()

        for (arg in args) {
            if (arg.contains(CommandConst.SEARCH_COMMAND_PREFIX)) {
                val command = arg.replace(CommandConst.SEARCH_COMMAND_PREFIX, "")
                commandFilter.add(command)
            }
        }

        return commandFilter
    }

    fun convertingLog(blockTypeFilter: MutableList<Material>, commandFilter: MutableList<String>): MutableList<TextComponent> {
        // コマンドブロックの種類をわかりやすく
        val blockDisplayMap = mapOf(
            Material.COMMAND_BLOCK to "${ChatColor.GOLD}衝撃${ChatColor.WHITE}",
            Material.CHAIN_COMMAND_BLOCK to "${ChatColor.AQUA}チェーン${ChatColor.WHITE}",
            Material.REPEATING_COMMAND_BLOCK to "${ChatColor.BLUE}反復${ChatColor.WHITE}"
        )

        val logTexts = mutableListOf<TextComponent>()

        for ((block, command) in Data.commandBlockLog) {
            val blockType = block.type
            val blockDisplay = blockDisplayMap[blockType]
            if (!checkBlockTypeFilter(blockTypeFilter, blockType) || !checkCommandFilter(commandFilter, command)) continue
            val logText = TextComponent("[$blockDisplay] $command")
            logText.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, ComponentBuilder(displayLocation(block)).create())
            logTexts.add(logText)
        }

        return logTexts
    }

    private fun checkBlockTypeFilter(blockTypeFilter: MutableList<Material>, blockType: Material): Boolean {
        return blockTypeFilter.isEmpty() || !blockTypeFilter.contains(blockType)
    }

    private fun checkCommandFilter(commandFilter: MutableList<String>, command: String): Boolean {
        return if (commandFilter.isNotEmpty()) {
            commandFilter.any { command.contains(it) }
        } else {
            true
        }
    }

    private fun displayLocation(block: Block): String {
        val location = block.location
        val world = location.world?.name
        val x = location.x.toInt()
        val y = location.y.toInt()
        val z = location.z.toInt()
        return "座標:world名:$world, x:$x, y:$y, z:$z"
    }
}
