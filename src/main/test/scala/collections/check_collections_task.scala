package collections

import collections.task_collections._
import org.scalatest.flatspec.AnyFlatSpec

class check_collections_task extends AnyFlatSpec {

  "check capitalizeIgnoringASCII" should "ok" in  {
    assert(capitalizeIgnoringASCII(List("Lorem", "ipsum", "dolor", "sit", "amet")) == List("Lorem", "IPSUM", "DOLOR", "SIT", "AMET"))
    assert(capitalizeIgnoringASCII(List("Оказывается", ",", "ЗвУк", "КЛАВИШЬ")) === List("Оказывается", ",", "звук", "клавишь"))
    assert(capitalizeIgnoringASCII(List("Оказывается", "aLert", "ЗвУк", "КЛАВИШЬ")) === List("Оказывается", "ALERT", "звук", "клавишь"))
  }

  "check numbersToNumericString" should "ok" in {
    val text = "Hello. I am 10 years old"
    val transformText = "Hello. I am ten years old"
    assert(numbersToNumericString(text) === transformText)
    assert(numbersToNumericString("") === "")
    assert(numbersToNumericString("1 and 1") === "one and one")
    assert(numbersToNumericString("4") === "four")
    assert(numbersToNumericString("The game's score was 2:0") === "The game's score was two:zero")
    assert(numbersToNumericString("q2w3e4r5t6y") === "qtwowthreeefourrfivetsixy")
    assert(numbersToNumericString("The 20th May") === "The twozeroth May")
  }

  "check intersectionAuto" should "ok" in {
    //Is it union or intersection?
    val dealerOne = Vector(Auto("BMW", "i3"), Auto("Mazda", "X5"))
    val dealerTwo = Seq(Auto("BMW", "i3"), Auto("Mazda", "X5"))
    assert(intersectionAuto(dealerOne, dealerTwo) === Set(Auto("BMW", "i3"), Auto("Mazda", "X5")))

    // Test for union:
    val dealerOneSecond = Seq(Auto("BMW", "i3"), Auto("Mazda", "X5"), Auto("VW", "Polo"), Auto("Mazda", "X5"))
    assert(intersectionAuto(dealerOneSecond, dealerTwo) === Set(Auto("BMW", "i3"), Auto("Mazda", "X5"), Auto("VW", "Polo")))
  }

  "check filterAllLeftDealerAutoWithoutRight" should "ok" in {
    val dealerOne = Vector(Auto("BMW", "i3"), Auto("Mazda", "X5"))
    val dealerTwo = Seq(Auto("BMW", "i3"), Auto("Mazda", "X5"))
    assert(filterAllLeftDealerAutoWithoutRight(dealerOne, dealerTwo) === Set.empty)

    val dealerOneSecond = Vector(Auto("BMW", "i3"), Auto("Mazda", "X5"))
    val dealerTwoSecond = Seq(Auto("BMW", "i3"))
    assert(filterAllLeftDealerAutoWithoutRight(dealerOneSecond, dealerTwoSecond) === Set(Auto("Mazda", "X5")))
  }

}
