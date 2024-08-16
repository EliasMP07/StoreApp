@file:OptIn(ExperimentalMaterial3Api::class)

package com.devdroid07.storeapp.core.presentation.designsystem.components.utils

import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue

val BottomSheetScaffoldState.isVisibleBottomSheet: Boolean get() = bottomSheetState.currentValue == SheetValue.Expanded