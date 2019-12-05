package at.technikum.wien.mse.swe

import at.technikum.wien.mse.swe.dlsspecific.FixedWidthProperties
import at.technikum.wien.mse.swe.dlsspecific.RiskCategory
import at.technikum.wien.mse.swe.dlsspecific.SecurityAccountOverview
import at.technikum.wien.mse.swe.dlsspecific.SecurityConfiguration
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class DslTest {

    @Test
    fun `assert DSL for SecurityAccountOverview`() {

        val mySecurityAccountOverview = SecurityAccountOverview {
            path("/examples/SecurityAccountOverview_0123456789.txt")
            accountNumber { FixedWidthProperties(40, 10, '0', "RIGHT") }
            riskCategory { FixedWidthProperties(50, 2, null, "LEFT") }
            depotOwner {
                lastName { FixedWidthProperties(52, 30, ' ', "RIGHT") }
                firstName { FixedWidthProperties(82, 30, ' ', "RIGHT") }
            }
            amount {
                currency { FixedWidthProperties(112, 3, ' ', "LEFT") }
                balance { FixedWidthProperties(115, 17, ' ', "RIGHT") }
            }
        }
        assertEquals("123456789", mySecurityAccountOverview.accountNumber)
        assertEquals(RiskCategory.NON_EXISTING, mySecurityAccountOverview.riskCategory)
        assertEquals("MUSTERMANN", mySecurityAccountOverview.depotOwner.lastName)
        assertEquals("MAX UND MARIA", mySecurityAccountOverview.depotOwner.firstName)
        assertEquals("EUR", mySecurityAccountOverview.amount.currency)
        assertEquals(1692.45.toBigDecimal(), mySecurityAccountOverview.amount.balance)
    }

    @Test
    fun `assert DSL for SecurityConfiguration`() {

        val mySecurityConfiguration = SecurityConfiguration {
            path ("/examples/SecurityConfiguration_AT0000937503.txt")
            isin { value { FixedWidthProperties(40, 10, null, "LEFT") } }
            riskCategory { FixedWidthProperties(52, 2, null, "LEFT") }
            name { FixedWidthProperties(54, 30, ' ', "RIGHT") }
            yearHighest {
                currency { FixedWidthProperties(84, 3, ' ', "LEFT") }
                balance { FixedWidthProperties(87, 10, ' ', "RIGHT") }
            }
            yearLowest {
                currency { FixedWidthProperties(84, 3, ' ', "LEFT") }
                balance { FixedWidthProperties(97, 10, ' ', "RIGHT") }
            }
        }
        assertEquals("AT0000937503", mySecurityConfiguration.isin.value,  mySecurityConfiguration.isin.value)
        assertEquals(RiskCategory.NON_EXISTING, mySecurityConfiguration.riskCategory)
        assertEquals("voestalpine Aktie", mySecurityConfiguration.name, mySecurityConfiguration.name)
        assertEquals("EUR", mySecurityConfiguration.yearLowest.currency, mySecurityConfiguration.yearLowest.currency)
        assertEquals( BigDecimal("29.60"), mySecurityConfiguration.yearLowest.balance)
        assertEquals("EUR", mySecurityConfiguration.yearHighest.currency)
        assertEquals(BigDecimal("54.98"), mySecurityConfiguration.yearHighest.balance)
    }
}