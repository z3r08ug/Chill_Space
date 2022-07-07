package com.z3r0_8ug.ui_common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.z3r0_8ug.ui_common.model.Photo
import com.z3r0_8ug.ui_common.theme.AppTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostList(
    photos: List<Photo>
) {
    LazyColumn(
        Modifier.background(AppTheme.colors.background),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(
            items = photos,
            itemContent = { photo ->
                Card(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                            ) {
                                Avatar(
                                    monogram = "TU",
                                    color = AppTheme.colors.secondary,
                                    size = 40.dp
                                )
                            }
                            Text(
                                text = "Username",
                                color = AppTheme.colors.almostBlack,
                                modifier = Modifier
                                    .padding(8.dp),
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AsyncImage(
                                    model = photo.url,
                                    contentDescription = null,
                                    alignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(200.dp)
                                        .padding(8.dp)
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}