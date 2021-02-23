package tsuzuru.infrastructure.useCase.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tsuzuru.infrastructure.jpa.repository.TblItemRepository
import tsuzuru.infrastructure.jpa.repository.TblUserRepository
import tsuzuru.useCase.AdminGetsStatisticsUseCase

@Component
@Transactional
class AdminGetsStatisticsUseCaseImpl(
    private val tblItemRepository: TblItemRepository,
    private val tblUserRepository: TblUserRepository,
) : AdminGetsStatisticsUseCase {

    override fun perform(
        request: AdminGetsStatisticsUseCase.Request,
    ): AdminGetsStatisticsUseCase.Response {
        return AdminGetsStatisticsUseCase.Response(
            userSize = tblUserRepository.count(),
            itemSize = tblItemRepository.count(),
        )
    }

}
