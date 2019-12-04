package at.technikum.wien.mse.swe

import at.technikum.wien.mse.swe.dlsspecific.FixedWidthPropertiesKt
import at.technikum.wien.mse.swe.dlsspecific.RiskCategoryKt
import at.technikum.wien.mse.swe.dlsspecific.SecurityAccountOverview
import at.technikum.wien.mse.swe.dlsspecific.SecurityConfiguration
import junit.framework.Assert.assertEquals
import org.junit.Test

class DslTest {

    @Test
    fun `assert DSL for SecurityAccountOverview`() {

        val mySecurityAccountOverview = SecurityAccountOverview {
            accountNumber { FixedWidthPropertiesKt(40, 10, '0', "RIGHT") }
            riskCategory { FixedWidthPropertiesKt(50, 2, null, "LEFT") }
            depotOwner {
                lastName { FixedWidthPropertiesKt(52, 30, ' ', "RIGHT") }
                firstName { FixedWidthPropertiesKt(82, 30, ' ', "RIGHT") }
            }
            amount {
                currency { FixedWidthPropertiesKt(112, 3, ' ', "LEFT") }
                balance { FixedWidthPropertiesKt(115, 17, ' ', "RIGHT") }
            }
        }
        assertEquals("123456789", mySecurityAccountOverview.accountNumber)
        assertEquals(RiskCategoryKt.NON_EXISTING, mySecurityAccountOverview.riskCategory)
        assertEquals("MUSTERMANN", mySecurityAccountOverview.depotOwner.lastName)
        assertEquals("MAX UND MARIA", mySecurityAccountOverview.depotOwner.firstName)
        assertEquals("EUR", mySecurityAccountOverview.amount.currency)
        assertEquals(1692.45.toBigDecimal(), mySecurityAccountOverview.amount.balance)
    }

    @Test
    fun `assert DSL for SecurityConfiguration`() {

        val mySecurityConfiguration = SecurityConfiguration {
            isin { FixedWidthPropertiesKt(40, 10, '0', "LEFT") }
            riskCategory { FixedWidthPropertiesKt(50, 2, null, "LEFT") }
            name { FixedWidthPropertiesKt(50, 2, null, "RIGHT") }
            yearHighest {
                currency { FixedWidthPropertiesKt(112, 3, ' ', "LEFT") }
                balance { FixedWidthPropertiesKt(115, 17, ' ', "RIGHT") }
            }
            yearLowest {
                currency { FixedWidthPropertiesKt(112, 3, ' ', "LEFT") }
                balance { FixedWidthPropertiesKt(115, 17, ' ', "RIGHT") }
            }
            }
        }
    }
}