package com.dsi.lab.plugin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PluginApplication

fun main(args: Array<String>) {
    runApplication<PluginApplication>(*args)
}
