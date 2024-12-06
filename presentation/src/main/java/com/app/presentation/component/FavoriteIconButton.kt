package com.app.presentation.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.app.presentation.R
import com.app.presentation.theme.space6

@Composable
fun FavoriteIconButton(
    isFavorite: Boolean,
    actionFavorite: () -> Unit,
) {
    IconButton(
        onClick = actionFavorite,
        modifier = Modifier.size(space6)
    ) {
        Icon(
            painter = painterResource(
                id = if (isFavorite) R.drawable.ic_favorite
                else R.drawable.ic_favorite_border
            ),
            contentDescription = "Favorite",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}