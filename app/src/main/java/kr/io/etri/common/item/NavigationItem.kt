package kr.io.etri.common.item

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * etri
 * Created by Naedong
 * Date: 2023-10-19(019)
 * Time: 오전 10:48
 */
sealed class NavigationItem(var route: String, var icon: ImageVector, var title: String ) {
    object home : NavigationItem("home",  Icons.Default.Home,"홈")
}