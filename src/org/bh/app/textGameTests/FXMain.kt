package org.bh.app.textGameTests

import LatteFX.LatteFXMain
import LatteFX.contentWrapper

class Main : LatteFXMain({ commandLineArguments, primaryWindow ->
      primaryWindow!!.contentWrapper = TGTContentWrapper()
      primaryWindow.show()
})
