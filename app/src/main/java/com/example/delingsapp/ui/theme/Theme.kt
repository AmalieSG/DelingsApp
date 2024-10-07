package com.example.delingsapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable

// Definer LightColorScheme eller DarkColorScheme som passer prosjektet ditt.
private val LightColorScheme = lightColorScheme(
    // Legg til dine fargevalg her, f.eks. primary, secondary, background osv.
)

@Composable
fun MyAppTheme(
    content: @Composable () -> Unit
) {
    // Bruk standard Material3 typografi eller tilpasset om nødvendig
    val typography = Typography()

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = typography,  // Dette er nå en instans av Typography, ikke selve klassen
        content = content
    )
}
