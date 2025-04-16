package com.dsi.lab.plugin.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix = "dsi.lab")
@Component
data class ConfigProps(
    var plugins: List<PluginCfg> = emptyList(),
)

data class PluginCfg(
    var code: String,
    var name: String,
    var jarFilePath: String?,
    var className: String,
)
