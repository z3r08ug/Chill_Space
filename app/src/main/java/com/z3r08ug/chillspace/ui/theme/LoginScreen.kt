package com.z3r08ug.chillspace.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.z3r08ug.chillspace.R

@Composable
fun LoginScreen() {
    ChillSpaceTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Scaffold(topBar = {
                TopAppBar {
                    Text(text = "Login", fontSize = 30.sp)
                }
            },
                content = {
                    Box(modifier = Modifier.fillMaxSize()) {
                        val image: Painter = painterResource(id = R.drawable.chill_space)
                        Image(painter = image, contentDescription = "Chill Space Logo", modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                        )

                        Button(onClick = { /*TODO*/ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .padding(16.dp)
                                .padding(bottom = 40.dp),
                        shape = RoundedCornerShape(50)) {
                            Text(text = "Login", fontSize = 24.sp)
                        }
                        
                        Text(text = "Don't have an account? Create one here.",
                        modifier = Modifier.align(Alignment.BottomCenter)
                            .padding(16.dp),
                        textAlign = TextAlign.Center,
                        color = Color.Blue)
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    LoginScreen()
}
