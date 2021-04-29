package cz.glubo.warrantier

import cz.glubo.warrantier.config.CommonConfig
import cz.glubo.warrantier.config.UpstreamConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@Import(
	CommonConfig::class,
    UpstreamConfiguration::class,
)
@SpringBootApplication
class WarrantierApplication

fun main(args: Array<String>) {
	runApplication<WarrantierApplication>(*args)
}
