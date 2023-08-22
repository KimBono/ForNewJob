@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.addressbook.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.addressbook.R
import com.example.addressbook.domain.AddressItem
import com.example.addressbook.ui.theme.AddressBookTheme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.navigation.NavController

@Composable
fun MainApp(viewModel: MainViewModel){
    var textName by rememberSaveable { mutableStateOf("") }
    var textPhoneNum by rememberSaveable { mutableStateOf("") }
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main"){
        composable(route = "main"){
            MainScreen(
                viewModel = viewModel,
                textName = textName,
                textPhoneNum = textPhoneNum,
                onNameChanged = {textName = it},
                onPhoneNumChanged ={textPhoneNum = it},
                navController = navController
            )
        }
        composable(route = "detailItemInfo"){
            showDetailItemInfo(
                navController = navController,
                item = viewModel.getDetailItemInfo(),
                onClickFixButton = {
                    navController.navigate("fixItemInfo")
                },
                onClickDeleteButton = {
                    viewModel.deleteItem(it.uid)
                    navController.navigate("main")
                }
            )
        }
        composable(route = "fixItemInfo"){
          fixAddressItemInfo(
              viewModel = viewModel,
              navController = navController,
              item = viewModel.getDetailItemInfo()
          )
        }
    }


}
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    textName : String,
    textPhoneNum : String,
    onNameChanged : (String) -> Unit,
    onPhoneNumChanged : (String)->Unit,
    navController: NavController
    ) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
       TextField(
           value = textName,
           onValueChange = onNameChanged,
           placeholder = {Text("Name")}
       )
       Spacer(modifier = Modifier.width(10.dp))
       TextField(
           value = textPhoneNum,
           onValueChange = onPhoneNumChanged,
           placeholder = {Text("Phone Number")} )

        Row() {
            Button(
                onClick = { viewModel.addItem(AddressItem(textName,textPhoneNum)) },
                content = {Text("추가")})
            Spacer(modifier = Modifier.width(30.dp))
            Button(
                onClick = {
                    var item = viewModel.searchAddressItem(textName)
                    if(item != null){
                        viewModel.setItem(item)
                        navController.navigate("detailItemInfo")
                    }
                          },
                content = {Text("검색")}
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
                        },
                        onClickedIetm = {it->
                            viewModel.setItem(it)
                            navController.navigate("detailItemInfo")
                        }
                    )

                }
            }
        }

    }
}

@Composable
fun addressItemList(
    item : AddressItem,
    onDeletedItem : (item : AddressItem)-> Unit = {},
    onClickedIetm : (item : AddressItem)-> Unit = {}
){
    Row(modifier = Modifier
        .fillMaxWidth()) {
        Icon(painter =
        painterResource(
            id = R.drawable.baseline_delete_24),
            contentDescription = null,
            modifier = Modifier.clickable {
                onDeletedItem(item)
            }
        )
        Spacer(modifier = Modifier.width(20.dp))
        Column(modifier = Modifier.clickable { onClickedIetm(item)}) {
            Text(item.name)
            Text(item.phoneNumber)
        }

    }
}

@Composable
fun showDetailItemInfo(
    navController: NavController,
    item : AddressItem,
    onClickFixButton : () -> Unit,
    onClickDeleteButton : (AddressItem)->Unit
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(){
            Text(text = "이름")
            Text(text = item.name)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(){
            Text(text = "전화번호")
            Text(text = item.phoneNumber)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(){
            Button(
                onClick = {onClickFixButton()},
                content = {Text("수정")}
            )
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = {onClickDeleteButton(item)},
                content = {Text("삭제")}
            )
        }
    }
}



@Composable
fun fixAddressItemInfo(
    viewModel: MainViewModel,
    navController: NavController,
    item : AddressItem
){

    var fixName by rememberSaveable { mutableStateOf(item.name) }
    var fixPhoneNum by rememberSaveable { mutableStateOf(item.phoneNumber) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(value = fixName, onValueChange ={ fixName = it} )
        TextField(value = fixPhoneNum, onValueChange = {fixPhoneNum = it})
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                      viewModel.changePhoneNum(fixName,fixPhoneNum)
                navController.navigate("main")
                      },
            content = {Text("수정")}
        )

    }
}