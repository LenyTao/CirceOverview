package ru.circe.examples.implicits.semiautomatic

import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder
import ru.circe.examples.implicits.ExampleClasses.{ Actor, PersonInfo }

trait SemiAutomaticEncoder {

  implicit val personInfoEncoder: Encoder.AsObject[PersonInfo] = deriveEncoder[PersonInfo]
  implicit val actorEncoder: Encoder.AsObject[Actor]           = deriveEncoder[Actor]

}
