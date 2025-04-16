package com.dsi.lab.pluginimpl

import com.dsi.lab.plugincontract.*
import com.dsi.lab.pluginimpl.util.LabUtils
import org.slf4j.LoggerFactory

class PluginImpl2(
    initParam: PluginInitParam
) : WithPlugin {
    private val log = LoggerFactory.getLogger(javaClass)

    override val code: String = initParam.code
    override val name: String = initParam.name

    private val dateService = initParam.applicationContext.getBean(DateService::class.java)

    override fun postInit() {
        log.info("postInit START")
        val classess = listOf(
            LabUtils::class.java,
            LabUtils.UtilReg::class.java,
            LabUtils.UtilResp::class.java,
        )
        log.info("postInit DONE")
    }


    override fun execute(param: PluginParam): PluginResp {
        val utilResp: LabUtils.UtilResp = LabUtils.runTest(
            reg = LabUtils.UtilReg(
                name = param.name,
            )
        )
        return PluginResp(
            message = utilResp.message,
            dateTime = utilResp.dateTime
        )

    }
}
