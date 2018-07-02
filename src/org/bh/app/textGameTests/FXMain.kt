package org.bh.app.textGameTests

import LatteFX.*

class Main : LatteFXMain(
        appInfo = TGTAppInfo,
        appConfig = TGTConfig,
        onStart = { _, primaryWindow ->
    primaryWindow?.show()
})



object TGTAppInfo : LatteAppInfo {
    override val appName: String = "Text Game Tests"
}



object TGTConfig : LatteAppConfig {
    override val automaticallySetUpSystemMenuBar = true
    override val primaryParent: PaneOrGroup<*>? = null
    override val startingWrapperGenerator = { TGTContentWrapper() }
}
