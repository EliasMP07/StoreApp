package com.devdroid07.storeapp.store.presentation.payment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.devdroid07.storeapp.core.presentation.designsystem.EditIcon
import com.devdroid07.storeapp.core.presentation.designsystem.StoreAppTheme

@Composable
fun PaymentScreenRoot(){

}

@Composable
private fun PaymentScreen(

){
    Scaffold {paddingValue ->
        Column(
            modifier = Modifier.padding(paddingValue)
        ) {
            Row (
                modifier = Modifier.fillMaxWidth()
            ){
                Text(text = "Shipping Address")
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = EditIcon ,
                        contentDescription = ""
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PaymentScreenPreview(){
    StoreAppTheme {
        PaymentScreen()
    }
}