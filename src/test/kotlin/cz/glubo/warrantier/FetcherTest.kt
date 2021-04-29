package cz.glubo.warrantier

import cz.glubo.warrantier.model.DokladType
import cz.glubo.warrantier.service.FetcherService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.platform.commons.logging.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.stream.Stream
import kotlin.io.path.exists
import kotlin.io.path.fileSize

@SpringBootTest
class FetcherTest {
    @Autowired
    lateinit var fetcherService: FetcherService
    val logger = LoggerFactory.getLogger(FetcherTest::class.java)

    @Test
    fun testSimpleFetch() {
        val fetched = fetcherService.fetchZip(DokladType.OP, false)
        logger.info { "fetched ${fetched.fileName}" }
        Assertions.assertNotNull(fetched, "fetcherService returned something")
        Assertions.assertTrue(fetched.exists(), "fetcherService returned file that exists")
        Assertions.assertTrue(fetched.fileSize() > 0, "fetcher service returned file that is non-empty")
    }

    @ParameterizedTest
    @MethodSource("testFetchAllProvider")
    fun testFetchAll(dokladType: DokladType, diff: Boolean) {
        val fetched = fetcherService.fetchZip(dokladType, diff)
        logger.info { "fetched ${fetched.fileName}" }
        Assertions.assertNotNull(fetched, "fetcherService returned something")
        Assertions.assertTrue(fetched.exists(), "fetcherService returned file that exists")
        Assertions.assertTrue(fetched.fileSize() > 0, "fetcher service returned file that is non-empty")
    }

    companion object {
        @JvmStatic
        fun testFetchAllProvider() = Stream.of(
            Arguments.of(DokladType.OP, true),
            Arguments.of(DokladType.OP, false),
            Arguments.of(DokladType.CD, true),
            Arguments.of(DokladType.CD, false),
            Arguments.of(DokladType.OPS, true),
            Arguments.of(DokladType.OPS, false),
            Arguments.of(DokladType.ZP, true),
            Arguments.of(DokladType.ZP, false),
        )
    }
}