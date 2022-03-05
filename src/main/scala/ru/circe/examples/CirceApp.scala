package ru.circe.examples
import ru.circe.examples.formatter.JsonFormatter
import ru.circe.examples.implicits.ExampleClasses.{Actor, PersonInfo}
import ru.circe.examples.implicits.union.semiautomatic.SemiAutomaticUnionConverter


object CirceApp extends App with SemiAutomaticUnionConverter{

    println(
      s"""
         |${"-" * 100}
         |Parsing Demonstration
         |${"-" * 100}
         |""".stripMargin
    )

    val simplyStr: String =
      """
        |{
        |    "name": "Leonardo Dicaprio",
        |    "sex":  "male",
        |    "films": [
        |      "Titanic",
        |      "The Wolf of Wall Street",
        |      "The Revenant"
        |    ]
        |  }
        |""".stripMargin

    val res1 = JsonFormatter.parseToJson(simplyStr)

    println(res1)

    println("-" * 100)

    val listSimplyStr: String =
      """
        |[
        |{
        |    "name": "Leonardo Dicaprio",
        |    "sex":  "male",
        |    "films": [
        |      "Titanic",
        |      "The Wolf of Wall Street",
        |      "The Revenant"
        |    ]
        |  },
        |  {
        |    "name": "Chloe Grace Moretz",
        |    "sex":  "female",
        |    "films": [
        |      "The Equalizer",
        |      "Carrie",
        |      "Movie 43"
        |    ]
        |  }
        |  ]
        |""".stripMargin

    val res2 = JsonFormatter.parseToJsonList(listSimplyStr)
    println(res2)

    println("-" * 100)

    println(
      s"""
         |${"-" * 100}
         |Converter Demonstration
         |${"-" * 100}
         |""".stripMargin
    )

    val actor: Actor =
      Actor(
        "Leonardo Dicaprio",
        PersonInfo(
          "male",
          46,
          Some("single")
        )
      )

    val actorJson: String = JsonFormatter.convertToJson[Actor](actor)
    val infoJson: String  = JsonFormatter.convertToJson[PersonInfo](actor.info)

    println(actorJson)
    println(infoJson)

    val res5: Option[Actor]      = JsonFormatter.convertToObj[Actor](actorJson)
    val res6: Option[PersonInfo] = JsonFormatter.convertToObj[PersonInfo](infoJson)

    println(res5.get)
    println(res6.get)

    println("-" * 100)

    println(
      s"""
         |${"-" * 100}
         |Custom Encoder Demonstration
         |${"-" * 100}
         |""".stripMargin
    )
    val strForMap =
      """
        |{
        | "name": "Leonardo Dicaprio",
        | "sex" : "male",
        | "age" : 46,
        | "vacancy":  "actor"
        | }
        |""".stripMargin

    println(JsonFormatter.getJsonTags(strForMap))
    println("-" * 100)

    println(
      s"""
         |${"-" * 100}
         |Extractor Value Demonstration
         |${"-" * 100}
         |""".stripMargin
    )

    val complexStr =
      """
        |{
        | "name": "Leonardo Dicaprio",
        | "vacancy":  "actor",
        | "personal_info" : {
        |    "sex" : "male",
        |    "age" : 46,
        |    "marital_status" : null
        |  },
        | "films": [
        |           {
        |      "film1" : {
        |                 "film_id":1,
        |                 "film_name":"Titanic"
        |                }
        |             },
        |      {
        |      "film2" : {
        |                 "film_id":2,
        |                 "film_name":"The Wolf of Wall Street"
        |                }
        |                },
        |      {
        |      "film3" : {
        |                 "film_id":3,
        |                 "film_name":"The Revenant"
        |                }
        |                }
        |    ]
        |  }
        |""".stripMargin

    val res3 =
      JsonFormatter.getTagValueFromJson(
        "film1.film_name",
        JsonFormatter.extractJson(JsonFormatter.parseToJson(complexStr))
      )
    println(
      res3
    )

    println("-" * 100)

    val complexListStr =
      """
        |[
        |{
        | "name": "Leonardo Dicaprio",
        | "profession":  "actor",
        | "personal_info" : {
        |    "sex" : "male",
        |    "age" : 46,
        |    "marital_status" : null
        |  },
        | "films": [
        |           {
        |      "film1" : {
        |                 "film_id":1,
        |                 "film_name":"Titanic"
        |                }
        |             },
        |      {
        |      "film2" : {
        |                 "film_id":2,
        |                 "film_name":"The Wolf of Wall Street"
        |                }
        |                },
        |      {
        |      "film3" : {
        |                 "film_id":3,
        |                 "film_name":"The Revenant"
        |                }
        |                }
        |    ]
        |  },
        |{
        | "name": "Chloe Grace Moretz",
        | "profession":  "actress",
        | "personal_info" : {
        |    "sex" : "female",
        |    "age" : 24,
        |    "marital_status" : null
        |  },
        | "films": [
        |           {
        |      "film1" : {
        |                 "film_id":1,
        |                 "film_name":"The Equalizer"
        |                }
        |             },
        |      {
        |      "film2" : {
        |                 "film_id":2,
        |                 "film_name":"Carrie"
        |                }
        |                },
        |      {
        |      "film3" : {
        |                 "film_id":3,
        |                 "film_name":"Movie 43"
        |                }
        |                }
        |    ]
        |  }
        |  ]
        |""".stripMargin

    JsonFormatter.showPersonalInfo(
      List("name", "profession", "sex", "age", "marital_status","film1.film_name"),
      JsonFormatter.parseToJsonList(complexListStr)
    )


}
