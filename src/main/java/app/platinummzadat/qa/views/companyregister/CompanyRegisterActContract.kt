package app.platinummzadat.qa.views.companyregister

import app.platinummzadat.qa.BasePresenter
import app.platinummzadat.qa.BaseView
import app.platinummzadat.qa.data.models.CheckCompanyRegisterRes
import app.platinummzadat.qa.data.models.ComputerCheckRes

interface CompanyRegisterActContract {
    interface View : BaseView<Presenter> {
        fun showData(data: CheckCompanyRegisterRes)
        fun showDataNoRegistration(data:CheckCompanyRegisterRes)
        fun showSuccess()
        fun showFailure()
        fun showSuccessComputerCard(data: ComputerCheckRes)
        fun showFailureComputerCard(data: ComputerCheckRes)
    }

    interface Presenter : BasePresenter {
        fun checkCompanyRegistartion()
        fun checkComputerCard( cr_no: String)
        fun registerCompany(

                company_name: String,
                company_sign_id: String,
                cr_no: String

        )
    }
}