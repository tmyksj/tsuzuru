package tsuzuru

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TsuzuruApplication

fun main(args: Array<String>) {
    runApplication<TsuzuruApplication>(*args)
}
