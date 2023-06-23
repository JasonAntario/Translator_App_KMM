package com.dsankovsky.translatorappkmm.android

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsankovsky.translatorappkmm.android.core.theme.darkColors
import com.dsankovsky.translatorappkmm.android.core.theme.lightColor

@Composable
fun TranslatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors
    } else {
        lightColor
    }

    val SfProText = FontFamily(
        Font(
            resId = R.font.sf_pro_text_regular,
            weight = FontWeight.Normal
        ),
        Font(
            resId = R.font.sf_pro_text_bold,
            weight = FontWeight.Bold
        ),
        Font(
            resId = R.font.sf_pro_text_medium,
            weight = FontWeight.Medium
        )
    )
    val typography = Typography(
        h1 = TextStyle(
            fontFamily = SfProText,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        ),
        h2 = TextStyle(
            fontFamily = SfProText,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        ),
        h3 = TextStyle(
            fontFamily = SfProText,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp
        ),
        body1 = TextStyle(
            fontFamily = SfProText,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        body2 = TextStyle(
            fontFamily = SfProText,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
