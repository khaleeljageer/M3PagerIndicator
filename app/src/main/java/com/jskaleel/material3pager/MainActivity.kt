package com.jskaleel.material3pager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.jskaleel.material3pager.ui.theme.Material3PagerTheme
import com.jskaleel.pagerindicator.AnimatedLineIndicator
import com.jskaleel.pagerindicator.PagerIndicator
import com.jskaleel.pagerindicator.LineIndicator
import kotlin.math.absoluteValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Material3PagerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreenContent(
                        pages = listOf(
                            "Card 1",
                            "Card 2",
                            "Card 3",
                            "Card 4",
                            "Card 5",
                        )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreenContent(pages: List<String>) {
    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        HorizontalPager(
            pageCount = pages.size,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 20.dp),
            pageSpacing = 8.dp,
        ) { index ->
            val item = pages[index]
            PagerItem(
                pagerState = pagerState,
                index = index,
                label = item
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        PagerIndicator(
            pagerState = pagerState,
            pageCount = pages.size,
        )

        Spacer(modifier = Modifier.height(8.dp))
        PagerIndicator(
            pagerState = pagerState,
            pageCount = pages.size,
            indicatorShape = object : Shape {
                override fun createOutline(
                    size: Size,
                    layoutDirection: LayoutDirection,
                    density: Density
                ): Outline {
                    val rectPath = Path().apply {
                        // Moves to top center position
                        moveTo(size.width / 2f, 0f)
                        // Add line to right corner above circle
                        lineTo(x = size.width, y = size.height)
                        //Add line to left corner above circle
                        lineTo(x = 0f, y = size.height)
                    }
                    return Outline.Generic(path = rectPath)
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))
        LineIndicator(
            pagerState = pagerState,
            pageCount = pages.size,
        )

        Spacer(modifier = Modifier.height(8.dp))
        AnimatedLineIndicator(
            pagerState = pagerState,
            pageCount = pages.size,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerItem(
    pagerState: PagerState,
    index: Int,
    label: String
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.778f)
            .graphicsLayer {
                val pageOffset = (
                        (pagerState.currentPage - index) +
                                pagerState.currentPageOffsetFraction).absoluteValue

                alpha = lerp(
                    start = 0.5f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Material3PagerTheme {
        MainScreenContent(
            pages = listOf(
                "Card 1",
                "Card 2",
                "Card 3",
                "Card 4",
            )
        )
    }
}