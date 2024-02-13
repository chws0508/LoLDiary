package com.woosuk.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin
import com.woosuk.designsystem.theme.backgroundDark
import com.woosuk.designsystem.theme.shimmerHighLight
import com.woosuk.domain.model.UserAccount
import com.woosuk.domain.model.UserMatchInfo
import com.woosuk.loldiary.ui.theme.LoLDiaryTheme
import java.time.Duration
import java.time.LocalDateTime

@Composable
fun MatchList(
    userMatchInfoList: List<UserMatchInfo>,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        items(
            key = { it.gameId },
            items = userMatchInfoList,
        ) { item ->
            MatchInfoItem(userMatchInfo = item)
        }
    }
}

@Composable
fun MatchInfoItem(
    userMatchInfo: UserMatchInfo,
) {
    Surface(
        Modifier
            .fillMaxWidth()
            .height(110.dp)
            .padding(8.dp),
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(8.dp),
        color = Color.White,
    ) {
        Row {
            MatchResultPart(
                isWIn = userMatchInfo.isWin,
                gameDuration = userMatchInfo.gameDuration,
            )
            ChampionAndRune(
                championImageUrl = userMatchInfo.championUrl,
                spell1ImageUrl = userMatchInfo.spell1Url,
                spell2ImageUrl = userMatchInfo.spell2Url
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(15.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    KillDeathAssist(
                        kills = userMatchInfo.kills,
                        deaths = userMatchInfo.deaths,
                        assists = userMatchInfo.assists,
                    )
                    GameDuration(userMatchInfo.gameEndTime, modifier = Modifier.weight(1f))
                }
                ItemList(
                    itemImageUrls = userMatchInfo.itemImageUrls,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(top = 20.dp),
                )
            }
        }
    }
}

@Composable
fun MatchResultPart(
    isWIn: Boolean,
    gameDuration: Long,
) {
    val backgroundColor: Color = if (isWIn) Color.Blue else Color.Red
    val winResultText = if (isWIn) "승" else "패"

    Column(
        Modifier
            .background(color = backgroundColor)
            .fillMaxHeight()
            .aspectRatio(0.5f, true),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = winResultText,
            textAlign = TextAlign.Center,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium,
        )

        Divider(
            thickness = 1.dp,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp, top = 5.dp, start = 5.dp, end = 5.dp)
        )

        Text(
            text = gameDuration.toGameDurationFormat(),
            textAlign = TextAlign.Center,
            color = Color.White,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Composable
fun ChampionAndRune(
    championImageUrl: String,
    spell1ImageUrl: String,
    spell2ImageUrl: String,
) {

    Box(
        modifier = Modifier.width(140.dp)
    ) {
        GlideImage(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(5.dp))
                .align(Alignment.CenterStart),
            imageModel = { championImageUrl },
            component = rememberImageComponent {
                +ShimmerPlugin(
                    baseColor = backgroundDark,
                    highlightColor = shimmerHighLight
                )
            },
            failure = {
                Box(modifier = Modifier.background(color = backgroundDark)) {}
            }
        )
        GlideImage(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(5.dp))
                .align(Alignment.TopEnd)
                .padding(start = 3.dp),
            imageModel = { spell1ImageUrl },
            component = rememberImageComponent {
                +ShimmerPlugin(
                    baseColor = backgroundDark,
                    highlightColor = shimmerHighLight
                )
            },
            failure = {
                Box(modifier = Modifier.background(color = backgroundDark)) {}
            }
        )

        GlideImage(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(5.dp))
                .align(Alignment.BottomEnd)
                .padding(start = 3.dp),
            imageModel = { spell2ImageUrl },
            component = rememberImageComponent {
                +ShimmerPlugin(
                    baseColor = backgroundDark,
                    highlightColor = shimmerHighLight
                )
            },
            failure = {
                Box(modifier = Modifier.background(color = backgroundDark)) {}
            }
        )
    }
}

@Composable
fun KillDeathAssist(
    kills: Int,
    deaths: Int,
    assists: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Text(text = "$kills / ", color = Color.Black)
        Text(text = "$deaths", color = Color.Red)
        Text(text = " / $assists", color = Color.Black)
    }
}

@Composable
fun GameDuration(
    gameEndTime: LocalDateTime,
    modifier: Modifier = Modifier,
) {
    val duration = Duration.between(gameEndTime, LocalDateTime.now())
    var gameDurationText by remember { mutableStateOf("") }
    gameDurationText = when (duration.toMinutes()) {
        in 0 until 60 -> {
            "${duration.toMinutes()}분전"
        }

        in 60 until 1440 -> {
            "${duration.toHours()}시간전"
        }

        else -> {
            "${duration.toDays()}일전"
        }
    }
    Text(
        modifier = modifier,
        text = gameDurationText,
        style = MaterialTheme.typography.bodySmall,
        textAlign = TextAlign.End
    )
}

@Composable
fun ItemList(
    itemImageUrls: List<String>,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    Row(modifier = modifier) {
        repeat(7) {
            GlideImage(
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                ),
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f,true)
                    .clip(RoundedCornerShape(5.dp)),
                imageModel = { itemImageUrls[it] },
                component = rememberImageComponent {
                    +ShimmerPlugin(
                        baseColor = Color.LightGray,
                        highlightColor = shimmerHighLight
                    )
                },
                failure = {
                    Box(
                        modifier = Modifier
                            .background(color = Color.LightGray)
                            .size(30.dp)
                    )
                }
            )
        }
    }
}

@Composable
@Preview
fun MatchInfoItemPreview() {
    LoLDiaryTheme {
        Surface {
            MatchInfoItem(
                userMatchInfo = UserMatchInfo(
                    userAccount = UserAccount(
                        uid = "ligula",
                        summonerId = "volumus",
                        gameName = "Emanuel Bruce",
                        tageLine = "adipisci",
                        isCurrentUser = false,
                    ),
                    gameDuration = 1930,
                    gameEndTime = LocalDateTime.now(),
                    spell1Id = 2378,
                    spell2Id = 3230,
                    isWin = false,
                    championId = 2197,
                    matchType = "iusto",
                    itemList = listOf(1, 2, 3, 4, 5, 6, 7),
                    gameId = 1,
                    championName = "르블랑", kills = 7, deaths = 6, assists = 11,
                ),
            )
        }
    }
}
