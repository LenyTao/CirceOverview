package ru.circe.examples.formatter

import cats.implicits.{catsSyntaxEitherId, catsSyntaxOptionId}
import com.jakewharton.fliptables.FlipTable
import io.circe.jawn.decode
import io.circe.parser.parse
import io.circe.{Decoder, Encoder, HCursor, Json}

object JsonFormatter {

  /**
    *This is method just extracted Json from Option[Json]
    */
  def extractJson(safeJson: Option[Json]): Json = safeJson.getOrElse(Json.Null)

  /**
    * This is method parse String from Option[Json]
    */
  def parseToJson(data: String): Option[Json] = parse(data).toOption

  /**
    * This method creates Json List
    *{{{
    * Use it if you have Json with a lot of similar data
    *
    * For example:
    *
    * [
    * {"car_id" : 1,"car_colour" : "red"},
    * {"car_id" : 2,"car_colour" : "blue"}
    * ]
    *}}}
    */
  def parseToJsonList(data: String): List[Json] = parse(data).fold(_ => List.empty, _.asArray.toList.flatten)

  /**
    * This method is converts string to JSON
    *
    * {{{
    * If you are using this method add:
    * `import io.circe.generic.auto._`
    *}}}
    */
  def convertToJson[T: Encoder](obj: T): String = Encoder[T].apply(obj).noSpaces

  /**
    * This method is converts string to Obj
    *
    * {{{
    * If you are using this method add:
    * `import io.circe.generic.auto._`
    *}}}
    */
  def convertToObj[T: Decoder](data: String): Option[T] = decode[T](data).toOption

  implicit val decoderForMap: Decoder[Map[String, String]] = { (jsonCursor: HCursor) =>
    (
      for {
        k <- jsonCursor.keys.toList.flatten
        v <- jsonCursor.value.findAllByKey(k)
      } yield k -> v.asString.fold(v.toString)(identity)
    ).toMap.asRight
  }

  /**
    * This method makes a Map from a JSON string:
    *{{{
    *
    * For example:
    *
    *"{\"master\":\"local\"}"
    *}}}
    *
    */
  def getJsonTags(data: String): Map[String, String] = decode(data)(decoderForMap).getOrElse(Map.empty)

  /**
    * Get data on the specified fields from the passed Json.
    *
    * This method supports both full and relative paths to fields.
    *
    * For example:
    *{{{
    * * Unique field - folder_id
    * * Full path - task.source_folder.folder_id
    * * Relative path - target_folder.folder_id
    *}}}
    */
  def getTagValueFromJson(fieldPath: String, jsonData: Json): Option[Json] =
    fieldPath
      .split('.')
      .foldLeft(jsonData.some) { (json, field) =>
        json.flatMap(_.findAllByKey(field).headOption)
      }

  /**
    * Shows a table with information about a person in the console
    * */
  def showPersonalInfo(fields: List[String], jsonList: List[Json]): Unit = {

    def printTableView(headers: Array[String], data: Array[Array[String]]): Unit = {
      val clearHeaders = headers.map(x=>x.split('.').head)
      println(
        FlipTable.of(clearHeaders, data)
      )
    }

    val personalDataList =
      for {
        json       <- jsonList
        singleLine <- fields.flatMap(getTagValueFromJson(_, json)).some
      } yield singleLine.map(json => json.asString.fold(json.toString)(identity)).toArray

    printTableView(fields.toArray, personalDataList.toArray)
  }
}
