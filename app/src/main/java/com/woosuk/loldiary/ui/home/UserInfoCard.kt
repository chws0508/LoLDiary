package com.woosuk.loldiary.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.memory.MemoryCache
import coil.request.ImageRequest
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.glide.GlideImage
import com.woosuk.loldiary.domain.model.User
import com.woosuk.loldiary.domain.model.UserRankInfo
import com.woosuk.loldiary.ui.theme.LoLDiaryTheme

@Composable
fun UserInfoCard(
    user: User,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 17.dp, vertical = 10.dp),
        color = Color.White,
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(8.dp),
    ) {
        Row {
            UserProfileImage(
                imageUrl = user.userProfile.profileImageUrl,
                userLevel = user.userProfile.level,
                modifier = Modifier.padding(
                    start = 17.dp,
                    top = 10.dp,
                    bottom = 10.dp,
                    end = 10.dp,
                ),
            )

            UserAccountInfoView(
                gameName = user.userAccount.gameName,
                tagLine = user.userAccount.tageLine,
                modifier = Modifier.padding(top = 20.dp, bottom = 10.dp, end = 20.dp),
            )

            UserRankInfoView(
                userRankInfo = user.userRankInfo,
                modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
            )
        }
    }
}

@Composable
fun UserProfileImage(
    imageUrl: String,
    userLevel: Int,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Box(modifier.wrapContentSize()) {
        CoilImage(
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(15.dp)),
            imageRequest = {
                ImageRequest.Builder(context)
                    .data(imageUrl)
                    .crossfade(true)
                    .build()
            },
            imageLoader = {
                ImageLoader.Builder(LocalContext.current)
                    .memoryCache { MemoryCache.Builder(context).maxSizePercent(0.25).build() }
                    .crossfade(true)
                    .build()
            },
        )
        GlideImage(
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(15.dp)),
            imageModel = { imageUrl },
            imageOptions = ImageOptions(
                contentDescription = null,
                contentScale = ContentScale.Crop,
            ),
        )
        Text(
            text = userLevel.toString(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Color.DarkGray),
            color = Color.White,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Composable
fun UserAccountInfoView(
    gameName: String,
    tagLine: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier.wrapContentSize()) {
        Text(
            text = gameName,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "#$tagLine",
            style = MaterialTheme.typography.bodySmall,
            color = Color.LightGray,
        )
    }
}

@Composable
fun UserRankInfoView(
    userRankInfo: UserRankInfo,
    modifier: Modifier = Modifier,
) {
    Column(modifier.wrapContentSize()) {
        Row {
            Text(
                text = userRankInfo.tier.name + userRankInfo.rank,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
        }
        Row {
            Text(
                text = userRankInfo.points.toString() + " LP  ",
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray,
            )
            Text(
                text = "${userRankInfo.wins}승 ${userRankInfo.losses}패 (${userRankInfo.wins * 100 / (userRankInfo.losses + userRankInfo.wins)}%)",
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray,
            )
        }
    }
}

@Composable
@Preview
fun UserInfoCardPreView() {
    LoLDiaryTheme {
        Column(Modifier.background(Color.White)) {
            UserInfoCard(user = User.mock())
        }
    }
}
