package cz.glubo.warrantier.service

import com.opencsv.CSVReader
import cz.glubo.warrantier.model.DokladType
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.zip.ZipInputStream
import kotlin.io.path.deleteIfExists

@Service
class UnmarshallingService(
    private val fetcherService: FetcherService,
) {
    data class Record(
        val identifier: String,
        val since: LocalDate,
    )

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("d.M.u")

    fun fetchAndUnmarshallAll(dokladType: DokladType, batchHandler: (List<Record>) -> Unit) {
        val zipFile = fetcherService.fetchZip(dokladType, false)
        unmarshallAll(FileSystemResource(zipFile), batchHandler)
        zipFile.deleteIfExists()
    }

    fun unmarshallAll(zipFile: Resource, batchHandler: (List<Record>) -> Unit) {
        ZipInputStream(zipFile.inputStream).use { zis ->
            generateSequence { zis.nextEntry }
                .filterNot { it.isDirectory }
                .forEach { unmarshallCsv(CSVReader(zis.reader(), ';'), batchHandler) }
        }
    }

    private fun unmarshallCsv(csvReader: CSVReader, batchHandler: (List<Record>) -> Unit, batchSize: Int = 100) {
        val buffer = ArrayList<Record>(batchSize)
        var nextLine = csvReader.readNext()
        while (nextLine != null) {
            if (buffer.size >= batchSize) {
                batchHandler(buffer)
                buffer.clear()
            }
            buffer.add(Record(nextLine[0], LocalDate.parse(nextLine[1], dateTimeFormatter)))
            nextLine = csvReader.readNext()
        }
        batchHandler(buffer)
    }
}