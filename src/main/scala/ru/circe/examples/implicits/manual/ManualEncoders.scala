package ru.circe.examples.implicits.manual

import io.circe.syntax.EncoderOps
import io.circe.{ Encoder, Json }
import ru.circe.examples.implicits.ExampleClasses.{ Actor, PersonInfo }


trait ManualEncoders {

  implicit val personInfoEncoder: Encoder[PersonInfo] = info =>
    Json.obj(
      "sex"            -> info.sex.asJson,
      "age"            -> info.age.asJson,
      "marital_status" -> info.marital_status.asJson
    )

  implicit val actorEncoder: Encoder[Actor] = actor =>
    Json.obj(
      "name" -> actor.name.asJson,
      "info" -> actor.info.asJson
    )

}
