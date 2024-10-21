package com.app.dogs.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.app.commondomain.model.BreedModel
import com.app.dogs.ui.theme.space8

@Composable
fun AutoCompleteText(
    @StringRes label: Int,
    showDropDown: Boolean,
    filterOpts: List<BreedModel>,
    onValueChange: (String) -> Unit,
    onItemSelect: (BreedModel) -> Unit = {},
    onDismissMenu: () -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val textState = remember { mutableStateOf(TextFieldValue()) }
    Column {
        TextField(
            value = textState.value,
            onValueChange = {
                textState.value = it
                onValueChange(it.text)
            },
            placeholder = { Text(stringResource(id = label)) },
            shape = RoundedCornerShape(space8),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (textState.value.text.isEmpty()) onDismissMenu()
                        else textState.value = textState.value.copy(text = "")
                    },
                    modifier = Modifier.clearAndSetSemantics { }) {
                    Icon(
                        Icons.Filled.Clear,
                        "Clear",
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(space8)
                )
        )
        DropdownMenu(
            expanded = showDropDown,
            onDismissRequest = onDismissMenu,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 200.dp),
            properties = PopupProperties(focusable = false, usePlatformDefaultWidth = true)
        ) {
            filterOpts.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        keyboardController?.hide()
                        onItemSelect(option)
                        textState.value = textState.value.copy(
                            text = option.displayName,
                            selection = TextRange(option.displayName.length)
                        )
                    },
                    text = {
                        Text(
                            text = option.displayName,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                )
            }
        }
    }
}