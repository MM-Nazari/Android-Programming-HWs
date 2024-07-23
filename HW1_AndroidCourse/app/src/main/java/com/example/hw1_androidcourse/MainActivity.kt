package com.example.hw1_androidcourse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hw1_androidcourse.ui.theme.HW1_AndroidCourseTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HW1_AndroidCourseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF000000)
                ) {
                    BusinessCard()
                }
            }
        }
    }
}

@Composable
fun BusinessCard(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ){
        Spacer(
            modifier = Modifier.weight(4.5f)
        )
        Image(
            painter = painterResource(R.drawable._0976_1),
            contentDescription = "Personal Photo",
            modifier = Modifier.weight(4f).clip(RoundedCornerShape(16.dp)).fillMaxHeight()
        )
        Spacer(
            modifier = Modifier.weight(0.5f)
        )
        Text(
            text = "Mohammad Mehdi Nazari",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(2f),
            color = Color(0xFFD4AF37),
            fontSize = 21.sp
        )
        Spacer(
            modifier = Modifier.weight(3.5f)
        )
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.icons8_email_48___),
                contentDescription = "Email Icon",
                modifier = Modifier.weight(2f),
                alignment = Alignment.CenterEnd
                )
            Spacer(
                modifier = Modifier.weight(0.5f)
            )
            Text(
                text = "nazarimohammadmehdi6@gmail.com",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFD4AF37),
                modifier = Modifier.weight(5f),
                fontStyle = FontStyle.Italic
            )
        }
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.icons8_instagram_50___),
                contentDescription = "Instagram Icon",
                modifier = Modifier.weight(2f),
                alignment = Alignment.CenterEnd
                )
            Spacer(
                modifier = Modifier.weight(0.5f)
            )
            Text(
                text = "mmn_personal",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFD4AF37),
                modifier = Modifier.weight(5f),
                fontStyle = FontStyle.Italic
            )
        }
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.icons8_telegram_50___),
                contentDescription = "Telegram Icon",
                modifier = Modifier.weight(2f),
                alignment = Alignment.CenterEnd
                )
            Spacer(
                modifier = Modifier.weight(0.5f)
            )
            Text(
                text = "MMNazari",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFD4AF37),
                modifier = Modifier.weight(5f),
                fontStyle = FontStyle.Italic
            )
        }
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.icons8_phone_number_50),
                contentDescription = "Phone Number Icon",
                modifier = Modifier.weight(2f),
                alignment = Alignment.CenterEnd
                )
            Spacer(
                modifier = Modifier.weight(0.5f)
            )
            Text(
                text = "09393691800",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFD4AF37),
                modifier = Modifier.weight(5f),
                fontStyle = FontStyle.Italic
            )
        }
        Spacer(
            modifier = Modifier.weight(5f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BusinessCardPreview() {
    HW1_AndroidCourseTheme {
        BusinessCard()
    }
}