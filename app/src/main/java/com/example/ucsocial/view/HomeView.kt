package com.example.ucsocial

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.ui.text.AnnotatedString
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.vp_alpapp.DataStore
import com.example.vp_alpapp.ListScreen
import com.example.vp_alpapp.R
import com.example.vp_alpapp.model.Content
import com.example.vp_alpapp.model.Pengguna
import com.example.vp_alpapp.ui.theme.orangelight
import com.example.vp_alpapp.viewmodel.ExploreViewModel
import com.example.vp_alpapp.viewmodel.HomeViewModel
import kotlin.random.Random

@Composable
fun FilterMenu(onTabSelected: (Boolean) -> Unit, isNewsSelected: MutableState<Boolean>) {
    var isNewsSelected by remember { mutableStateOf(true) }
    var isCommitteesSelected by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.End
    ) {
        item {
            Row(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .padding(8.dp)
            ) {
                Tab("News", R.drawable.news, isNewsSelected) {
                    isNewsSelected = true
                    isCommitteesSelected = false
                    onTabSelected(isNewsSelected)
                }
                Tab("Committees", R.drawable.comit, isCommitteesSelected) {
                    isNewsSelected = false
                    isCommitteesSelected = true
                    onTabSelected(isNewsSelected)
                }
            }
        }
    }
}


@Composable
fun RowScope.Tab(
    text: String,
    iconRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .padding(8.dp)
            .clickable { onClick() }
            .background(
                color = if (isSelected) orangelight else Color.Transparent,
                shape = MaterialTheme.shapes.medium
            )
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.Black else Color.Gray,
                modifier = Modifier.clickable { onClick() }
            )
        }
    }
}


@Composable
fun Post(

    user: Pengguna,
    content: Content,
    exploreViewModel: ExploreViewModel,
    navController: NavController,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp) // Use padding for left and right margins
            .clip(RoundedCornerShape(16.dp)) // Adjust the corner radius as needed
            .background(Color(0xFFFBFBFB))
            .padding(vertical = 4.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp) // Use padding for left and right margins
                .padding(vertical = 4.dp),
        ) {
            // Top Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Profile Picture and Name
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    var gambaruser =
                        "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"

                    if (content.user.photo == null) {
                        LoadProfileImage(url = gambaruser, navController, content, user)
                    } else {
                        gambaruser = content.user.photo
                        LoadProfileImage(url = gambaruser, navController, content, user)
                    }
                    if (user.id == content.user.id) {
                        Text(
                            text = content.user.name,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.clickable {

                                navController.navigate(ListScreen.Profile.name)
                            }
                        )
                    } else {
                        Text(
                            text = content.user.name,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.clickable {

                                navController.navigate(ListScreen.Profile2.name + "/" + content.user.id.toString())
                            }
                        )
                    }


                }


                // Three Dot Menu
                if (user != null && user.id == content.user.id) {

                    var showDialog by remember { mutableStateOf(false) }

                    IconButton(
                        onClick = {
                            showDialog = true
                        },
                        modifier = Modifier
                            .size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.Black
                        )
                    }

                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = {
                                // Handle dialog dismissal if needed
                                showDialog = false
                            },
                            title = {
                                Text(text = "Delete Post")
                            },
                            text = {
                                Text(text = "Are you sure you want to delete this post?")
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        exploreViewModel.delete(content.id.toString())
                                        showDialog = false
                                        navController.navigate(ListScreen.Profile.name)
                                    }
                                ) {
                                    Text(text = "Yes")
                                }
                            },
                            dismissButton = {
                                Button(
                                    onClick = {
                                        showDialog = false
                                    }
                                ) {
                                    Text(text = "No")
                                }
                            }
                        )
                    }

                }

            }

            // Post Title
            Text(
                text = content.headline,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .clickable {

                        navController.navigate(ListScreen.CommentView.name + "/" + content.id.toString())

                    }
            )

            if (content.image == null) {

            } else {
                LoadImageCustom(
                    url = content.image, modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(ListScreen.CommentView.name + "/" + content.id.toString())
                        }, contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Post Content
            ClickableTextWithUrls(content.contentText, navController)
            // Bottom Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Like, Comment, Share
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.favorite),
//                        contentDescription = null,
//                        modifier = Modifier.size(24.dp)
//                    )
//                    Spacer(modifier = Modifier.width(16.dp))
                    Image(
                        painter = painterResource(id = R.drawable.comment),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {

                                navController.navigate(ListScreen.CommentView.name + "/" + content.id.toString())

                            }
                    )
                    ShareButton()
//                    Spacer(modifier = Modifier.width(16.dp))
//                    Image(
//                        painter = painterResource(id = R.drawable.share),
//                        contentDescription = null,
//                        modifier = Modifier.size(24.dp)
//                    )
                }

                if (user != null && user.id == content.user.id) {


                    IconButton(
                        onClick = {

                            navController.navigate(ListScreen.EditKonten.name + "/" + content.id.toString())

                        },
                        modifier = Modifier.size(24.dp)

                    ) {

                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "edit",
                            tint = Color.Black
                        )
                    }
                }
            }


        }
    }
}
@Composable
fun ShareButton() {
    val context = LocalContext.current

    // Create a shareable content
    val shareableContent = "Check Out This Cool New App! UC Social!"

    // Create an Intent to share the content
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareableContent)
    }

    // Create a launcher for the share action
    val shareLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            // Handle the result if needed
        }

    // Create a button to trigger the share action
    IconButton(onClick = {
        // Start the share activity
        shareLauncher.launch(Intent.createChooser(shareIntent, "Share via"))
    }) {
        Image(
            painter = painterResource(id = R.drawable.share),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}
@Composable
fun TopBar(
    homeViewModel: HomeViewModel,
    navController: NavController,
    user: Pengguna,
    dataStore: DataStore
) {
    var isLogoutVisible by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .padding(16.dp)
            .clickable {
                isLogoutVisible = !isLogoutVisible
            },
        verticalAlignment = Alignment.Top // Set verticalAlignment to Alignment.Top
    ) {
        Text(
            text = user.name,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight(800),
                color = Color(0xFF000000),
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(id = R.drawable.arrowdown),
            contentDescription = "Arrow Down",
            modifier = Modifier
                .width(24.dp)
        )
    }

    // PopupMenu triggered by the click on the Image
    DropdownMenu(
        expanded = isLogoutVisible,
        onDismissRequest = { isLogoutVisible = false },
        modifier = Modifier.background(Color.Gray),
        offset = DpOffset((30).dp, (-100).dp) // Adjust the offset as needed
    ) {
        // Menu item for logout
        DropdownMenuItem(
            text = { Text(text = "Logout") },
            onClick = {
                isLogoutVisible = false
                homeViewModel.logout(navController = navController, dataStore)
                // Handle logout action here
            }
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun Home(
    navController: NavController,
    homeViewModel: HomeViewModel,
    user: Pengguna,
    exploreViewModel: ExploreViewModel,
    listData: List<Content>,
    dataStore: DataStore
) {
    var isNewsSelected by remember { mutableStateOf(true) }
    var lazyListState = rememberLazyListState()
    Column(
        modifier = Modifier
            .background(Color(0xFFF3F3F3))
    ) {
        TopBar(homeViewModel, navController = navController, user, dataStore = dataStore)
        FilterMenu(
            onTabSelected = { isNewsSelected = it },
            isNewsSelected = mutableStateOf(isNewsSelected)
        )
        // if newsselected is true print out the category = 1
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState
        ) {
            if (listData != null) {
                val reversedList = listData.reversed()
                items(reversedList.size) { index ->
                    if (user != null) {
                        if (reversedList[index].userId != user.id) {
                            // Check if "News" is selected
                            if (isNewsSelected && reversedList[index].categoryId == 1) {
                                Spacer(modifier = Modifier.height(20.dp))
                                Post(
                                    content = reversedList[index],
                                    user = user,
                                    exploreViewModel = exploreViewModel,
                                    navController = navController
                                )
                            }
                            // Check if "Committees" is selected
                            else if (!isNewsSelected && reversedList[index].categoryId == 2) {
                                Spacer(modifier = Modifier.height(20.dp))
                                Post(
                                    content = reversedList[index],
                                    user = user,
                                    exploreViewModel = exploreViewModel,
                                    navController = navController
                                )
                            }
                        }
                    }
                }
            }
        }
        LaunchedEffect(isNewsSelected) {
            // Reset scroll position to top when isNewsSelected changes
            lazyListState.scrollToItem(0)
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LoadProfileImage(
    url: String? = null,
    navController: NavController,
    content: Content,
    user: Pengguna
) {

    GlideImage(
        model = url
            ?: "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
        contentDescription = "",
        modifier = Modifier
            .height(34.dp)
            .width(34.dp)
            .clip(CircleShape)
            .clickable {
                if (content.user.id != user.id) {
                    navController.navigate(ListScreen.Profile2.name + "/" + content.user.id.toString())
                } else {
                    navController.navigate(ListScreen.Profile.name)
                }
            },
        contentScale = ContentScale.Crop,
    )

}

@Composable
fun ClickableTextWithUrls(
    text: String,
    navController: NavController
) {
    val annotatedText = buildAnnotatedString {
        // Define a simple logic to find URLs in the text
        val urlRegex = """\b(?:https?|ftp)://\S+""".toRegex()
        urlRegex.findAll(text).forEach { result ->
            val start = result.range.first
            val end = result.range.last + 1
            addStyle(
                style = SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline),
                start = start,
                end = end
            )
            addStringAnnotation("URL", result.value, start, end)
        }
        append(text)
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            val url = annotatedText.getStringAnnotations("URL", offset, offset)
                .firstOrNull()?.item

            url?.let {
                // Open the URL in a external browser
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                navController.context.startActivity(intent)
            }
        },
        modifier = Modifier.padding(bottom = 8.dp),
        style = LocalTextStyle.current.copy(color = Color.Black)
    )
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun HomeView() {
//    Home()
}