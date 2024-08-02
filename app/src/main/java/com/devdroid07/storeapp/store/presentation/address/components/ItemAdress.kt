package com.devdroid07.storeapp.store.presentation.address.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ItemAddress(){
    ElevatedCard(
        shape = RoundedCornerShape(32.dp),
        onClick = { /*TODO*/ })
    {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ){
            Text(text = "Calle 1 x 10 SN", style = MaterialTheme.typography.titleMedium)//caLLE
            Text(text = "9923932", style = MaterialTheme.typography.bodyMedium)//CODIGO POSTAL
            Text(text= "Elias Mena PPech", style = MaterialTheme.typography.bodyMedium)
        }
    }
}