package app.platinummzadat.qa.views.root.company

import app.platinummzadat.qa.MApp.Companion.MzRepo
import app.platinummzadat.qa.R
import app.platinummzadat.qa.data.models.CheckCompanyRegisterRes
import app.platinummzadat.qa.tempUserId
import raj.nishin.wolfrequest.ERROR

/**
 * Created by WOLF
 * at 10:21 on Monday 24 June 2019
 */
class CompanyRegisterPresenter(private val view:CompanyRegisterContract.View):CompanyRegisterContract.Presenter {
    init {
        view.presenter = this
    }

    override fun checkCompanyRegistartion() {
        view.showLoading()
        MzRepo.checkCompanyRegistration() { status, data, error ->
            view.hideLoading()
            when {
                error == ERROR.NO_INTERNET -> {
                    view.showNoInternet()
                }
                null == data -> {
                    view.showApiError()
                }
                status -> {
                    view.showData(data)
                }
                !status -> {
                    view.showDataNoRegistration(data)
                }
                else -> {
                    view.showApiError()
                }
            }
        }
    }

    override fun checkComputerCard(cr_no: String) {
        MzRepo.checkComputerCard(cr_no) { status, data, error ->
            view.hideLoading()
            when {
                error == ERROR.NO_INTERNET -> {
                    view.showNoInternet()
                }
                null == data -> {
                    view.showApiError()
                }
                status -> {
                    view.showSuccessComputerCard(data)
                }
                !status -> {
                    view.showFailureComputerCard(data)
                }
                else -> {
                    view.showApiError()
                }
            }
        }
    }

    override fun registerCompany( company_name: String, company_sign_id: String,cr_no: String) {
            view.showLoading()
            MzRepo.registerCompany( company_name, company_sign_id,cr_no ) { status, data, error ->
                view.hideLoading()
                when {
                    error == ERROR.API_ERROR -> {
                        view.showApiError()
                    }
                    error == ERROR.NO_INTERNET -> {
                        view.showNoInternet()
                    }

                    status -> {
                        view.showSuccess()
                    }
                    !status -> {
                        view.showFailure()
                    }

                }
            }
    }
}