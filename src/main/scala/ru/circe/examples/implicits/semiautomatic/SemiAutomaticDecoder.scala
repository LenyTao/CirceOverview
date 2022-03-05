package ru.circe.examples.implicits.semiautomatic

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder
import ru.circe.examples.implicits.ExampleClasses.{ Actor, PersonInfo }

trait SemiAutomaticDecoder {

  implicit val personInfoDecoder: Decoder[PersonInfo] = deriveDecoder[PersonInfo]
  implicit val actorDecoder: Decoder[Actor]           = deriveDecoder[Actor]

}
