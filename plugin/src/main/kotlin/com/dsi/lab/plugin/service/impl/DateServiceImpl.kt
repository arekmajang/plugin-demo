package com.dsi.lab.plugin.service.impl

import com.dsi.lab.plugincontract.DateService
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class DateServiceImpl : DateService {
    override fun getDateTime(): LocalDateTime = LocalDateTime.now().minusDays(1)
}