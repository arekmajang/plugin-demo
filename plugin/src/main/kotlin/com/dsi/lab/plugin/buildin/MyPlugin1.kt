package com.dsi.lab.plugin.buildin

import com.dsi.lab.plugincontract.PluginInitParam
import com.dsi.lab.plugincontract.PluginParam
import com.dsi.lab.plugincontract.PluginResp
import com.dsi.lab.plugincontract.WithPlugin
import com.dsi.lab.plugincontract.DateService

class MyPlugin1(
    initParam: PluginInitParam,
) : WithPlugin {

    override val code: String = initParam.code
    override val name: String = initParam.name

    private val dateService = initParam.applicationContext.getBean(DateService::class.java)

    override fun execute(param: PluginParam): PluginResp =
        PluginResp(
            message = "CODE $code NAME: $name PName: ${param.name} PAge: ${param.age}",
            dateTime = dateService.getDateTime()
        )
}