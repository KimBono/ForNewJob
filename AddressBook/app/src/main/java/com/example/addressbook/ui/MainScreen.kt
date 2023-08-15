@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.addressbook.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.addressbook.R
import com.example.addressbook.domain.AddressItem
import com.example.addressbook.ui.theme.AddressBookTheme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@Composable
fun MainScreen(viewModel: MainViewModel) {
    var textName by rememberSaveable { mutableStateOf("") }
    var textPhoneNum by rememberSaveable { mutableStateOf("") }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
       TextField(
           value = textName,
           onValueChange = {textName = it},
           placeholder = {Text("Name")}
       )
       Spacer(modifier = Modifier.width(10.dp))
       TextField(
           value = textPhoneNum,
           onValueChange = {textPhoneNum = it},
           placeholder = {Text("Phone Number")} )

        Row() {
            Button(
                onClick = { textPhoneNum = viewModel.getPhoneNum(textName).toString() },
                content = {Text("search")})
            Spacer(modifier = Modifier.width(30.dp))
            Button(
                onClick = {
                        if(viewModel.getPhoneNum(textName) == null){
                            viewModel.addItem(AddressItem(name = textName, phoneNumber = textPhoneNum))
                        }
                        else{
                            viewModel.changePhoneNum(name = textName, changedPhoneNumber = textPhoneNum)
                        }
                    },
                content = {Text("update")}
            )
        }
        Divider()

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            items(viewModel.items.value){it->
                Column() {
                    addressItemList(
                        item = it,
                        onDeletedItem ={it->
                            viewModel.deleteItem(it.uid)
                        }
                    )

                }
            }
        }

    }
}

@Composable
fun addressInformation(info : String
){
    var text by rememberSaveable { mutableStateOf("") }

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(40.dp)
        .padding(10.dp)
    ) {

        Text(
            text = info,
            fontSize = 15.sp,
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically)
                .width(60.dp)

        )
        Spacer(modifier = Modifier.width(10.dp))
        TextField(value = text, onValueChange = {text = it} )
    }
}

@Composable
fun addressItemList(
    item : AddressItem,
    onDeletedItem : (item : AddressItem)-> Unit = {}
){
    Row(modifier = Modifier
        .fillMaxWidth()) {
        Icon(painter =
        painterResource(
            id = R.drawable.baseline_delete_24),
            contentDescription = null,
            modifier = Modifier.clickable { onDeletedItem(item) }
        )
        Spacer(modifier = Modifier.width(20.dp))
        Column() {
            Text(item.name)
            Text(item.phoneNumber)
        }

    }
}
