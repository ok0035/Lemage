package com.zerosword.domain.navigation

enum class Routes(val route: String) {

    Search("search"),
    Bookmark("bookmark"),
    ImageViewer("image");

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}