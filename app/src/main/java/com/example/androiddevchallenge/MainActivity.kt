/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.data.FakeDatabase
import com.example.androiddevchallenge.data.model.PetModel
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme(false) {
                Scaffold {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    Column(modifier = Modifier.padding(vertical = 15.dp)) {
        Text(
            text = "Adopt My Paw",
            modifier = Modifier.padding(20.dp),
            style = MaterialTheme.typography.h3.copy(MaterialTheme.colors.primary)
        )
        StraggeredGrid(
            children = {
                FakeDatabase.data.map { PetCard(it) }
            }
        )
    }
}

@Composable
fun StraggeredGrid(
    children: @Composable () -> Unit,
    rows: Int = 2
) {
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState())
    ) {
        Layout(content = children) { measurables, constraints ->
            val rowWidths = IntArray(rows) { 0 }
            val rowMaxHeights = IntArray(rows) { 0 }

            val placeables = measurables.mapIndexed { index, measurable ->

                val placeable = measurable.measure(constraints)

                val row = index % rows

                rowWidths[row] = rowWidths[row] + placeable.width
                rowMaxHeights[row] = kotlin.math.max(rowMaxHeights[row], placeable.height)

                placeable
            }

            val width = rowWidths.maxOrNull()
                ?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth))
                ?: constraints.minWidth

            val height = rowMaxHeights.sumBy { it }
                .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

            val rowY = IntArray(rows) { 0 }
            for (i in 1 until rows) {
                rowY[i] = rowY[i - 1] + rowMaxHeights[i - 1]
            }

            layout(width, height) {
                val rowX = IntArray(rows) { 0 }

                placeables.forEachIndexed { index, placeable ->
                    val row = index % rows
                    placeable.placeRelative(
                        x = rowX[row],
                        y = rowY[row]
                    )
                    rowX[row] += placeable.width
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PetCard(pet: PetModel) {
    var showOverlay by remember { mutableStateOf(false) }
    val alpha: Float by animateFloatAsState(if (showOverlay) 0.5f else 1f)
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(15.dp),
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier
            .padding(15.dp)
            .clickable { showOverlay = !showOverlay },
    ) {
        Box {
            Image(
                painter = painterResource(id = pet.image),
                contentDescription = pet.name,
                alpha = alpha,
                modifier = Modifier.height(250.dp),
                contentScale = ContentScale.FillHeight
            )
            Box(
                Modifier
                    .align(Alignment.BottomStart)
                    .padding(10.dp)
            ) {
                PetDetails(pet)
            }
            AnimatedVisibility(
                visible = showOverlay,
                enter = slideInVertically(
                    initialOffsetY = { -40 }
                ) + expandVertically(
                    expandFrom = Alignment.CenterVertically
                ) + fadeIn(initialAlpha = 0.3f),
                exit = slideOutVertically() + shrinkVertically() + fadeOut(),
                modifier = Modifier.align(Alignment.Center)
            ) {
                Button(
                    onClick = {
                        Toast.makeText(context, "Thanks for adopting me!!!", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Text(text = "Adopt ME")
                }
            }
        }
    }
}

@Composable
fun PetDetails(pet: PetModel) {
    val overlayTxtStyle = TextStyle(
        color = Color.White,
        fontWeight = FontWeight.Bold,
        shadow = Shadow(Color.Black, offset = Offset(2f, 2f))
    )
    Column {
        Text(
            text = pet.name,
            style = overlayTxtStyle.copy(fontSize = 22.sp)
        )
        Text(
            text = pet.breed,
            style = overlayTxtStyle.copy(fontSize = 18.sp)
        )
    }
}
