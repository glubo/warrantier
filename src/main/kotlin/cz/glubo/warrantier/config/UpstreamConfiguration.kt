package cz.glubo.warrantier.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(UpstreamConfiguration.UpstreamProperties::class)
class UpstreamConfiguration {
    @ConstructorBinding
    @ConfigurationProperties(prefix = "upstream")
    data class UpstreamProperties (
        val neplatneDokladyUri: String
    )
}