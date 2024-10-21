package com.app.dogs.ui.component.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun TextHeadlineMediumPrimary(text: String, modifier: Modifier = Modifier) {
    TextHeadlineMedium(
        text = text,
        modifier = modifier,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun TextHeadlineMedium(text: String, modifier: Modifier, color: Color) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.headlineMedium,
        color = color
    )
}