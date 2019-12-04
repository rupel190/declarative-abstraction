package at.technikum.wien.mse.swe.dlsspecific

import at.technikum.wien.mse.swe.model.Amount
import at.technikum.wien.mse.swe.model.DepotOwner
import at.technikum.wien.mse.swe.model.RiskCategory

class SecurityAccountOverview {
    var accountNumberConfig: String? = null
    var riskCategoryConfig: RiskCategory? = null
    var depotOwnerConfig: DepotOwner? = null
    var amountConfig: Amount? = null

    //fun open?

    fun accountNumberManager(accountNumber: () -> String?) {
        accountNumberConfig = accountNumber()
    }

    fun riskCategoryManager(riskCategory: () -> RiskCategory) {
        riskCategoryConfig = riskCategory()
    }

    fun depotOwnerManager(depotOwner: () -> DepotOwner) {
        depotOwnerConfig = depotOwner()
    }

    fun amountManager(amount: () -> Amount) {
        amountConfig = amount()
    }

}