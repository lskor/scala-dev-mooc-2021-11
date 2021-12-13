package module1
import scala.util.control.Breaks._

object App {

  def main(args: Array[String]): Unit = {

    def doomyFunc(a: String) = {
      Thread.sleep(1000)
      println(a)
    }

    val doomyFuncWithLoggingTime: String => Unit = hof.logRunningTime(doomyFunc)

    val v: PartialFunction[(Int, Int), Int] = {
      case (x, y) if y != 0 => x / y
    }

    val v2: PartialFunction[(Int, Int), Int] =
      new PartialFunction[(Int, Int), Int] {
        def isDefinedAt(v: (Int, Int)): Boolean = ???
        def apply(v: (Int, Int)): Int = ???
      }

    println(v.isDefinedAt(10, 1))
    println(v.isDefinedAt(10, 0))

    trait Printer {
      def print(str: String): Unit
    }

    val printer: Printer = str => println(str)

    printer.print("Hello")

  }

}
