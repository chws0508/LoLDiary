package com.woosuk.designsystem.theme

import androidx.compose.ui.graphics.Color

val Primary100: Color = Color(0xFF2752E7)
val Primary80: Color = Color(0xFF5275EC)
val Primary60: Color = Color(0xFF476AE5)
val Primary40: Color = Color(0xFFD0DBFF)
val Primary20: Color = Color(0xFFF5F8FE)

val Secondary100: Color = Color(0xFFFFBE55)
val Secondary80: Color = Color(0xFFFFD899)
val Secondary60: Color = Color(0xFFFFE5BB)
val Secondary40: Color = Color(0xFFFFF2DD)
val Secondary20: Color = Color(0xFFFFF9EF)

val Black100: Color = Color(0xFF111111)
val Black80: Color = Color(0xFF707070)
val Black60: Color = Color(0xFFA0A0A0)
val Black40: Color = Color(0xFFCFCFCF)
val Black20: Color = Color(0xFFF3F3F3)
val Black0: Color = Color(0xFFFFFFFF)

val Success: Color = Color(0xFF3F845F)
val Error: Color = Color(0xFFE25C5C)
val Warning: Color = Color(0xFFE4C65B)
val Info: Color = Color(0xFF2685CA)

data class WoosukColor(
    val Primary100: Color = Color(0xFF2752E7),
    val Primary80: Color = Color(0xFF5275EC),
    val Primary60: Color = Color(0xFF476AE5),
    val Primary40: Color = Color(0xFFD0DBFF),
    val Primary20: Color = Color(0xFFF5F8FE),

    val Secondary100: Color = Color(0xFFFFBE55),
    val Secondary80: Color = Color(0xFFFFD899),
    val Secondary60: Color = Color(0xFFFFE5BB),
    val Secondary40: Color = Color(0xFFFFF2DD),
    val Secondary20: Color = Color(0xFFFFF9EF),
    val Black100: Color = Color(0xFF111111),
    val Black80: Color = Color(0xFF707070),
    val Black60: Color = Color(0xFFA0A0A0),
    val Black40: Color = Color(0xFFCFCFCF),
    val Black20: Color = Color(0xFFF3F3F3),
    val Black0: Color = Color(0xFFFFFFFF),

    val Success: Color = Color(0xFF3F845F),
    val Error: Color = Color(0xFFE25C5C),
    val Warning: Color = Color(0xFFE4C65B),
    val Info: Color = Color(0xFF2685CA),
)

val lightColor = WoosukColor()
val darkColor = WoosukColor()
