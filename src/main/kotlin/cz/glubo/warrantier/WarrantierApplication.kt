package cz.glubo.warrantier

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WarrantierApplication

fun main(args: Array<String>) {
	runApplication<WarrantierApplication>(*args)
}
