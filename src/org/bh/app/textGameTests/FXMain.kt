package org.bh.app.textGameTests

import LatteFX.*

class Main : LatteFXMain({ commandLineArguments, primaryWindow ->
    primaryWindow!!.contentWrapper = TGTContentWrapper()
    primaryWindow.show()
})
