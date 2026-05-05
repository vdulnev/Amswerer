package com.example.amswerer

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import kotlinx.serialization.Serializable

@Serializable
data object CameraRoute : NavKey

@Serializable
data object ResultRoute : NavKey

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(CameraRoute)
    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<CameraRoute> {
                CameraScreen(
                    modifier = modifier,
                    onPhotoCaptured = { bitmap ->
                        capturedBitmap = bitmap
                        backStack.add(ResultRoute)
                    },
                )
            }
            entry<ResultRoute> {
                ResultScreen(
                    modifier = modifier,
                    bitmap = capturedBitmap,
                    onAskAnother = {
                        capturedBitmap = null
                        backStack.removeLastOrNull()
                    },
                )
            }
        },
    )
}