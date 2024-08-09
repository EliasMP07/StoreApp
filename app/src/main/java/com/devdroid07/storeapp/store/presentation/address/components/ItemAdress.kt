package com.devdroid07.storeapp.store.presentation.address.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.DeleteIcon
import com.devdroid07.storeapp.core.presentation.designsystem.Dimensions
import com.devdroid07.storeapp.core.presentation.designsystem.EditIcon
import com.devdroid07.storeapp.store.domain.model.Address

@Composable
fun ItemAddress(
    address: Address = Address(),
    onClick: (Int) -> Unit = {},
    onDeleteClick: (Int) -> Unit,
    onEditClick: (String) -> Unit,
    spacing: Dimensions = Dimensions(),
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier,
        shape = RoundedCornerShape(spacing.spaceLarge),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.onBackground
        ),
        onClick = { onClick(address.id) })
    {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .animateContentSize()
                    .fillMaxWidth()
                    .padding(spacing.spaceMedium),
                verticalArrangement = Arrangement.spacedBy(spacing.spaceSmall)
            ) {
                Text(
                    text = address.street,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(
                        id = R.string.item_adreess_template,
                        address.postalCode,
                        address.state,
                        address.mayoralty
                    ),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(
                        id = R.string.phone_template,
                        address.phoneNumber
                    ),
                    style = MaterialTheme.typography.bodyMedium
                )

            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.TopEnd)
            ) {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = "Localized description"
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(
                        text = { Text("Editar") },
                        onClick = {
                            expanded = false
                            onEditClick(address.id.toString())
                        },
                        leadingIcon = {
                            Icon(
                                EditIcon,
                                contentDescription = null
                            )
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Eliminar") },
                        onClick = {
                            expanded = false
                            onDeleteClick(address.id)
                        },
                        leadingIcon = {
                            Icon(
                                DeleteIcon,
                                contentDescription = null
                            )
                        }
                    )
                }
            }

        }
    }

}