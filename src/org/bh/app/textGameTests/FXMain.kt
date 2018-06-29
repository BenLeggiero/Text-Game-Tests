package org.bh.app.textGameTests

import LatteFX.*

class Main : LatteFXMain(onStart = { _, primaryWindow ->
    primaryWindow!!.contentWrapper = TGTContentWrapper()
    primaryWindow.show()
})
