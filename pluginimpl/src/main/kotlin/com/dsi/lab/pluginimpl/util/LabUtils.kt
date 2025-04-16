package com.dsi.lab.pluginimpl.util

import java.time.LocalDateTime

object LabUtils {

    fun runTest(reg: UtilReg) = UtilResp(
        message = "LabUtils -> ${reg.name}",
        dateTime = LocalDateTime.now().minusYears(1),
    )

    data class UtilReg(
        val name: String,
    )

    data class UtilResp(
        val message: String,
        val dateTime: LocalDateTime,
    )
}