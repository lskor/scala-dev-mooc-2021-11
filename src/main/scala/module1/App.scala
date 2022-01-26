import scala.util.control.Breaks._
import module3.functional_effects
import module3.zioRecursion
import module3.multipleErrors
import zio.ExitCode
import zio.URIO
import zio.ZEnv
import zio.ZIO

object App {

  def main(args: Array[String]): Unit = {
    zio.Runtime.default.unsafeRun(multipleErrors.app)
  }

}

object ZioApp extends zio.App{
  def run(args: List[String]): URIO[ZEnv, ExitCode] = {
    zioRecursion.factorialZ(5)
      .flatMap(v => zio.console.putStrLn(v.toString())).exitCode
  }
}