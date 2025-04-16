package com.dsi.lab.plugincontract

import org.springframework.context.ApplicationContext
import java.time.LocalDateTime

interface WithPlugin {
    val code: String
    val name: String
    fun execute(param: PluginParam): PluginResp
}

data class PluginParam(
    val name: String,
    val age: Int,
)

data class PluginResp(
    val message: String,
    val dateTime: LocalDateTime,
)

data class PluginInitParam(
    val code: String,
    val name: String,
    val applicationContext: ApplicationContext
)