package com.dsi.lab.plugin.service

import com.dsi.lab.plugin.config.PluginCfg
import com.dsi.lab.plugincontract.PluginParam
import com.dsi.lab.plugincontract.PluginResp
import com.dsi.lab.plugincontract.WithPlugin

interface PluginService {
    fun getPluginCodes(): Set<String>
    fun allState(): List<PluginState>
    fun execute(pluginCode: String, param: PluginParam): PluginResp
}

class PluginState(
    val code: String,
    val name: String,
    val className: String,
    val loadStatus: Boolean,
    val loadMessage: String
)

class PluginRuntime(
    val state: PluginState,
    val plugin: WithPlugin?
) {
    companion object {
        fun ofError(pluginCfg: PluginCfg, t: Throwable): PluginRuntime {
            return PluginRuntime(
                state = PluginState(
                    code = pluginCfg.code,
                    name = pluginCfg.name,
                    className = pluginCfg.className,
                    loadStatus = false,
                    loadMessage = t.stackTraceToString() ?: "error",
                ),
                plugin = null
            )
        }

        fun ofSuccess(pluginCfg: PluginCfg, plugin: WithPlugin): PluginRuntime {
            return PluginRuntime(
                state = PluginState(
                    code = pluginCfg.code,
                    name = pluginCfg.name,
                    className = pluginCfg.className,
                    loadStatus = true,
                    loadMessage = "success",
                ),
                plugin = plugin
            )
        }
    }
}
