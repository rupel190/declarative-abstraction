package at.technikum.wien.mse.swe.dlsspecific

import java.math.BigDecimal
import java.util.*


val INPUTFILE = "/examples/SecurityAccountOverview_0123456789.txt"

@DslMarker annotation class BusinessDsl
data class SecurityAccountOverviewKt(val accountNumber: String,
                                     val riskCategory: RiskCategoryKt,
                                     val depotOwner: DepotOwnerKt,
                                     val amount: AmountKt)

data class FixedWidthPropertiesKt(var startsAt: Int, var length: Int, var padding: Char?, var alignment: String) {
    //contains the extracted value
    var fieldValue: String = ConfigParser(INPUTFILE).readField(this)

}

data class AmountKt (val currency: String, val balance: BigDecimal)
data class DepotOwnerKt(val firstName: String , val lastName: String)

enum class RiskCategoryKt(private val code: String) {
    NON_EXISTING("00"), EXECUTION_ONLY("01"), LOW("02"), MIDDLE("04"), HIGH("06"), SPECULATIVE("08");

    companion object {
        fun fromCode(code: String?): Optional<RiskCategoryKt> {
            return Arrays.stream(RiskCategoryKt.values()).filter { rc: RiskCategoryKt -> rc.code.equals(code, ignoreCase = true) }.findFirst()
        }
    }
}

fun SecurityAccountOverview(block: SecurityAccountOverviewBuilder.() -> Unit) = SecurityAccountOverviewBuilder().apply(block).build()

@BusinessDsl class SecurityAccountOverviewBuilder {
    private var accountNumber = ""
    private var riskCategory = RiskCategoryKt.NON_EXISTING
    private var depotOwner = DepotOwnerKt("", "")
    private var amount = AmountKt("", BigDecimal.ZERO)

    //todo: not called, why?
//    fun accountNumber(block: () -> FixedWidthPropertiesKt) { accountNumber = block().fieldValue }
    fun accountNumber(block: () -> FixedWidthPropertiesKt) { accountNumber = block().fieldValue }
    fun riskCategory(block: RiskCategoryBuilder.() -> Unit) { riskCategory = RiskCategoryBuilder().apply ( block ).build() }
    fun depotOwner(block: DepotOwnerBuilder.() -> Unit) { depotOwner = DepotOwnerBuilder().apply(block).build() }
    fun amount(block: AmountBuilder.() -> Unit ) { amount = AmountBuilder().apply ( block ).build() }
    fun build() = SecurityAccountOverviewKt(accountNumber, riskCategory, depotOwner, amount)
}

@BusinessDsl class RiskCategoryBuilder {
    private var riskCategory = RiskCategoryKt.NON_EXISTING

    fun riskCategory(riskCategory: () -> FixedWidthPropertiesKt) {
        val extracted = ConfigParser(INPUTFILE).readField(riskCategory())
        this.riskCategory = RiskCategoryKt.valueOf(extracted)
    }

    fun build() = riskCategory
}

@BusinessDsl class DepotOwnerBuilder {
    private var firstName = ""
    private var lastName = ""

    fun firstName(firstName: () -> FixedWidthPropertiesKt) {
        val extracted = ConfigParser(INPUTFILE).readField(firstName())
        this.firstName = firstName().fieldValue
    }

    fun lastName(lastName: () -> FixedWidthPropertiesKt)  {
        val extracted =  ConfigParser(INPUTFILE).readField(lastName())
        this.lastName = lastName().fieldValue
    }

    fun build() = DepotOwnerKt(firstName, lastName)
}

@BusinessDsl class AmountBuilder {
    private var currency = ""
    private var balance: BigDecimal = BigDecimal.ZERO

    fun currency(currency: () -> FixedWidthPropertiesKt) {
        this.currency = currency().fieldValue
    }
    fun balance(balance: () -> FixedWidthPropertiesKt) {
        this.balance = balance().fieldValue.toBigDecimal()
    }

    fun build() = AmountKt(currency, balance)
}
