package tsuzuru.infrastructure.sudachi.service

interface MorphologicalAnalysisService {

    fun pickNoun(text: String): List<String>

}
