package com.hfad.animationp11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.animationp11.ui.theme.AnimationP11Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CrossfadeDemo2()
        }
    }
}

@Composable
fun VisibilityDemo(){
    var visible by remember{
        mutableStateOf(true)
    }
    Column {
        Button(onClick = { visible = !visible }) {
            Text(text = if (visible) "HIDE" else "SHOW")
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (visible) Box(modifier = Modifier
            .size(128.dp)
            .padding(10.dp)
            .background(Color.Blue))
    }
}

//Без анимации
@Composable
fun StateDemo(){
    val Blue = Color(0xFF40C4D1)
    val Orange = Color(0xFFFF5722)
    var blue by remember{
        mutableStateOf(true)
    }

    val color = if (blue) Blue else Orange

    Column {
        Button(onClick = { blue = !blue },
            modifier = Modifier.padding(10.dp))
        {
        Text(text = "Change color")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(128.dp)
                .padding(10.dp)
                .background(color)
        )
    }
}
//С анимацией
@Composable
fun AnimateAsStateDemo(){
    var blue by remember {
        mutableStateOf(true)
    }
    val color by animateColorAsState(
        if(blue) Color.Blue else Color(0xFFFF5722),
        animationSpec = tween(
            durationMillis = 2000,
            delayMillis = 1500,

        ),
        label ="5"
    )
    Column {
        Button(onClick = { blue = !blue },
            modifier = Modifier.padding(10.dp))
        {
            Text(text = "Change color")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(128.dp)
                .padding(10.dp)
                .background(color)
        )
    }
}

//Пример 2. Анимация для нескольких параметров – цвет, размер.

//Без анимации
@Composable
fun TwoParametersUpdateDemo(){
    var small by remember{
        mutableStateOf(true)
    }
    val color = when(small){
        false -> Color(0xFFFF5722)
        true -> Color.Blue
    }

    val size = when(small){
        true -> 64.dp
        false -> 128.dp
    }
    Column {
        Button(onClick = { small = !small },
            modifier = Modifier.padding(10.dp))
        {
            Text(text = "Change color")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(128.dp)
                .padding(10.dp)
                .background(color)
        )
    }
}
//С анимацией
@Composable
fun AnimatedTwoParametersUpdateDemo() {
    val coroutineScope = rememberCoroutineScope()
    var small by remember { mutableStateOf(true) }
    val transition = updateTransition(targetState = small)
    val color by transition.animateColor(transitionSpec = { tween(2000) }) { state ->
        when (state) {
            false -> Color(0xFFFF5722)
            true -> Color.Blue
        }
    }

    val size by transition.animateDp(transitionSpec = { tween(2000) }) { state ->
        when (state) {
            true -> 64.dp
            false -> 128.dp
        }
    }

    Column {
        Button(
            onClick = {
                coroutineScope.launch {
                    small = !small
                }
            },
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = "Change color")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(size)
                .padding(10.dp)
                .background(color)
        )
    }
}
//Пример 3. Анимация для режима видимый/невидимый. Специальные отдельные эффекты при
//появлении и исчезновении – enter ... & exit

@Composable
fun AnimatedVisibilityDemo(){
    var visible by remember{
        mutableStateOf(true)
    }
    Column {
        Button(
            onClick = {
                visible = !visible
            },
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = if(visible)"HIDE" else "SHOW")
        }

        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(visible, enter = fadeIn(tween(2000)),
            exit = fadeOut(tween(5000))
        ) {
            Box(
                modifier = Modifier
                    .size(128.dp)
                    .padding(10.dp)
                    .background(Color.Blue)
            )
        }
    }
}
//Пример 4. Анимация для смены контента.

//Basic
@Composable
fun ContentSizeDemo(){
    var expanded by remember {
        mutableStateOf(true)
    }
    Column {
        Button(onClick = {expanded = !expanded}) {
            Text(text = if(expanded)"SHRINK" else "EXPANDED")
            
        }
        Spacer(modifier = Modifier
            .height(16.dp)
            .animateContentSize(animationSpec = tween(2000)))
    }
    Box(modifier = Modifier.background(Color.Gray))
    {
        Text(text = stringResource(id = R.string.abc),
            fontSize = 16.sp,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(10.dp),
            maxLines = if(expanded) Int.MAX_VALUE else 2)
    }
}
//5 Smena icononc
//Basic
private enum class DemoScene{
    Text,
    Icon
}
@Composable
fun NoCrossfadeDemo(){
    var scene by remember{
        mutableStateOf(DemoScene.Text)
    }
    Column {
        Button(onClick = {
            scene = when(scene){
                DemoScene.Icon -> DemoScene.Text
                DemoScene.Text -> DemoScene.Icon
            }
        }) {
            Text(text = "TOGGLE")
        }
        Spacer(modifier = Modifier.padding(16.dp))
        when(scene){
            DemoScene.Text -> Text(text = "Phone", fontSize = 32.sp)
            DemoScene.Icon -> Icon(imageVector = Icons.Default.Phone, contentDescription = "Phone",
                modifier = Modifier.size(48.dp))
        }
    }
}
//ANimation
@Composable
fun CrossfadeDemo() {
    var scene by remember {
        mutableStateOf(DemoScene.Text)
    }
    Column {
        Button(onClick = {
            scene = when (scene) {
                DemoScene.Icon -> DemoScene.Text
                DemoScene.Text -> DemoScene.Icon
            }
        }) {
            Text(text = "TOGGLE")
        }
        Spacer(modifier = Modifier.padding(16.dp))
        Crossfade(targetState = scene) { it ->
            when (it) {
                DemoScene.Text -> Text(text = "Phone", fontSize = 32.sp)
                DemoScene.Icon -> Icon(
                    imageVector = Icons.Default.Phone, contentDescription = "Phone",
                    modifier = Modifier.size(48.dp)
                )
            }
        }

    }
}
enum class DemoScene2 {
    TEXT, ICON
}

@Composable
fun CrossfadeDemo2() {
    var scene by remember { mutableStateOf(DemoScene2.TEXT) }
    var list1 by remember {
        mutableStateOf(listOf("Phone", "Email", "Message"))
    }
    var list2 by remember {
        mutableStateOf(listOf("Apple", "Banana", "Orange"))
    }

    Column {
        Button(onClick = {
            scene = when (scene) {
                DemoScene2.TEXT -> DemoScene2.ICON
                DemoScene2.ICON -> DemoScene2.TEXT
            }
        }) {
            Text(text = "TOGGLE")
        }
        Spacer(modifier = Modifier.padding(16.dp))
        Crossfade(targetState = scene) { target ->
            when (target) {
                DemoScene2.TEXT -> LazyColumn {
                    items(list1) { item ->
                        Text(text = item, fontSize = 32.sp)
                    }
                }
                DemoScene2.ICON -> LazyColumn {
                    items(list2) { item ->
                        Text(text = item, fontSize = 32.sp)
                    }
                }
            }
        }
    }
}