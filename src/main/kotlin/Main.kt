import json.models.*
import json.visitors.InscritosValidationVisitor
import json.visitors.NumberValidationVisitor
import json.visitors.ObjectVisitor
import json.visitors.ValueVisitor

fun main() {






    val UC =  JsonString("PA")
    val ECTS = JsonNumber(6.0)
    val DATA_EXAME = JsonNumber(null)
    val NUMERO =JsonKeyValuePair("numero", JsonNumber(101101.1,3))
    val NUMERO2 =JsonKeyValuePair("numero", JsonNumber(101102,3))
    val NUMERO3 =JsonKeyValuePair("numero", JsonNumber(26503,3))
    val NOME =JsonKeyValuePair("nome", JsonString("Dave Farley",3))
    val NOME2 =JsonKeyValuePair("nome", JsonString("Martin Fowler",3))
    val NOME3 =JsonKeyValuePair("nome", JsonString("André Santos",3))
    val INTERNACIONAL = JsonKeyValuePair("internacional", JsonBoolean(true,3))
    val INTERNACIONAL2 = JsonKeyValuePair("internacional", JsonBoolean(true,3))
    val INTERNACIONAL3 = JsonKeyValuePair("internacional", JsonBoolean(false,3))
    //val jsonObject = JsonObject(listOf(NUMERO, NOME),2)
    val jsonObject = JsonObject(listOf(NUMERO, NOME,INTERNACIONAL),2)
    val jsonObject2 = JsonObject(listOf(NUMERO2, NOME2,INTERNACIONAL2),2)
    val jsonObject3 = JsonObject(listOf(NUMERO3, NOME3,INTERNACIONAL3),2)
    val inscritosList = JsonList(listOf(jsonObject,jsonObject2,jsonObject3),1)

    val uc = JsonKeyValuePair("uc", UC)
    val ects = JsonKeyValuePair("ects", ECTS)
    val data_exame = JsonKeyValuePair("data-exame", DATA_EXAME)
    val inscritos = JsonKeyValuePair("inscritos", inscritosList)
    val obj = JsonObject(listOf(uc,ects,data_exame,inscritos))

    print(obj)



        var visitor = ValueVisitor("nome")
        obj.accept(visitor)
        val visitorResult = visitor.getValues()
        println(visitorResult)

        val list = listOf("nome", "numero")

        var objectVisitor = ObjectVisitor(list)
        obj.accept(objectVisitor)
        val objectVisitorResult = objectVisitor.getObjects()
        println(objectVisitorResult)


        var numberValidatorVisitor = NumberValidationVisitor()
        obj.accept(numberValidatorVisitor)
        val numberVisitorResult = numberValidatorVisitor.isValid
        println("isValid = $numberVisitorResult")

        var inscritosValidationVisitor = InscritosValidationVisitor()
        obj.accept(inscritosValidationVisitor)
        val inscritosVisitorResult = inscritosValidationVisitor.isValid
        println("isValid = $inscritosVisitorResult")



}