package com.icoo.ssgsag_android.data.model.poster

import com.icoo.ssgsag_android.data.model.poster.posterDetail.PosterDetail

data class TodaySsgSagResponse(
    val status: Int,
    val message: String,
    var data: TodaySsgSag
)