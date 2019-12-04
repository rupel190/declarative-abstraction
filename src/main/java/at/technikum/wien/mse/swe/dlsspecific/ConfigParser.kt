package at.technikum.wien.mse.swe.dlsspecific

class ConfigParser (val fileName: String){

    fun readFileAsLinesUsingGetResourceAsStream()
            = javaClass.getResourceAsStream(fileName).bufferedReader()



    fun readField(props: FixedWidthPropertiesKt) : String {
        val reader = readFileAsLinesUsingGetResourceAsStream()
        val line = reader.readLine()

        val rawField = line.substring(props.startsAt, props.startsAt + props.length)
        if(props.padding!= null) {
            return rawField.trim(props.padding!!)
        }
        return rawField
    }


}