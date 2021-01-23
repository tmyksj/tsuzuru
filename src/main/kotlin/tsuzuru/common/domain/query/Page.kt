package tsuzuru.common.domain.query

import tsuzuru.common.domain.entity.AbstractEntity

data class Page<T : AbstractEntity>(
    val entityList: List<T>,
    val pageable: Pageable,
    val totalPage: Int,
    val totalSize: Int,
)
