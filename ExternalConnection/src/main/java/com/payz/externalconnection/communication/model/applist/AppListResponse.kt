package com.payz.externalconnection.communication.model.applist

data class AppListResponse(
        var appList: List<AppInfo> = listOf(),
        var type: String = "AppListResponse"
)