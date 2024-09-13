package com.daftech.eplclubs.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.daftech.eplclubs.model.Club
import com.daftech.eplclubs.model.ClubData
import com.daftech.eplclubs.ui.theme.EPLClubsTheme

@Composable
fun ClubItem(
    club: Club,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.width(135.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(club.color.toColorInt())
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.height(100.dp),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = club.logo),
                contentDescription = null
            )
            Text(
                text = club.name.uppercase(),
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Color.White,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClubItemPreview() {
    EPLClubsTheme {
        ClubItem(
            club = ClubData.clubs[0],
            modifier = Modifier.padding(2.dp)
        )
    }
}
