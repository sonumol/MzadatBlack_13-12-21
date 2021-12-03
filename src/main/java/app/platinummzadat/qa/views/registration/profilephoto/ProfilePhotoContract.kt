package app.platinummzadat.qa.views.registration.profilephoto

import app.platinummzadat.qa.BasePresenter
import app.platinummzadat.qa.BaseView
import app.platinummzadat.qa.data.models.ProfileModel

interface ProfilePhotoContract {
    interface View : BaseView<Presenter> {
        fun showSuccess()
        fun showData(data: ProfileModel)
    }
    interface Presenter : BasePresenter {
        fun uploadPhoto(path:String)
        fun fetchProfile()

    }
}