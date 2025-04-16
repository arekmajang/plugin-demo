package com.dsi.lab.pluginimpl

import com.dsi.lab.plugincontract.*
import org.slf4j.LoggerFactory

class PluginImpl1(
    initParam: PluginInitParam
) : WithPlugin {
    private val log = LoggerFactory.getLogger(javaClass)

    override val code: String = initParam.code
    override val name: String = initParam.name

    private val dateService = initParam.applicationContext.getBean(DateService::class.java)

    override fun postInit() {
        log.info("postInit START")
        log.info("postInit DONE")
    }

    override fun execute(param: PluginParam): PluginResp =
        PluginResp(
            message = "PluginImpl1 -> CODE $code NAME: $name PName: ${param.name} PAge: ${param.age}",
            dateTime = dateService.getDateTime()
        )
}