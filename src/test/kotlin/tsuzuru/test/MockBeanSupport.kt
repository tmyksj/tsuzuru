package tsuzuru.test

import org.springframework.boot.test.context.TestComponent
import org.springframework.boot.test.mock.mockito.MockBean
import tsuzuru.infrastructure.sudachi.service.MorphologicalAnalysisService

@TestComponent
class MockBeanSupport {

    @MockBean
    private lateinit var morphologicalAnalysisService: MorphologicalAnalysisService

}
