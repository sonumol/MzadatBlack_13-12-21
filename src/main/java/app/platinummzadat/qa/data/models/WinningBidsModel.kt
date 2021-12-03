package app.platinummzadat.qa.data.models

/**
 * Created by Badhusha
 * at 12:57  09 OCt 2020
 */
data class WinningBidsModel(
    val `data`: List<WinningBidsDetails>,
    val message: String,
    val status: Boolean
)

