package com.quiz.rest.Main

import reactivemongo.api._
import reactivemongo.api.collections.default.BSONCollection
import reactivemongo.bson.BSONDocument
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by lukasz.ogan on 7/27/16.
  */
object Database {

  val collection = connect()

  def connect(): BSONCollection = {

    var driver = new MongoDriver
    val connection = driver.connection(List("localhost"))

    val db = connection("akka")
    db.connection("stocks")


  }

  def findAllTickers(): Future[List[BSONDocument]] = {

    val query = BSONDocument()
    val filter = BSONDocument("Company" -> 1, "Country" -> 2, "Ticker" -> 1)


    // which results in a Future[List[BSONDocument]]
    Database.collection
      .find(query, filter)
      .cursor[BSONDocument]
      .collect[List]()
  }

  def findTicker(ticker: String) : Future[Option[BSONDocument]] = {
    val query = BSONDocument("Ticker" -> ticker)

    Database.collection
      .find(query)
      .one
  }

}