package app.platinummzadat.qa.views.root.profile

import app.platinummzadat.qa.BasePresenter
import app.platinummzadat.qa.BaseView
import app.platinummzadat.qa.data.models.AmountData
import app.platinummzadat.qa.data.models.ProfileModel

interface ProfileContract {
    interface View : BaseView<Presenter> {
        fun showData(data: ProfileModel)
        fun showDataAmount(data: AmountData)
        fun showUploadingQid()
        fun hideUploadingQid()
        fun showUploadedQid(side:String)
        fun showUploadQidFailed(side:String)
        fun showUploadedProfilePhoto()
        fun showUploadProfilePhotoFailed()
    }

    interface Presenter : BasePresenter {
        fun fetchProfile()
        fun getAmount()
//        fun uploadQid(path:String)
    }
}