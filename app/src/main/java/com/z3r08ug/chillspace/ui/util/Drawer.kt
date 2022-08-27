package com.z3r08ug.chillspace.ui.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.z3r08ug.chillspace.R


@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    onDestinationClicked: (route: String) -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(start = 24.dp, top = 48.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.chill_space),
            contentDescription = "App icon",
            modifier = Modifier.size(100.dp)
        )
        screens.forEach { screen ->
            Spacer(Modifier.height(24.dp))
            Text(
                text = screen.title,
                style = MaterialTheme.typography.h4,
                modifier = Modifier.clickable {
                    onDestinationClicked(screen.route)
                },
                fontSize = 18.sp
            )
        }

        Spacer(Modifier.height(24.dp))
        Text(
            text = "Logout",
            style = MaterialTheme.typography.h4,
            fontSize = 18.sp,
            modifier = Modifier.clickable {
                val auth = FirebaseAuth.getInstance()
                auth.signOut()
                onDestinationClicked(Screen.NewLoginScreen.route)
            }
        )
    }
}