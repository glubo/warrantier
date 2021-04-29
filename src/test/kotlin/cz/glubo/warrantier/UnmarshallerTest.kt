package cz.glubo.warrantier

import cz.glubo.warrantier.service.UnmarshallingService
import org.junit.jupiter.api.Test
import org.junit.platform.commons.logging.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource

@SpringBootTest
class UnmarshallerTest {
    @Autowired
    lateinit var unmarshallingService: UnmarshallingService
    val logger = LoggerFactory.getLogger(FetcherTest::class.java)

    @Test
    fun testSmall() {
        val testZip = ClassPathResource("testData/small_all.zip")

        unmarshallingService.unmarshallAll(testZip) {
            logger.info { it.toString() }
        }
    }
}