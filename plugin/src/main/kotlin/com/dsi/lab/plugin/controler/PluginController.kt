package com.dsi.lab.plugin.controler

import com.dsi.lab.plugincontract.PluginParam
import com.dsi.lab.plugincontract.PluginResp
import com.dsi.lab.plugin.service.PluginService
import com.dsi.lab.plugin.service.PluginState
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/plugins")
class PluginController(
    private val pluginService: PluginService,
) {

    @GetMapping("codes")
    fun getPluginCodes(): Set<String> = pluginService.getPluginCodes()

    @GetMapping("states")
    fun allState(): List<PluginState> = pluginService.allState()

    @PostMapping("execute")
    fun execute(
        @RequestParam pluginCode: String,
        @RequestBody param: PluginParam
    ): PluginResp =
        pluginService.execute(pluginCode, param)


}