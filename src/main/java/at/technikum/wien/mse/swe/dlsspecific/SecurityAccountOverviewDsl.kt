package at.technikum.wien.mse.swe.dlsspecific

import java.math.BigDecimal

//ugh
var INPUTFILEPATH = "/examples/SecurityConfiguration_AT0000937503.txt"

fun SecurityAccountOverview(block: SecurityAccountOverviewBuilder.() -> Unit) = SecurityAccountOverviewBuilder().apply(block).build()
fun SecurityConfiguration(block: SecurityConfigurationBuilder.() -> Unit) = SecurityConfigurationBuilder().apply(block).build()

//region data classes
@DslMarker annotation class SecurityDsl
data class SecurityConfiguration(
        val isin: Isin,
        val riskCategory: RiskCategory,
        val name: String,
        val yearHighest: Amount,
        val yearLowest: Amount
)
data class SecurityAccountOverviewKt(val accountNumber: String,
                                     val riskCategory: RiskCategory,
                                     val depotOwner: DepotOwner,
                                     val amount: Amount)

data class FixedWidthProperties(var startsAt: Int, var length: Int, var padding: Char?, var alignment: String) {
    //contains the extracted value
    var fieldValue: String = ConfigParser(INPUTFILEPATH).readField(this)

}

data class Isin (val value: String)
data class Amount (val currency: String, val balance: BigDecimal)
data class DepotOwner(val firstName: String, val lastName: String)

enum class RiskCategory(private val code: String) {
    NON_EXISTING("00"), EXECUTION_ONLY("01"), LOW("02"), MIDDLE("04"), HIGH("06"), SPECULATIVE("08");
}
//endregion

//region builders
@SecurityDsl class SecurityAccountOverviewBuilder {

    private var accountNumber = ""
    private var riskCategory = RiskCategory.NON_EXISTING
    private var depotOwner = DepotOwner("", "")
    private var amount = Amount("", BigDecimal.ZERO)

    // todo: was not called before, why? ->
    // https://stackoverflow.com/questions/45731647/whats-the-difference-between-curly-braces-and-normal-brackets-in-rxjava-with-ko
    fun accountNumber(block: () -> FixedWidthProperties) { accountNumber = block().fieldValue }
    fun riskCategory(block: RiskCategoryBuilder.() -> Unit) { riskCategory = RiskCategoryBuilder().apply ( block ).build() }
    fun depotOwner(block: DepotOwnerBuilder.() -> Unit) { depotOwner = DepotOwnerBuilder().apply(block).build() }
    fun amount(block: AmountBuilder.() -> Unit ) { amount = AmountBuilder().apply ( block ).build() }
    fun build() = SecurityAccountOverviewKt(accountNumber, riskCategory, depotOwner, amount)

    fun path(s: String) {
        INPUTFILEPATH = s
    }
}

@SecurityDsl class SecurityConfigurationBuilder {

    private var isin = Isin("")
    private var riskCategory = RiskCategory.NON_EXISTING
    private var name = ""
    private var yearHighest = Amount("", BigDecimal.ZERO)
    private var yearLowest = Amount("", BigDecimal.ZERO)

    fun isin(block: IsinBuilder.() -> Unit) { isin = IsinBuilder().apply(block).build() }
    fun riskCategory(block: RiskCategoryBuilder.() -> Unit) { riskCategory = RiskCategoryBuilder().apply ( block ).build() }
    fun name(block: () -> FixedWidthProperties) { name = block().fieldValue }
    fun yearHighest(block: AmountBuilder.() -> Unit ) { yearHighest = AmountBuilder().apply ( block ).build() }
    fun yearLowest(block: AmountBuilder.() -> Unit ) { yearLowest = AmountBuilder().apply ( block ).build() }

    fun build() = SecurityConfiguration(isin, riskCategory, name, yearHighest, yearLowest)

    fun path(s: String) {
        INPUTFILEPATH = s
    }
}

 @SecurityDsl class IsinBuilder {
    private var value : String = ""

    fun value(isin: () -> FixedWidthProperties) {
        val extracted = ConfigParser(INPUTFILEPATH).readField(isin())
        this.value = extracted
    }
     fun build() = Isin(value)
}

@SecurityDsl class RiskCategoryBuilder {
    private var riskCategory = RiskCategory.NON_EXISTING

    fun riskCategory(riskCategory: () -> FixedWidthProperties) {
        val extracted = ConfigParser(INPUTFILEPATH).readField(riskCategory())
        this.riskCategory = RiskCategory.valueOf(extracted)
    }

    fun build() = riskCategory
}

@SecurityDsl class DepotOwnerBuilder {
    private var firstName = ""
    private var lastName = ""

    fun firstName(firstName: () -> FixedWidthProperties) {
        val extracted = ConfigParser(INPUTFILEPATH).readField(firstName())
        this.firstName = firstName().fieldValue
    }

    fun lastName(lastName: () -> FixedWidthProperties)  {
        val extracted =  ConfigParser(INPUTFILEPATH).readField(lastName())
        this.lastName = lastName().fieldValue
    }

    fun build() = DepotOwner(firstName, lastName)
}

@SecurityDsl class AmountBuilder {
    private var currency = ""
    private var balance: BigDecimal = BigDecimal.ZERO

    fun currency(currency: () -> FixedWidthProperties) {
        this.currency = currency().fieldValue
    }
    fun balance(balance: () -> FixedWidthProperties) {
        this.balance = balance().fieldValue.toBigDecimal()
    }

    fun build() = Amount(currency, balance)
}
//endregion
