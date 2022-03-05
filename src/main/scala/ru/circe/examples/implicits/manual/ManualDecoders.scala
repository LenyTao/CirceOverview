package ru.circe.examples.implicits.manual

import io.circe.{ Decoder, HCursor }
import ru.circe.examples.implicits.ExampleClasses.{ Actor, PersonInfo }


trait ManualDecoders {

  implicit val personInfoDecoder: Decoder[PersonInfo] = (hCursor: HCursor) =>
    for {
      sex            <- hCursor.get[String]("sex")
      age            <- hCursor.get[Int]("age")
      marital_status <- hCursor.get[Option[String]]("marital_status")
    } yield PersonInfo(sex, age, marital_status)

  implicit val actorDecoder: Decoder[Actor] = (hCursor: HCursor) =>
    for {
      name       <- hCursor.get[String]("name")
      personInfo <- hCursor.get[PersonInfo]("info")
    } yield Actor(name, personInfo)
}
