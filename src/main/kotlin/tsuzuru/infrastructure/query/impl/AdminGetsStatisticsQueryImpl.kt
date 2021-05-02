package tsuzuru.infrastructure.query.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tsuzuru.infrastructure.jpa.repository.TblItemRepository
import tsuzuru.infrastructure.jpa.repository.TblUserRepository
import tsuzuru.useCase.query.AdminGetsStatisticsQuery

@Component
@Transactional
class AdminGetsStatisticsQueryImpl(
    private val tblItemRepository: TblItemRepository,
    private val tblUserRepository: TblUserRepository,
) : AdminGetsStatisticsQuery {

    override fun perform(
        request: AdminGetsStatisticsQuery.Request,
    ): AdminGetsStatisticsQuery.Response {
        return AdminGetsStatisticsQuery.Response(
            userSize = tblUserRepository.count(),
            itemSize = tblItemRepository.count(),
        )
    }

}
