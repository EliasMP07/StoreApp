

package com.devdroid07.storeapp.core.presentation.designsystem.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.components.animation.animateAttention
import com.devdroid07.storeapp.core.presentation.designsystem.components.card.utils.CardDesign
import com.devdroid07.storeapp.core.presentation.designsystem.components.card.utils.CardIssuer
import com.devdroid07.storeapp.core.presentation.designsystem.components.card.utils.IssuerFinder

@Composable
fun CreditCard(
    modifier: Modifier = Modifier,
    cardNumber: String,
    cvv: String,
    nameHeadline: String,
    dateExpire: String,
) {
    val bankCardAspectRatio = 1.586f

    val card = when (IssuerFinder.detect(cardNumber)) {
        CardIssuer.VISA -> CardDesign(
            logo = R.drawable.visa,
            color = Color(0xFF00D1EC),
            textColor = Color.Black
        )
        CardIssuer.MASTERCARD -> {
            CardDesign(
                logo = R.drawable.ic_mastercard,
                color = Color(0xFF404242),
                textColor = Color.White,
            )
        }
        CardIssuer.OTHER -> {
            CardDesign(
                logo = null,
                color = Color(0xFFC9CCCC),
                textColor = Color.Black
            )
        }
        CardIssuer.EMPTY -> {
            CardDesign(
                logo = null,
                color = Color(0xFFC9CCCC),
                textColor = Color.Black
            )
        }
    }

    Card (
        modifier = modifier,
        elevation = CardDefaults.cardElevation(20.dp)
    ){
        Box(
            modifier = Modifier
                .animateAttention()
                .background(color = card.color)
                .fillMaxWidth()
                .aspectRatio(bankCardAspectRatio)
        ) {
            card.logo?.let {
                Image(
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.TopEnd)
                        .padding(end = 16.dp),
                    painter = painterResource(id = it),
                    contentDescription = stringResource(R.string.content_description_img_logo_issuer)
                )
            }
            if (cardNumber.isNotEmpty()) {
                BankCardNumber(
                    cardNumber = cardNumber,
                    textColor = card.textColor
                )
            }
            if (nameHeadline.isNotEmpty()) {
                SpaceWrapper(
                    modifier = Modifier.align(Alignment.TopStart),
                    space = 32.dp,
                    top = true,
                    left = true
                ) {
                    BankCardLabelAndText(
                        label = stringResource(R.string.name_headline_card),
                        text = nameHeadline,
                        textColor = card.textColor
                    )
                }
            }
            SpaceWrapper(
                modifier = Modifier.align(Alignment.BottomStart),
                space = 32.dp,
                bottom = true,
                left = true
            ) {
                Row {
                    if (dateExpire.isNotEmpty()) {
                        BankCardLabelAndText(
                            label = stringResource(R.string.expires),
                            text = dateExpire,
                            textColor = card.textColor
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    if (cvv.isNotEmpty()) {
                        BankCardLabelAndText(
                            label = stringResource(R.string.cvv),
                            text = cvv,
                            textColor = card.textColor
                        )
                    }
                }
            }

        }
    }

}
