package com.dsi.lab.pluginimpl

import com.dsi.lab.plugincontract.*
import com.dsi.lab.pluginimpl.util.LabUtils

class PluginImpl2(
    initParam: PluginInitParam
) : WithPlugin {

    override val code: String = initParam.code
    override val name: String = initParam.name

    private val dateService = initParam.applicationContext.getBean(DateService::class.java)

    private val classess = listOf(
        LabUtils::class.java,
        LabUtils.UtilReg::class.java,
        LabUtils.UtilResp::class.java,
    )

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
