package com.example.newsapp

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.news_detail.NewsDetailScreen
import com.example.news_list.NewsListScreen

@Composable
fun NewsAppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "news_list"
    ) {
        composable("news_list") {
            NewsListScreen(
                onNewsClick = { article ->
                    navController.navigate("news_detail/${Uri.encode(article.url)}")
                }
            )
        }
        composable(
            route = "news_detail/{url}",
            arguments = listOf(
                navArgument("url") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val encodedUrl = backStackEntry.arguments?.getString("url") ?: ""
            val url = Uri.decode(encodedUrl)
            NewsDetailScreen(url = url)
        }
    }
}