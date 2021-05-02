package tsuzuru.infrastructure.query.impl

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tsuzuru.domain.entity.ItemEntity
import tsuzuru.domain.entity.UserEntity
import tsuzuru.infrastructure.jpa.repository.TblItemRepository
import tsuzuru.infrastructure.repository.impl.ItemRepositoryImpl
import tsuzuru.infrastructure.repository.impl.UserRepositoryImpl
import tsuzuru.infrastructure.sudachi.service.MorphologicalAnalysisService
import tsuzuru.useCase.query.GuestGetsUserActivityQuery

@Component
@Transactional
class GuestGetsUserActivityQueryImpl(
    private val tblItemRepository: TblItemRepository,
    private val itemRepositoryImpl: ItemRepositoryImpl,
    private val userRepositoryImpl: UserRepositoryImpl,
    private val morphologicalAnalysisService: MorphologicalAnalysisService,
) : GuestGetsUserActivityQuery {

    override fun perform(
        request: GuestGetsUserActivityQuery.Request,
    ): GuestGetsUserActivityQuery.Response {
        val userEntity: UserEntity = userRepositoryImpl.findByName(request.name)
            ?: throw GuestGetsUserActivityQuery.UserIsNotFoundException()

        if (userEntity.scope == UserEntity.Scope.Private) {
            throw GuestGetsUserActivityQuery.UserIsNotFoundException()
        }

        return GuestGetsUserActivityQuery.Response(
            name = userEntity.name,
            profileName = userEntity.profileName,
            analytics = buildAnalytics(request, userEntity),
            itemList = buildItemList(request, userEntity),
        )
    }

    private fun buildAnalytics(
        request: GuestGetsUserActivityQuery.Request,
        userEntity: UserEntity,
    ): GuestGetsUserActivityQuery.Analytics {
        val itemEntityList: List<ItemEntity> = itemRepositoryImpl.run {
            tblItemRepository
                .findByTblUserUuidAndWrittenDateBetweenOrderByWrittenDateAsc(
                    tblUserUuid = userEntity.uuid.toString(),
                    writtenDateStart = request.range.start.atStartOfDay(),
                    writtenDateEndInclusive = request.range.endInclusive.atStartOfDay().plusDays(1),
                ).toDomainEntity()
        }

        val nounList: List<GuestGetsUserActivityQuery.Noun> = itemEntityList
            .flatMap { morphologicalAnalysisService.pickNoun(it.body) }
            .groupBy { it }
            .values
            .sortedByDescending { it.size }
            .map {
                GuestGetsUserActivityQuery.Noun(
                    value = it.first(),
                    size = it.size,
                )
            }

        return GuestGetsUserActivityQuery.Analytics(
            nounList = nounList,
            size = itemEntityList.size,
        )
    }

    private fun buildItemList(
        request: GuestGetsUserActivityQuery.Request,
        userEntity: UserEntity,
    ): List<GuestGetsUserActivityQuery.Item> {
        val itemEntityList: List<ItemEntity> = itemRepositoryImpl.run {
            tblItemRepository
                .findByTblUserUuidAndWrittenDateLessThanEqualOrderByWrittenDateDesc(
                    tblUserUuid = userEntity.uuid.toString(),
                    writtenDate = request.page,
                    pageable = PageRequest.of(0, request.size),
                ).toDomainEntity()
        }

        return itemEntityList.map {
            GuestGetsUserActivityQuery.Item(
                body = it.body,
                writtenDate = it.writtenDate,
            )
        }
    }

}
