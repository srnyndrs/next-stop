package com.srnyndrs.next_stop.app.presentation.sample

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.srnyndrs.next_stop.shared.domain.model.combined.TripDetails

class TripDetailsPreviewParameterProvider: PreviewParameterProvider<TripDetails> {
    override val values: Sequence<TripDetails>
        get() = sequenceOf(
            testTripDetailsA()
        )
}