package com.z3r08ug.chillspace.ui.theme

import android.app.Activity
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.firebase.auth.FirebaseAuth
import com.z3r08ug.chillspace.R
import com.z3r08ug.chillspace.Screen
import com.z3r08ug.chillspace.utils.FirebaseUtils

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(navController: NavHostController?, arguments: Bundle?, auth: FirebaseAuth?, activity: Activity?) {
    ChillSpaceTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.DarkGray
        ) {
            Scaffold(topBar = {
                TopAppBar(backgroundColor = MaterialTheme.colors.secondary) {
                    Text(text = "", fontSize = 30.sp)
                }
            },
                content = {
                    var emailText by rememberSaveable { mutableStateOf("") }
                    var passwordText by rememberSaveable { mutableStateOf("") }
                    val isValid =
                        (emailText.count() > 5 && '@' in emailText) || emailText.count() == 0
                    val focusRequester = remember { FocusRequester() }
                    val keyboardController = LocalSoftwareKeyboardController.current

                    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                        val (logo, email, password, login, register, emailError) = createRefs()

                        val image: Painter = painterResource(id = R.drawable.chill_space)
                        Image(
                            painter = image,
                            contentDescription = "Chill Space Logo",
                            modifier = Modifier
                                .constrainAs(logo) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(email.top)
                                }
                                .fillMaxWidth()

                        )

                        Button(
                            onClick = {
                                val user = FirebaseUtils.loginUser(activity, auth, emailText, passwordText)
                                if (user != null) {
                                    navController?.navigate(Screen.HomeScreen.route)
                                }
                                      },
                            modifier = Modifier
                                .constrainAs(login) {
                                    bottom.linkTo(register.top, margin = 8.dp)
                                }
                                .fillMaxWidth()
                                .padding(16.dp),
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.secondary,
                                contentColor = Color.Black
                            )
                        ) {
                            Text(text = "Login", fontSize = 24.sp)
                        }

                        val annotatedText = buildAnnotatedString {
                            //append your initial text
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Gray,
                                )
                            ) {
                                append("Don't have an account? ")

                            }

                            //Start of the pushing annotation which you want to color and make them clickable later
                            pushStringAnnotation(
                                tag = "SignUp",// provide tag which will then be provided when you click the text
                                annotation = "SignUp"
                            )
                            //add text with your different color/style
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Blue,
                                )
                            ) {
                                append("Sign Up")
                            }
                            // when pop is called it means the end of annotation with current tag
                            pop()
                        }
                        ClickableText(text = annotatedText,
                            modifier = Modifier
                                .constrainAs(register) {
                                    bottom.linkTo(parent.bottom, margin = 8.dp)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                                .padding(16.dp),
                            onClick = { })

                        OutlinedTextField(
                            value = emailText,
                            onValueChange = {
                                emailText = it
                            },
                            label = {
                                val label = if (isValid) "Email" else "Email*"
                                Text(text = label)
                            },
                            isError = !isValid,
                            modifier = Modifier
                                .constrainAs(email) {
                                    bottom.linkTo(password.top)
                                }
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp)
                                .focusRequester(focusRequester)
                                .navigationBarsWithImePadding(),
                            singleLine = true,
                            leadingIcon = { Icon(Icons.Filled.Email, "Email") },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Blue,
                                unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
                            ),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(
                                onNext = { focusRequester.requestFocus() }
                            )
                        )

                        Text(
                            textAlign = TextAlign.Center,
                            text = if (!isValid) "Requires '@' and at least 5 symbols" else "",
                            style = MaterialTheme.typography.caption.copy(color = Color.Red),
                            modifier = Modifier
                                .constrainAs(emailError) {
                                    top.linkTo(email.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                        )

                        OutlinedTextField(
                            value = passwordText,
                            onValueChange = {
                                passwordText = it
                            },
                            label = { Text("Password") },
                            modifier = Modifier
                                .constrainAs(password) {
                                    bottom.linkTo(login.top, margin = 8.dp)
                                }
                                .fillMaxWidth()
                                .padding(8.dp)
                                .focusRequester(focusRequester)
                                .navigationBarsWithImePadding(),
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            leadingIcon = { Icon(Icons.Filled.Lock, "") },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Blue,
                                unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
                            ),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(
                                onDone = { keyboardController?.hide() }
                            )
                        )
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    LoginScreen(null, null, null, null)
}
