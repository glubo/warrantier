package cz.glubo.warrantier.service

import cz.glubo.warrantier.config.UpstreamConfiguration
import cz.glubo.warrantier.model.DokladType
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.util.StreamUtils
import org.springframework.web.util.UriComponentsBuilder
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.outputStream

@Service
class FetcherService(
    restTemplateBuilder: RestTemplateBuilder,
    private val upstreamProperties: UpstreamConfiguration.UpstreamProperties,
) {
    val restTemplate = restTemplateBuilder.build()

    /** fetches data zip from upstream and returns it's local path, caller is responsible for it deletion */
    fun fetchZip(dokladType: DokladType, diff: Boolean): Path {
        val uri = UriComponentsBuilder.fromUriString(upstreamProperties.neplatneDokladyUri)
            .queryParam("typ_dokladu", dokladType.upstreamIdentifier)
            .queryParam("rozdil", if (diff) 1 else 0)
            .build().toUri()

        val tempFile = Files.createTempFile("warrantier", "fetcherService")

        restTemplate.execute(uri, HttpMethod.GET, null) {
            StreamUtils.copy(it.body, tempFile.outputStream(StandardOpenOption.WRITE))
            tempFile
        }
        return tempFile
    }
}