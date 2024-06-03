package com.example.ucsocial.view

import androidx.compose.foundation.Image
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ucsocial.model.Content
import com.example.ucsocial.model.Pengguna
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.ucsocial.ListScreen
import com.example.ucsocial.Post
import com.example.vp_alpapp.R
import com.example.vp_alpapp.viewmodel.ExploreViewModel
import com.example.vp_alpapp.viewmodel.ProfileViewModel


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LoadImage(url: String? = null) {

    GlideImage(
        model = url
            ?: "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
        contentDescription = "",
        modifier = Modifier.height(350.dp),
        contentScale = ContentScale.Crop,
        colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply {
            setToScale(
                0.5f,
                0.5f,
                0.5f,
                1f
            )
        })
    )

}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun LoadImageCustom(
    url: String? = null,
    modifier: Modifier = Modifier,
    contentScale: ContentScale
) {
    Box(
        modifier = Modifier
    ) {
        GlideImage(
            model = url
                ?: "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
            contentDescription = null,
            contentScale = contentScale,
            modifier = modifier
        )
    }
}

@Composable
fun Profile(
    navController: NavController,
    user: Pengguna,
    listku: List<Content>?,
    profileViewModel: ProfileViewModel,
    exploreViewModel: ExploreViewModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F3F3))
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .background(Color.Gray)
            ) {
                var gambaruser =
                    "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"

                if (user.photo == null) {
                    LoadImage(url = gambaruser)
                } else {
                    gambaruser = user.photo
                    LoadImage(url = gambaruser)
                }

                Column() {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        var showMenu by remember { mutableStateOf(false) }

                        // Image with onClick listener to show the popup menu
                        Image(
                            painter = painterResource(id = R.drawable.more),
                            contentDescription = "",
                            modifier = Modifier
                                .size(50.dp)
                                .padding(top = 16.dp)
                                .clickable { showMenu = true }
                        )

                        // PopupMenu triggered by the click on the Image
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false },
                            modifier = Modifier.background(Color.Gray),
                            offset = DpOffset((-8).dp, 0.dp) // Adjust the offset as needed
                        ) {
                            // Menu item for logout
                            DropdownMenuItem(
                                text = { Text(text = "Logout") },
                                onClick = {
                                    showMenu = false
                                    profileViewModel.logout(navController)
                                    // Handle logout action here
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(120.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column() {
                            Text(
                                text = user.name,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            var teks = "Your Bio"

                            if (user.bio == null || user.bio.equals("{null}")) {
                                teks =
                                    "Share a bit about yourself. Let others know who you are, what you're passionate about. Highlight your interests, experiences, and anything else that you think defines you. This is your space to express yourself."
                            } else {
                                teks = user.bio
                            }
                            Text(
                                text = teks,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 100.dp)
                    .background(Color(0xFFF89715), RoundedCornerShape(16.dp))
                    .border(1.dp, Color(0xFFF89715), RoundedCornerShape(16.dp))
                    .padding(12.dp)
                    .clip(RoundedCornerShape(20.dp))
            ) {
            }
        }
        // FOR LOOP HERE
        if (listku != null) {
            val reversedList = listku.reversed()
            items(reversedList.size) { index ->
                Spacer(modifier = Modifier.height(20.dp))
                Post(
                    content = reversedList[index],
                    user = user,
                    exploreViewModel = exploreViewModel,
                    navController = navController
                )
            }
        }
        if (listku != null) {
            if (listku.isEmpty()) {
                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                    )
                    Text(
                        text = "No Posts Yet!",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp) // Adjust the padding as needed
                    )
                }
            }
        }
        item { Spacer(modifier = Modifier.height(20.dp)) }

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
//    Profile()
}

//@Composable
//fun Profile(
//
//    navController: NavController,
//    user: Pengguna,
//    listku: List<Content>?,
//    exploreViewModel: ExploreViewModel
//
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
//            .background(Color(0xFFF3F3F3))
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(1000.dp)
//                .verticalScroll(rememberScrollState())
//        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .align(Alignment.TopStart)
//                    .height(350.dp)
//                    .background(Color.Gray) // GANTI IMAGE BACKGROUND
//            ) {
//
//                var gambaruser = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
//
//                if ( user.photo == null) {
//                    LoadImage(url = gambaruser)
//
//                }
//
//                else {
//
//                    gambaruser = user.photo.toString()
//                    LoadImage(url = gambaruser)
//                }
//
//
//                Column() {
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.End,
//                    ) {
//                        Image(
//                            painter = painterResource(id = R.drawable.more),
//                            contentDescription = "",
//                            modifier = Modifier
//                                .size(50.dp)
//                                .padding(top = 16.dp)
//                        )
//                    }
//                    Spacer(modifier = Modifier.height(120.dp))
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 12.dp),
//                        horizontalArrangement = Arrangement.Center,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Column() {
//                            Text(
//                                text = user.name,
//                                color = Color.White,
//                                fontWeight = FontWeight.Bold,
//                                fontSize = 20.sp
//                            )
//                            var teks = "Your Bio";
//
//                            if (user.bio == null) {
//
//                                teks = "Share a bit about yourself. Let others know who you are, what you're passionate about. Highlight your interests, experiences, and anything else that you think defines you. This is your space to express yourself."
//                            }
//                            else {
//                                teks = user.bio
//                            }
//                            Text(
//
//                                text= teks,
//                                color = Color.White
//                            )
//                        }
//                    }
//                }
//            }
//            Box(
//                modifier = Modifier
//                    .height(680.dp)
//                    .align(Alignment.BottomStart)
//            ) {
//                Column {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 100.dp)
//                            .background(Color(0xFFF89715), RoundedCornerShape(16.dp))
//                            .border(1.dp, Color(0xFFF89715), RoundedCornerShape(16.dp))
//                            .padding(12.dp)
//                            .clip(RoundedCornerShape(20.dp))
//                    ) {
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(horizontal = 12.dp).clickable {
//                                    navController.navigate(ListScreen.EditProfile.name)
//                                },
//                            horizontalArrangement = Arrangement.Center,
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Text(
//                                text = "Edit",
//                                color = Color.White,
//                                fontSize = 20.sp,
//                                fontWeight = FontWeight.Bold,
//
//                                )
//                            Image(
//                                painter = painterResource(id = R.drawable.arrdownwhite),
//                                contentDescription = "",
//                                modifier = Modifier
//                                    .size(20.dp)
//                            )
//                        }
//                    }
//                    Spacer(modifier = Modifier.height(20.dp))
//                    Column {
////                        Row(
////                            modifier = Modifier
////                                .fillMaxWidth()
////                                .padding(horizontal = 30.dp),
////                            horizontalArrangement = Arrangement.Center,
////                            verticalAlignment = Alignment.CenterVertically
////                        ) {
////                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
////                                Text(
////                                    text = "1000", // POST AMMOUNT
////                                    fontSize = 18.sp,
////                                    textAlign = TextAlign.Center,
////                                    fontWeight = FontWeight(400),
////                                )
////                                Text(
////                                    text = "Posts",
////                                    fontSize = 12.sp,
////                                    textAlign = TextAlign.Center,
////                                    color = Color(0xFF7D7D7D),
////                                    fontWeight = FontWeight(400),
////                                )
////                            }
////                            Spacer(modifier = Modifier.width(60.dp))
////                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
////                                Text(
////                                    text = "1000", // FOllowers AMMOUNT
////                                    fontSize = 18.sp,
////                                    textAlign = TextAlign.Center,
////                                    fontWeight = FontWeight(400),
////                                )
////                                Text(
////                                    text = "Followers",
////                                    fontSize = 12.sp,
////                                    textAlign = TextAlign.Center,
////                                    color = Color(0xFF7D7D7D),
////                                    fontWeight = FontWeight(400),
////                                )
////                            }
////                            Spacer(modifier = Modifier.width(60.dp))
////                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
////                                Text(
////                                    text = "1000", // FOLLOWING AMMOUNT
////                                    fontSize = 18.sp,
////                                    textAlign = TextAlign.Center,
////                                    fontWeight = FontWeight(400),
////
////                                    )
////                                Text(
////                                    text = "Following",
////                                    fontSize = 12.sp,
////                                    textAlign = TextAlign.Center,
////                                    color = Color(0xFF7D7D7D),
////                                    fontWeight = FontWeight(400),
////
////                                    )
////                            }
////                        }
//                        Spacer(modifier = Modifier.height(5.dp))
//                        Divider(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(20.dp)
//                        )
//                        Spacer(modifier = Modifier.height(5.dp))
////                        Row(
////                            modifier = Modifier
////                                .fillMaxWidth()
////                                .padding(horizontal = 30.dp),
////                            horizontalArrangement = Arrangement.Center,
////                            verticalAlignment = Alignment.CenterVertically
////                        ) {
////                            Image(
////                                painter = painterResource(id = R.drawable.posts),
////                                contentDescription = "",
////                                modifier = Modifier
////                                    .size(28.dp)
////                            )
////                            Spacer(modifier = Modifier.width(20.dp))
////                            Image(
////                                painter = painterResource(id = R.drawable.videos),
////                                contentDescription = "",
////                                modifier = Modifier
////                                    .size(28.dp)
////                            )
////                            Spacer(modifier = Modifier.width(20.dp))
////                            Image(
////                                painter = painterResource(id = R.drawable.tagged),
////                                contentDescription = "",
////                                modifier = Modifier
////                                    .size(28.dp)
////                            )
////                        }
//                    }
//
//                    //FORLOOP ND SINI YA NGAB
//                    if (listku != null) {
//                        repeat(listku.size) {
//                            Spacer(modifier = Modifier.height(20.dp))
//
//                            Post(content = listku[it], user = user, exploreViewModel = exploreViewModel, navController = navController)
//
//                        }
//                    }
//                    else {
//
//                    }
//
//                }
//            }
//        }
//    }
//
//}