package tsuzuru.infrastructure.sudachi.service.impl

import com.worksap.nlp.sudachi.Dictionary
import com.worksap.nlp.sudachi.DictionaryFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import tsuzuru.infrastructure.sudachi.service.MorphologicalAnalysisService
import javax.annotation.PostConstruct

@Service
class MorphologicalAnalysisServiceImpl : MorphologicalAnalysisService {

    private lateinit var dictionary: Dictionary

    @PostConstruct
    fun initialize() {
        dictionary = DictionaryFactory()
            .create("", "{ \"systemDict\": \"${ClassPathResource("system_full.dic").file.path}\" }", true)
    }

    override fun pickNoun(text: String): List<String> {
        return dictionary.create().tokenize(text).filter {
            it.partOfSpeech().contains("名詞")
        }.map {
            it.surface()
        }
    }

}
