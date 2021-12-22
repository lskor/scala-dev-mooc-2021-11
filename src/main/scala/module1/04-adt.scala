package module1

import java.time.LocalDate

object adt {

  object tuples {

    /** Tuples
      */
    type ProductUnitBoolean = (Unit, Boolean)

    /** Создать возможные экземпляры с типом ProductUnitBoolean
      */

      val p1: ProductUnitBoolean = ((), true)
      val p2: ProductUnitBoolean = ((), false)

    /** Реализовать тип Person который будет содержать имя и возраст
      */

    type Person = (String, Int)

    /**  Реализовать тип `CreditCard` который может содержать номер (String),
      *  дату окончания (java.time.YearMonth), имя (String), код безопастности (Short)
      */

    type CreditCard = (String, java.time.YearMonth, String, Short)

    lazy val cc: CreditCard = ???


  }

  object case_classes {

    /** Case classes
      */

    //  Реализовать Person с помощью case класса
    case class Person(name: String, age: Int)

    // Создать экземпляр для Tony Stark 42 года
    lazy val tonyStark = Person("Tony", 42)

    // Создать case class для кредитной карты
    case class CreditCard(number: String, valid: java.time.YearMonth, name: String, cv: Short)

    lazy val cc: CreditCard = ???

  }

  object either {

    /** Sum
      */

    /** Either - это наиболее общий способ хранить один из двух или более кусочков информации в одно время.
      * Также как и кортежи обладает целым рядом полезных методов
      * Иммутабелен
      */

    // trait Ether   Right() extends Either Left() extends Either

    type IntOrString = Either[Int, String]

    /** Реализовать экземпляр типа IntOrString с помощью конструктора Right(_)
      */
    val intOrString: IntOrString = Right("Hello")
    val intOrString2: IntOrString = Left(10)

    final case class CreditCard()
    final case class WireTransfer()
    final case class Cash()

    /** \
      * Реализовать тип PaymentMethod который может быть представлен одной из альтернатив
      */
    type PaymentMethod = Either[CreditCard, Either[WireTransfer, Cash]]

  }

  object sealed_traits {

    /** Также Sum type можно представить в виде sealed trait с набором альтернатив
      */

      sealed trait PaymentMethod
      final case class CreditCard() extends PaymentMethod
      final case class WireTransfer() extends PaymentMethod
      final case class Cash() extends PaymentMethod


  }

  object cards {
    sealed trait Suit // Club | Diamond 


    case object Club extends Suit
    case object Diamond extends Suit
    case object Spade extends Suit
    case object Heart extends Suit

    sealed trait  Rank

    case object Two extends Rank
    case object Three extends Rank
    case object Four extends Rank
    case object Five extends Rank
    case object Six extends Rank
    case object Seven extends Rank
    case object Eight extends Rank
    case object Nine extends Rank
    case object Ten extends Rank
    case object Jack extends Rank
    case object Queen extends Rank
    case object King extends Rank

    case class Card(suit: Suit, rank: Rank) // Suit * Rank
    type Deck = Set[Card]
    type Hand = Set[Card]

    case class Game(deck: Deck, players: List[Player])
    case class Player(name: String, hand: Hand)

    type PickupCard = (Hand, Card) => Card 

  }

}
