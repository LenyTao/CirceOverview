package ru.circe.examples.implicits.union.semiautomatic

import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec
import ru.circe.examples.implicits.ExampleClasses.{Actor, PersonInfo}

trait SemiAutomaticUnionConverter {

  implicit val personInfoEncoder: Codec.AsObject[PersonInfo] = deriveCodec[PersonInfo]
  implicit val actorEncoder: Codec.AsObject[Actor] = deriveCodec[Actor]

}
