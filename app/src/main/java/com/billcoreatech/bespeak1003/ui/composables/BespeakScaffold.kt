package com.billcoreatech.bespeak1003.ui.composables

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.plusAssign
import com.billcoreatech.bespeak1003.widget.appCurrentDestinationAsState
import com.billcoreatech.bespeak1003.widget.destinations.Destination
import com.billcoreatech.bespeak1003.widget.startAppDestination
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.ramcosta.composedestinations.spec.Route

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun BespeakScaffold(
    startRoute: Route,
    navController: NavHostController,
    bottomBar: @Composable (Destination) -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) {
    val destination = navController.appCurrentDestinationAsState().value
        ?: startRoute.startAppDestination

    // ๐ ๋๋ฒ๊น์ ์ํด์๋ง ์ฃผ์์ ์ํด ์ ํ๋๊ธฐ ๋๋ฌธ์ backQueue API๋ฅผ ์ฌ์ฉํด์๋ ์๋ฉ๋๋ค.
    navController.backQueue.print()

    val bottomSheetNavigator = rememberBottomSheetNavigator()
    navController.navigatorProvider += bottomSheetNavigator

    // ๐ ModalBottomSheetLayout์ ์ผ๋ถ ๋์์ด ํ๋จ ์ํธ ์คํ์ผ์ธ ๊ฒฝ์ฐ์๋ง ํ์ํฉ๋๋ค.
    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetShape = RoundedCornerShape(16.dp)
    ) {
        Scaffold(
            bottomBar = { bottomBar(destination) },
            content = content
        )
    }
}

private fun ArrayDeque<NavBackStackEntry>.print(prefix: String = "stack") {
    val stack = map { it.destination.route }.toTypedArray().contentToString()
    Log.e("ArrayDeque","$prefix = $stack")
}
