package app.platinummzadat.qa.data.models

import app.platinummzadat.qa.views.root.drawer.MzNav

/**
 * Created by WOLF
 * at 14:22 on Tuesday 02 April 2019
 */
data class NavDrawerModel(
    val text: String,
    val icon: Int,
    val choice: MzNav
)