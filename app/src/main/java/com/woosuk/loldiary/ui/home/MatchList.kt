package com.woosuk.loldiary.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.glide.GlideImage
import com.woosuk.loldiary.R
import com.woosuk.loldiary.domain.model.UserAccount
import com.woosuk.loldiary.domain.model.UserMatchInfo
import com.woosuk.loldiary.ui.theme.LoLDiaryTheme
import com.woosuk.loldiary.ui.utils.toGameDurationFormat
import java.time.LocalDateTime

@Composable
fun MatchList(
    userMatchInfoList: List<UserMatchInfo>,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        items(
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
        Modifier.fillMaxWidth()
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
                modifier = Modifier.padding(15.dp)
            ) {
                KillDeathAssist(
                    kills = userMatchInfo.kills,
                    deaths = userMatchInfo.deaths,
                    assists = userMatchInfo.assists,
                    modifier = Modifier.padding(bottom = 15.dp)
                )
                ItemList(itemImageUrls = userMatchInfo.itemImageUrls)
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
            .size(width = 40.dp, height = 90.dp),
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
                .padding(bottom = 5.dp, top = 5.dp)
                .width(20.dp),
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
                .size(90.dp)
                .clip(RoundedCornerShape(5.dp))
                .align(Alignment.CenterStart),
            imageModel = { championImageUrl },
            requestOptions = {
                RequestOptions()
                    .override(250, 250)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .sizeMultiplier(0.8f)
            }
        )
        GlideImage(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(5.dp))
                .align(Alignment.TopEnd),
            imageModel = { spell1ImageUrl },
            requestOptions = {
                RequestOptions()
                    .override(100, 100)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .sizeMultiplier(0.5f)
            }
        )

        GlideImage(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(5.dp))
                .align(Alignment.BottomEnd),
            imageModel = { spell2ImageUrl },
            requestOptions = {
                RequestOptions()
                    .override(100, 100)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .sizeMultiplier(0.5f)
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
fun ItemList(
    itemImageUrls: List<String>,
) {
    Row {
        itemImageUrls.forEach {
            GlideImage(
                modifier = Modifier
                    .size(25.dp)
                    .clip(RoundedCornerShape(5.dp)),
                imageModel = { it },
                requestOptions = {
                    RequestOptions()
                        .override(100, 100)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .sizeMultiplier(0.3f)
                        .placeholder(R.drawable.app_logo)
                        .error(R.drawable.app_logo)
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
