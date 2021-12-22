package module1

object pattern_matching{
    // Pattern matching



       class Person(name: String, age: Int)

       object Person{
           def unapply(p: Person): Option[(String, Int)] = ???
           def apply(name: String, age: Int): Person = ???
       }

       lazy val tonyStark = Person("Tony", 43)
      /**
       * используя паттерн матчинг напечатать имя и возраст
       */


       def printNameAndAge: Unit = tonyStark match {
           case Person(n, a) => println(s"$n $a")
       }






      final case class Employee(name: String, address: Address)
      final case class Address(street: String, number: Int)

      val alex = Employee("Alex", Address("XXX", 221))

      /**
       * воспользовавшись паттерн матчингом напечатать номер из поля адрес
       */

      alex match {
          case Employee(_ , Address(_, n)) => println(n)
      }


      /**
       * Паттерн матчинг может содержать литералы.
       * Реализовать паттерн матчинг на alex с двумя кейсами.
       * 1. Имя должно соотвествовать Alex
       * 2. Все остальные
       */
       alex match {
           case Employee("Alex", _) => println("It's Alex")
           case _ => println("It's not Alex")
       }
      


      /**
       * Паттерны могут содержать условия. В этом случае case сработает,
       * если и паттерн совпал и условие true.
       * Условия в паттерн матчинге называются гардами.
       */



      /**
       * Реализовать паттерн матчинг на alex с двумя кейсами.
       * 1. Имя должно начинаться с A
       * 2. Все остальные
       */
       alex match {
           case Employee(name, _) if name.startsWith("A") => println("It's Alex")
           case _ => println("It's not Alex")
       }



      /**
       *
       * Мы можем поместить кусок паттерна в переменную использую `as` паттерн,
       * x @ ..., где x это любая переменная. Это переменная может использоваться, как в условии,
       * так и внутри кейса
       */

       alex match {
           case a @ Employee(name, _) if name.startsWith("A") => a
           case _ => println("It's not Alex")
       }

    


      /**
       * Мы можем использовать вертикальную черту `|` для матчинга на альтернативы
       */

       sealed trait A
       case class B(v: Int) extends A
       case class C(v: Int) extends A
       case class D(v: Int) extends A

      val a: A = ???

      a match {
          case B(_) | C(_) => println("")
          case D(v) => println("")
      }
}