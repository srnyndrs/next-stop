package com.srnyndrs.next_stop.app.presentation.sample

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.srnyndrs.next_stop.shared.domain.model.combined.NearbyDepartures

class NearbyDeparturesPreviewParameterProvider: PreviewParameterProvider<NearbyDepartures> {
    override val values: Sequence<NearbyDepartures>
        get() = sequenceOf(
            testNearbyDepartures()
        )
}