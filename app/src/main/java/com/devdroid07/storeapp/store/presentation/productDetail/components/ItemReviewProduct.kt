@file:OptIn(ExperimentalGlideComposeApi::class)

package com.devdroid07.storeapp.store.presentation.productDetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.devdroid07.storeapp.auth.presentation.register.components.PhotoProfile
import com.devdroid07.storeapp.core.presentation.designsystem.Dimensions
import com.devdroid07.storeapp.core.presentation.designsystem.components.StarRating
import com.devdroid07.storeapp.core.presentation.designsystem.animation.animateAttention
import com.devdroid07.storeapp.store.domain.model.Review

@Composable
fun ItemReviewProduct(
    review: Review,
    spacing: Dimensions,
) {
    ElevatedCard(
        modifier = Modifier
            .padding(spacing.spaceMedium)
            .clip(RoundedCornerShape(spacing.spaceLarge))
            .animateAttention()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        topStart = spacing.spaceLarge,
                        bottomStart = spacing.spaceLarge
                    )
                )
                .background(MaterialTheme.colorScheme.background),
        ) {
            PhotoProfile(
                modifier = Modifier.padding(spacing.spaceSmall),
                image = review.imageAuthor,
                size = 40.dp
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(spacing.spaceSmall)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "${review.author} | ",
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = MaterialTheme.colorScheme.onBackground.copy(
                                alpha = 0.5f
                            )
                        )
                    )
                    Text(
                        text = review.created,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground.copy(
                                alpha = 0.5f
                            )
                        )
                    )
                }
                Spacer(modifier = Modifier.height(spacing.spaceSmall))
                StarRating(
                    iconSize = 20.dp,
                    isClickable = false,
                    rating = review.rating
                ) {

                }
                Spacer(modifier = Modifier.height(spacing.spaceSmall))
                Text(
                    text = review.comment,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        }
    }
}