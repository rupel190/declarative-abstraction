package at.technikum.wien.mse.swe.dlsspecific

import at.technikum.wien.mse.swe.model.Amount
import at.technikum.wien.mse.swe.model.DepotOwner
import at.technikum.wien.mse.swe.model.RiskCategory
import sun.security.krb5.Config
import java.math.BigDecimal
import java.util.*


val INPUTFILE = "/examples/SecurityAccountOverview_0123456789.txt"

data class SecurityAccountOverviewKt(val accountNumber: String,
                                     val riskCategory: RiskCategoryKt,
                                     val depotOwner: DepotOwnerKt,
                                     val amount: AmountKt)

data class FixedWidthPropertiesKt(var startsAt: Int, var length: Int, var padding: Char?, var alignment: FieldAlignment) {
    //contains the extracted value
    var fieldValue: String = ""

}

data class AmountKt (val currency: String, val value: BigDecimal)
data class DepotOwnerKt(val firstName: String , val lastName: String)

enum class RiskCategoryKt(private val code: String) {
    NON_EXISTING("00"), EXECUTION_ONLY("01"), LOW("02"), MIDDLE("04"), HIGH("06"), SPECULATIVE("08");

    companion object {
        fun fromCode(code: String?): Optional<RiskCategoryKt> {
            return Arrays.stream(RiskCategoryKt.values()).filter { rc: RiskCategoryKt -> rc.code.equals(code, ignoreCase = true) }.findFirst()
        }
    }
}

fun SecurityAccountOverview(block: SecurityAccountOverviewBuilder.() -> Unit) = SecurityAccountOverviewBuilder().apply{block}.build()

class SecurityAccountOverviewBuilder {
    private var accountNumber = ""
    private var riskCategory = RiskCategoryKt.NON_EXISTING
    private var depotOwner = DepotOwnerKt("", "")
    private var amount = AmountKt("EUR", BigDecimal.valueOf(10.10))

    //lambda provides accountNumber through its logic -> but the goal is to
    //contain FixedwidthProperties which will have to evaluated for the real value
    fun accountNumber(block: () -> String) { accountNumber = block() }

    fun riskCategory(block: RiskCategoryKt.() -> Unit) { riskCategory = RiskCategoryBuilder().apply { block }.build() }

    fun depotOwner(block: DepotOwnerKt.() -> Unit) { depotOwner = DepotOwnerBuilder().apply{block}.build() }

    fun amount(block: AmountKt.() -> Unit ) { amount = AmountBuilder().apply { block }.build() }

    fun build() = SecurityAccountOverviewKt(accountNumber, riskCategory, depotOwner, amount)
}

class RiskCategoryBuilder {
    private var riskCategory = RiskCategoryKt.NON_EXISTING

    fun riskCategory(riskCategory: () -> FixedWidthPropertiesKt) {
        val extracted = ConfigParser(INPUTFILE).readField(riskCategory())
        this.riskCategory = RiskCategoryKt.valueOf(extracted)
    }

    fun build() = riskCategory
}

class DepotOwnerBuilder {
    private var firstName = ""
    private var lastName = ""

    fun firstName(firstName: () -> FixedWidthPropertiesKt) {
        val extracted = ConfigParser(INPUTFILE).readField(firstName())
        this.firstName = extracted
    }

    fun lastName(lastName: () -> FixedWidthPropertiesKt)  {
        val extracted =  ConfigParser(INPUTFILE).readField(lastName())
        this.lastName = extracted
    }

    fun build() = DepotOwnerKt(firstName, lastName)
}

class AmountBuilder {
    private var currency = ""
    private var value: BigDecimal = BigDecimal.ZERO

    fun currency(currency: () -> FixedWidthPropertiesKt) {
        val extracted = ConfigParser(INPUTFILE).readField(currency())
        this.currency = extracted
    }
    fun value(value: () -> FixedWidthPropertiesKt) {
        val extracted = ConfigParser(INPUTFILE).readField(value())
        this.value = extracted.toBigDecimal()
    }

    fun build() = AmountKt(currency, value)
}
