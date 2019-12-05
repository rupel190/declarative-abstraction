package at.technikum.wien.mse.swe

import at.technikum.wien.mse.swe.dlsspecific.ConfigParser
import at.technikum.wien.mse.swe.dlsspecific.FixedWidthProperties
import junit.framework.Assert.assertEquals
import org.junit.Test

class ConfigParserTest {

    @Test
    fun testTransactionName() {
        val configParser = ConfigParser("/examples/SecurityAccountOverview_0123456789.txt")
        val props = FixedWidthProperties( 0, 40, ' ', "LEFT")
        val field = configParser.readField(props)
        assertEquals("SecurityAccountOverview", field)
    }

    @Test
    fun testSecurityAccountNumber() {
        val configParser = ConfigParser("/examples/SecurityAccountOverview_0123456789.txt")
        val props = FixedWidthProperties(40, 10, '0', "RIGHT")
        val field = configParser.readField(props)
        assertEquals("123456789", field)
    }

    @Test
    fun testRiskCategory() {
        val configParser = ConfigParser("/examples/SecurityAccountOverview_0123456789.txt")
        val props = FixedWidthProperties(50, 2, null, "LEFT")
        val field = configParser.readField(props)
        assertEquals("00", field)
    }

    @Test
    fun testDepotOwnerLastName() {
        val configParser = ConfigParser("/examples/SecurityAccountOverview_0123456789.txt")
        val props = FixedWidthProperties(52, 30, ' ', "RIGHT")
        val field = configParser.readField(props)
        assertEquals("MUSTERMANN", field)
    }

    @Test
    fun testDepotOwnerFirstName() {
        val configParser = ConfigParser("/examples/SecurityAccountOverview_0123456789.txt")
        val props = FixedWidthProperties(82, 30, ' ', "RIGHT")
        val field = configParser.readField(props)
        assertEquals("MAX UND MARIA", field)
    }

    @Test
    fun testCurrency() {
        val configParser = ConfigParser("/examples/SecurityAccountOverview_0123456789.txt")
        val props = FixedWidthProperties(112, 3, ' ', "LEFT")
        val field = configParser.readField(props)
        assertEquals("EUR", field)
    }

    @Test
    fun testBalance() {
        val configParser = ConfigParser("/examples/SecurityAccountOverview_0123456789.txt")
        val props = FixedWidthProperties(115, 17, ' ', "RIGHT")
        val field = configParser.readField(props)
        assertEquals("1692.45", field)
    }


















}