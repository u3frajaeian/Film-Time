package io.filmtime.tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.tv.material3.Surface
import dagger.hilt.android.AndroidEntryPoint
import io.filmtime.tv.ui.theme.FilmTimeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      FilmTimeTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
        ) {
          FilmTimeTvApp()
        }
      }
    }
  }
}
