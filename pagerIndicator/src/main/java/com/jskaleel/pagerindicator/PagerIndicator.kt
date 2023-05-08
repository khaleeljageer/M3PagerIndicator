package com.jskaleel.pagerindicator

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    pageCount: Int,
    indicatorSpace: Dp = 4.dp,
    indicatorSize: Dp = 8.dp,
    indicatorShape: Shape = CircleShape,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.primary.copy(
        alpha = 0.5f
    ),
    selectedIndicatorSize: Float = 1.3f,
) {

    Row(
        modifier = modifier.then(
            Modifier.fillMaxWidth()
        ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        repeat(pageCount) { index ->
            val selected = pagerState.currentPage == index
            val scale by animateFloatAsState(if (selected) selectedIndicatorSize else 1f)
            val color = if (selected) {
                selectedColor
            } else {
                unselectedColor
            }

            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
                    .padding(all = indicatorSpace)
                    .clip(shape = indicatorShape)
                    .background(color = color)
                    .size(size = indicatorSize)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LineIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    pageCount: Int,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.primary.copy(
        alpha = 0.5f
    ),
    indicatorSpace: Dp = 4.dp,
    indicatorHeight: Dp = 4.dp,
    indicatorCornerShape: Shape = RoundedCornerShape(0.dp)
) {
    Row(
        modifier = modifier.then(
            Modifier
                .padding(start = 4.dp)
                .fillMaxWidth()
        ),
        horizontalArrangement = Arrangement.Start
    ) {
        repeat(pageCount) { index ->
            val selected = pagerState.currentPage == index
            val color = if (selected) {
                selectedColor
            } else {
                unselectedColor
            }

            Box(
                modifier = Modifier
                    .padding(all = indicatorSpace)
                    .clip(shape = indicatorCornerShape)
                    .background(color = color)
                    .weight(1f)
                    .height(height = indicatorHeight),
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimatedLineIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    pageCount: Int,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.primary.copy(
        alpha = 0.5f
    ),
    indicatorSpace: Dp = 4.dp,
    indicatorHeight: Dp = 4.dp,
    indicatorCornerShape: Shape = RoundedCornerShape(0.dp),
    initialIndicatorWeight: Float = 1f,
    selectedIndicatorWeight: Float = 1.5f,
    unSelectedIndicatorWeight: Float = 0.5f,

    ) {
    Row(
        modifier = modifier.then(
            Modifier
                .padding(start = 4.dp)
                .fillMaxWidth()
        ),
        horizontalArrangement = Arrangement.Start
    ) {
        repeat(pageCount) { iteration ->
            val selected = pagerState.currentPage == iteration

            val indicatorWeight by animateFloatAsState(
                targetValue = if (selected) {
                    selectedIndicatorWeight
                } else {
                    if (iteration < pagerState.currentPage) {
                        unSelectedIndicatorWeight
                    } else {
                        initialIndicatorWeight
                    }
                }, label = "indicatorWeight", animationSpec = tween(300, easing = EaseInOut)
            )

            val color = if (selected) {
                selectedColor
            } else {
                unselectedColor
            }

            Box(
                modifier = Modifier
                    .padding(all = indicatorSpace)
                    .clip(shape = indicatorCornerShape)
                    .background(color = color)
                    .weight(indicatorWeight)
                    .height(height = indicatorHeight),
            )
        }
    }
}