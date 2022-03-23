package com.z3r0_8ug.ui_common.framework.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import kotlin.math.roundToInt

@OptIn(ExperimentalAnimationApi::class)
object XAxisTransition {
  private const val ANIMATION_DURATION = 300

  /**
   * - Slide in from the right with a 30.dp offset, full duration
   * - Fade in, starting at 30% duration
   */
  val enter = slideInHorizontally(
    initialOffsetX = {
      // NOTE: this should be 30.dp
      it / 5
    },
    animationSpec = tween(
      durationMillis = ANIMATION_DURATION,
      easing = FastOutSlowInEasing
    )
  ).plus(
    fadeIn(
      initialAlpha = 0f,
      animationSpec = tween(
        durationMillis = (ANIMATION_DURATION * 0.7).roundToInt(), // 210 on the standard 300 ms
        delayMillis = (ANIMATION_DURATION * 0.3).roundToInt(), // 90 on the standard 300 ms
        easing = LinearOutSlowInEasing
      )
    )
  )

  /**
   * - Slide out to the left 30.dp, full duration
   * - Fade out, 30% duration
   */
  val exit = slideOutHorizontally(
    targetOffsetX = {
      // NOTE: this should be 30.dp
      -it / 5
    },
    animationSpec = tween(
      durationMillis = ANIMATION_DURATION,
      easing = FastOutSlowInEasing
    )
  ).plus(
    fadeOut(
      targetAlpha = 0f,
      animationSpec = tween(
        durationMillis = (ANIMATION_DURATION * 0.3).roundToInt(), // 90 on the standard 300 ms
        easing = FastOutLinearInEasing
      )
    )
  )

  val popEnter = slideInHorizontally(
    initialOffsetX = {
      // NOTE: this should be 30.dp
      -it / 5
    },
    animationSpec = tween(
      durationMillis = ANIMATION_DURATION,
      easing = FastOutSlowInEasing
    )
  ).plus(
    fadeIn(
      initialAlpha = 0f,
      animationSpec = tween(
        durationMillis = (ANIMATION_DURATION * 0.5).roundToInt(), // 150 on the standard 300 ms
        delayMillis = (ANIMATION_DURATION * 0.5).roundToInt(), // 150 on the standard 300 ms
        easing = FastOutLinearInEasing
      )
    )
  )

  val popExit = slideOutHorizontally(
    targetOffsetX = {
      // NOTE: this should be 30.dp
      it / 5
    },
    animationSpec = tween(
      durationMillis = ANIMATION_DURATION,
      easing = FastOutSlowInEasing
    )
  ).plus(
    fadeOut(
      targetAlpha = 0f,
      animationSpec = tween(
        durationMillis = (ANIMATION_DURATION * 0.7).roundToInt(), // 210 on the standard 300 ms
        easing = LinearOutSlowInEasing
      )
    )
  )
}

@OptIn(ExperimentalAnimationApi::class)
object ZAxisTransition {
  private const val ANIMATION_DURATION = 300

  /**
   * - Scale from 80% to 100%, full duration
   * - Fade in, starting at 30% duration
   */
  val enter = scaleIn(
    initialScale = 0.8f,
    animationSpec = tween(
      durationMillis = ANIMATION_DURATION,
      easing = FastOutSlowInEasing
    )
  ).plus(
    fadeIn(
      initialAlpha = 0f,
      animationSpec = tween(
        durationMillis = (ANIMATION_DURATION * 0.7).roundToInt(), // 210 on the standard 300 ms
        delayMillis = (ANIMATION_DURATION * 0.3).roundToInt(), // 90 on the standard 300 ms
        easing = LinearOutSlowInEasing
      )
    )
  )

  /**
   * - Scale from 100% to 110%, full duration
   * - Fade out, 30% duration
   */
  val exit = scaleOut(
    targetScale = 1.1f,
    animationSpec = tween(
      durationMillis = ANIMATION_DURATION,
      easing = FastOutSlowInEasing
    )
  ).plus(
    fadeOut(
      targetAlpha = 0f,
      animationSpec = tween(
        durationMillis = (ANIMATION_DURATION * 0.3).roundToInt(), // 90 on the standard 300 ms
        easing = FastOutLinearInEasing
      )
    )
  )

  val popEnter = scaleIn(
    initialScale = 1.1f,
    animationSpec = tween(
      durationMillis = ANIMATION_DURATION,
      easing = FastOutSlowInEasing
    )
  ).plus(
    fadeIn(
      initialAlpha = 0f,
      animationSpec = tween(
        durationMillis = (ANIMATION_DURATION * 0.5).roundToInt(), // 150 on the standard 300 ms
        delayMillis = (ANIMATION_DURATION * 0.5).roundToInt(), // 150 on the standard 300 ms
        easing = FastOutLinearInEasing
      )
    )
  )

  val popExit = scaleOut(
    targetScale = 0.8f,
    animationSpec = tween(
      durationMillis = ANIMATION_DURATION,
      easing = FastOutSlowInEasing
    )
  ).plus(
    fadeOut(
      targetAlpha = 0f,
      animationSpec = tween(
        durationMillis = (ANIMATION_DURATION * 0.7).roundToInt(), // 210 on the standard 300 ms
        easing = LinearOutSlowInEasing
      )
    )
  )
}

