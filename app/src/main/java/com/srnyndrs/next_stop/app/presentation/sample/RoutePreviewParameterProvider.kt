package com.srnyndrs.next_stop.app.presentation.sample

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.srnyndrs.next_stop.shared.domain.model.single.Route

class RoutePreviewParameterProvider: PreviewParameterProvider<Route> {
    override val values: Sequence<Route>
        get() = sequenceOf(
            testRouteA(),
            testRouteB(),
            testRouteC()
        )
}