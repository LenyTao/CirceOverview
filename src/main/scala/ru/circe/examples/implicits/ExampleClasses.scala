package ru.circe.examples.implicits

object ExampleClasses {

  case class PersonInfo(sex: String, age: Int, marital_status: Option[String])

  case class Actor(name: String, info: PersonInfo)

}
