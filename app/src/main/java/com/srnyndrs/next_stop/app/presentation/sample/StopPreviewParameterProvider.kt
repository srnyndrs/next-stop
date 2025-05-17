package com.srnyndrs.next_stop.app.presentation.sample

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.srnyndrs.next_stop.shared.domain.model.single.Stop

class StopPreviewParameterProvider: PreviewParameterProvider<Stop> {
    override val values: Sequence<Stop>
        get() = sequenceOf(
            testStopA(),
            testStopB()
        )
}