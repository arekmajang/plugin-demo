package com.dsi.lab.pluginimpl

import com.dsi.lab.plugincontract.*

class PluginImpl1(
    initParam: PluginInitParam
) : WithPlugin {

    override val code: String = initParam.code
    override val name: String = initParam.name

    private val dateService = initParam.applicationContext.getBean(DateService::class.java)

    override fun execute(param: PluginParam): PluginResp =
        PluginResp(
            message = "PluginImpl1 -> CODE $code NAME: $name PName: ${param.name} PAge: ${param.age}",
            dateTime = dateService.getDateTime()
        )
}