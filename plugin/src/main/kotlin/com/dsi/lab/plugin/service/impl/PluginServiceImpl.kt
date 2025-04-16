package com.dsi.lab.plugin.service.impl

import com.dsi.lab.plugin.config.ConfigProps
import com.dsi.lab.plugin.config.PluginCfg
import com.dsi.lab.plugincontract.PluginInitParam
import com.dsi.lab.plugincontract.PluginParam
import com.dsi.lab.plugincontract.PluginResp
import com.dsi.lab.plugincontract.WithPlugin
import com.dsi.lab.plugin.service.PluginRuntime
import com.dsi.lab.plugin.service.PluginService
import com.dsi.lab.plugin.service.PluginState
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service
import java.io.File
import java.net.URL
import java.net.URLClassLoader
import java.util.concurrent.ConcurrentHashMap

@Service
class PluginServiceImpl(
    private val applicationContext: ApplicationContext,
    private val configProps: ConfigProps
) : PluginService {

    private val log = LoggerFactory.getLogger(javaClass)

    private val codeToPlugin: ConcurrentHashMap<String, PluginRuntime> = ConcurrentHashMap()

    init {
        initPlugin()
    }

    private fun initPlugin() {
        codeToPlugin.clear()
        configProps.plugins.forEach { pluginCfg: PluginCfg ->
            val pluginRuntime = loadPlugin(pluginCfg)
            codeToPlugin[pluginRuntime.state.code] = pluginRuntime
        }
    }

    override fun getPluginCodes(): Set<String> = codeToPlugin.keys

    override fun allState(): List<PluginState> =
        codeToPlugin.map { it.value.state }

    override fun execute(pluginCode: String, param: PluginParam): PluginResp {
        val pluginRuntime = codeToPlugin[pluginCode]
            ?: throw RuntimeException("Plugin code $pluginCode not found")
        return if (!pluginRuntime.state.loadStatus) {
            throw RuntimeException("Plugin code $pluginCode fail load ${pluginRuntime.state.loadMessage}")
        } else {
            pluginRuntime.plugin!!.execute(param)
        }
    }

    private fun loadPlugin(pluginCfg: PluginCfg): PluginRuntime =
        try {
            val jarPath = pluginCfg.jarFilePath
            val initParam = PluginInitParam(
                code = pluginCfg.code,
                name = pluginCfg.name,
                applicationContext = applicationContext
            )
            if (jarPath.isNullOrBlank()) {
                val clazz = Class.forName(pluginCfg.className).kotlin
                val constructor = clazz.constructors.first()
                val plugin = constructor.call(initParam) as WithPlugin
                PluginRuntime.ofSuccess(
                    pluginCfg, plugin
                )
            } else {
                val jarFile: File = File(pluginCfg.jarFilePath!!)
                val jarUrl: URL = jarFile.toURI().toURL()

                URLClassLoader(arrayOf(jarUrl), this.javaClass.classLoader).use { classLoader ->
                    val clazz = Class.forName(pluginCfg.className, true, classLoader).kotlin
                    val constructor = clazz.constructors.first()
                    val plugin = constructor.call(initParam) as WithPlugin
                    plugin.postInit()
                    PluginRuntime.ofSuccess(
                        pluginCfg, plugin
                    )
                }
            }
        } catch (t: Throwable) {
            log.error("loadPlugin ERR ${pluginCfg}", t)
            PluginRuntime.ofError(pluginCfg, t)
        }

}