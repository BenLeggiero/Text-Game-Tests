package org.bh.app.textGameTests

import java.net.URL


/**
 * @author Ben Leggiero
 * @since 2018-01-26
 */
object TGTStyle {

    val cssFileName = "/primary.css"

    val cssFileUrl: URL by lazy { TGTStyle.javaClass.getResource(cssFileName) }
    val cssFileUrlString: String by lazy { cssFileUrl.toExternalForm() }
    val cssString: String by lazy { cssFileUrl.readText() }
}
