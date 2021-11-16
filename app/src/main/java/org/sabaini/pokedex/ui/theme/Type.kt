package org.sabaini.pokedex.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.sabaini.pokedex.R

private val robotoFontFamily = FontFamily(
    fonts = listOf(
        Font(
            resId = R.font.roboto_black,
            weight = FontWeight.W900,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.roboto_black_italic,
            weight = FontWeight.W900,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.roboto_bold,
            weight = FontWeight.W700,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.roboto_bold_italic,
            weight = FontWeight.W700,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.roboto_light,
            weight = FontWeight.W300,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.roboto_light_italic,
            weight = FontWeight.W300,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.roboto_medium,
            weight = FontWeight.W500,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.roboto_medium_italic,
            weight = FontWeight.W500,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.roboto_regular,
            weight = FontWeight.W400,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.roboto_thin,
            weight = FontWeight.W100,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.roboto_thin_italic,
            weight = FontWeight.W100,
            style = FontStyle.Italic
        )
    )
)

private val defaultTypography = Typography()

val Typography = Typography(
    h1 = defaultTypography.h1.copy(fontFamily = robotoFontFamily),
    h2 = defaultTypography.h2.copy(fontFamily = robotoFontFamily),
    h3 = defaultTypography.h3.copy(fontFamily = robotoFontFamily),
    h4 = defaultTypography.h4.copy(fontFamily = robotoFontFamily),
    h5 = defaultTypography.h5.copy(fontFamily = robotoFontFamily),
    h6 = defaultTypography.h6.copy(fontFamily = robotoFontFamily),
    subtitle1 = defaultTypography.subtitle1.copy(fontFamily = robotoFontFamily),
    subtitle2 = defaultTypography.subtitle2.copy(fontFamily = robotoFontFamily),
    body1 = defaultTypography.body1.copy(fontFamily = robotoFontFamily),
    body2 = defaultTypography.body2.copy(fontFamily = robotoFontFamily),
    button = defaultTypography.button.copy(fontFamily = robotoFontFamily),
    caption = defaultTypography.caption.copy(fontFamily = robotoFontFamily),
    overline = defaultTypography.overline.copy(fontFamily = robotoFontFamily)
)