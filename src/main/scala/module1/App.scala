import scala.util.control.Breaks._
import module3.functional_effects
import module3.zioRecursion
import module3.multipleErrors
import zio.ExitCode
import zio.URIO
import zio.ZEnv
import zio.ZIO
import module3.zioConcurrency
import module3.di
import module3.zioDS
import zio.Has
import zio.ZLayer
import zio.clock
import java.util.concurrent.TimeUnit
import zio.console.{Console, putStr, putStrLn}
import zio.Task
import scala.language.postfixOps
import zio.duration.durationInt

object App {

  def main(args: Array[String]): Unit = {
    zio.Runtime.default.unsafeRun(???)
  }

}

object ZioApp extends zio.App{

  object logM {
    type Logging = Has[Logging.Service]

    object Logging {
      trait Service {
        def log[R, E, A](zio: ZIO[R, E, A]):  ZIO[clock.`package`.Clock with Console with R, E, A]
      }

      val live =
        ZLayer.succeed{
          new Service {
            override def log[R, E, A](zio: ZIO[R, E, A]): ZIO[clock.`package`.Clock with Console with R, E, A] =
              for {
                start <- clock.currentTime(TimeUnit.SECONDS)
                z <- zio
                finish <- clock.currentTime(TimeUnit.SECONDS)
                _ <- putStrLn(s"Running time: ${finish - start}")
              } yield z
          }
      }
    }

    def log[R, E, A](zio: ZIO[R, E, A]): ZIO[logM.Logging with clock.`package`.Clock with Console with R, E, A] = 
      ZIO.accessM(_.get.log(zio))
  }
  def run(args: List[String]): URIO[ZEnv, ExitCode] = {
    logM.log(ZIO.sleep(5 seconds))
    .provideSomeLayer[clock.`package`.Clock with Console](logM.Logging.live).exitCode
  }
}