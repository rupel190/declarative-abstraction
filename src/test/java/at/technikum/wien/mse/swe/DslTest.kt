package at.technikum.wien.mse.swe

import at.technikum.wien.mse.swe.dlsspecific.SecurityAccountOverview
import at.technikum.wien.mse.swe.dlsspecific.SecurityAccountOverviewKt
import org.junit.Test

class DslTest {
    @Test
    fun assertDsl() {
        SecurityAccountOverview {
            accountNumber { FixedWidthProperties(10, 10,  '0', "LEFT") }
            riskCategory { FixedWidthProperties(10, 10,  '0', "LEFT") }
            depotOwner {
                firstname { FixedWidthProperties(10, 10,  '0', "LEFT") }
                lastname{ FixedWidthProperties(10, 10,  '0', "LEFT") }
            }
            amount {
                currency { FixedWidthProperties(10, 10,  '0', "LEFT") }
                balance { FixedWidthProperties(10, 10,  '0', "LEFT") }
            }
        }
    }
}