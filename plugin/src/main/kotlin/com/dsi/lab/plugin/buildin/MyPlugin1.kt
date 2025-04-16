package com.dsi.lab.plugin.buildin

import com.dsi.lab.plugincontract.PluginInitParam
import com.dsi.lab.plugincontract.PluginParam
import com.dsi.lab.plugincontract.PluginResp
import com.dsi.lab.plugincontract.WithPlugin
import com.dsi.lab.plugincontract.DateService
import org.slf4j.LoggerFactory

class MyPlugin1(
    initParam: PluginInitParam,
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
            message = "CODE $code NAME: $name PName: ${param.name} PAge: ${param.age}",
            dateTime = dateService.getDateTime()
        )
}