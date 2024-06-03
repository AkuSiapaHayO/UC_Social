package com.example.ucsocial
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vp_alpapp.R
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.vp_alpapp.viewmodel.CreateContentViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPostView(

    createContent: CreateContentViewModel,
    context: Context,
    navController: NavController

) {

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var photoOrNot by remember { mutableStateOf(1) }

    var selectedImageUris by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris -> selectedImageUris = uris }
    )

    var headline by rememberSaveable { mutableStateOf("") }
    var image by rememberSaveable { mutableStateOf("") }
    var contentText by rememberSaveable { mutableStateOf("") }
    var categoryId by rememberSaveable { mutableStateOf(1) }
    var filled1 by remember { mutableStateOf(false) }
    var filled2 by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F3F3))
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp) // Use padding for left and right margins
                .clip(RoundedCornerShape(16.dp)) // Adjust the corner radius as needed
                .background(Color(0xFFFBFBFB))
                .padding(vertical = 4.dp),
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profilepic),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                    TextField(
                        value = headline,
                        onValueChange = {
                            headline = it
                            filled1 = true
                        },
                        placeholder = { Text("What's New...") },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            cursorColor = Color(0xFF8F8F8F),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            containerColor = Color.Transparent,
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        textStyle = TextStyle(fontSize = 18.sp, color = Color.Black)
                    )
                }

                // Content
                TextField(
                    value = contentText,
                    onValueChange = {
                        contentText = it
                        filled2 = true
                    },
                    placeholder = { Text("Your Content Goes Here...") },
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color(0xFF8F8F8F),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        containerColor = Color.Transparent,
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textStyle = TextStyle(fontSize = 18.sp, color = Color.Black)
                )

                // IMG URL
//                TextField(
//                    value = image,
//                    onValueChange = { image = it },
//                    label = { Text("Image URL") },
//                    colors = TextFieldDefaults.textFieldColors(
//                        cursorColor = Color(0xFF8F8F8F),
//                        focusedIndicatorColor = Color.Transparent,
//                        unfocusedIndicatorColor = Color.Transparent,
//                        containerColor = Color.Transparent,
//                    ),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp),
//                    textStyle = TextStyle(fontSize = 12.sp, color = Color.Black)
//                )

//
                if (photoOrNot == 1) {


                    Button(
                        onClick = {
                            singlePhotoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }, colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent, // Set to transparent
                            contentColor = Color.Black // Set the text color
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.upload),
                                contentDescription = "Profile Picture",
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                            )
                            Text(text = "Upload A Photo", color = Color.Black)
                        }
                    }
                }
                //
                LazyColumn() {
                    item {
                        AsyncImage(
                            model = selectedImageUri,
                            contentDescription = null,
                            modifier = Modifier.size(100.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }


                // Category ID
                var categoryId by remember { mutableStateOf(1) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            // Toggle between 1 and 2
                            categoryId = if (categoryId == 1) 2 else 1
                        }
                ) {
                    Text(
                        "Category ID (Click To Switch): ${if (categoryId == 1) "News" else "Committee"}",
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            // Toggle between 1 and 2
                            photoOrNot = if (photoOrNot == 1) 2 else 1
                        }
                ) {
                    Text(
                        "Use Image (Click To Switch): ${if (photoOrNot == 1) "With Image" else "No Image"}",
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }
                Row {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                    ) {

                        if (filled1 && filled2) {
                            if (photoOrNot == 1) {
                                Button(
                                    onClick = {
                                        selectedImageUri?.let {
                                            createContent.uploadAndCreateContent(
                                                headline = headline,
                                                image = it,
                                                content_text = contentText,
                                                category_id = categoryId,
                                                context = context,
                                                navController
                                            )
                                        }


                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    colors = ButtonDefaults.buttonColors(Color(0xFFF89715))
                                ) {
                                    Text(text = "POST")

                                }
                            } else {
                                Button(
                                    onClick = {

                                        createContent.createContent(
                                            headline,
                                            content_text = contentText,
                                            categoryId.toString(),
                                            navController
                                        )

                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    colors = ButtonDefaults.buttonColors(Color(0xFFF89715))
                                ) {
                                    Text(text = "POST")

                                }
                            }
                        } else {
                            Button(
                                onClick = {

                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                colors = ButtonDefaults.buttonColors(Color.LightGray)
                            ) {
                                Text(text = "Headline & Content Required!")

                            }
                        }
                    }
                }

            }


        }
//        BottomNavigationBar(navController = navController)
    }


}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AddPostPreview() {

    val createContent: CreateContentViewModel = viewModel()

//    AddPostView(createContent)
}