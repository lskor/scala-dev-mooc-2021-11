package module3

import module3.zioConcurrency.printEffectRunningTime
import zio.duration.durationInt
import scala.language.postfixOps

import module3.zio_homework.config.AppConfig
import zio._
import zio.clock.Clock
import zio.console.{Console, putStrLn}
import zio.random.Random

package object zio_homework {

  type InteractionEnv = Random with Console

  def getConsole: ZIO[Console, Nothing, Console.Service] = ZIO.environment[Console].map(_.get)
  def getRandom: ZIO[Random, Nothing, Random.Service] = ZIO.environment[Random].map(_.get)
  def getClock: ZIO[Clock, Nothing, Clock.Service] = ZIO.environment[Clock].map(_.get)

  /**
   * 1.
   * Используя сервисы Random и Console, напишите консольную ZIO программу которая будет предлагать пользователю угадать число от 1 до 3
   * и печатать в консоль угадал или нет. Подумайте, на какие наиболее простые эффекты её можно декомпозировать.
   */

  def printLine(console: Console.Service, line: String): Task[Unit] = console.putStrLn(line)
  def randomFrom1To3(random: Random.Service): Task[Int] = random.nextIntBetween(1, 3)
  def readLine(console: Console.Service): Task[String] = console.getStrLn
  def readInt(line: String): Task[Int] = ZIO.effect(line.toInt)

  lazy val guessProgram: ZIO[InteractionEnv, Throwable, Unit] = for {
      console <- getConsole
      random <- getRandom
      thinkingNumber <- randomFrom1To3(random)
      _ <- printLine(console,"Let's play!")
      _ <- printLine(console,"I'm thinking of a number between 1 and 3. Guess it!")
      _ <- printLine(console, s"So, this number is...")
      _ <- readLine(console)
            .flatMap(readInt)
            .withFilter(_ == thinkingNumber)
            .*> (printLine(console, "You are win!")) // ToDo: возможно, есть более подходящий метод
            .orElse(printLine(console, s"O, no! You are wrong. My number is $thinkingNumber!"))
    }
    yield ()

  /**
   * 2. реализовать функцию doWhile (общего назначения), которая будет выполнять эффект до тех пор, пока его значение в условии не даст true
   * 
   */

    def doWhile[R, E](effect: ZIO[R, E, Boolean]): ZIO[R, E, Unit]=
        effect.flatMap(cond => if(cond) ZIO.succeed() else doWhile(effect))

  /**
   * 3. Реализовать метод, который безопасно прочитает конфиг из файла, а в случае ошибки вернет дефолтный конфиг
   * и выведет его в консоль
   * Используйте эффект "load" из пакета config
   */

  lazy val defaultConfig = AppConfig("default", "http://www.default.com")

  def loadConfigOrDefault(load: Task[AppConfig]): ZIO[Any, Nothing, AppConfig] =
    load.orElse(Task.succeed(defaultConfig))

  /**
   * 4. Следуйте инструкциям ниже для написания 2-х ZIO программ,
   * обратите внимание на сигнатуры эффектов, которые будут у вас получаться,
   * на изменение этих сигнатур
   */


  /**
   * 4.1 Создайте эффект, который будет возвращать случайным образом выбранное число от 0 до 10 спустя 1 секунду
   * Используйте сервис zio Random
   */

  lazy val randomFrom0To10: ZIO[Random, Nothing, Int] = getRandom.flatMap(_.nextIntBetween(0, 10))
  lazy val sleep1Seconds: URIO[Clock, Unit] = getClock.flatMap(_.sleep(1 seconds))

  lazy val eff: ZIO[Random with Clock, Nothing, Int] = sleep1Seconds *> randomFrom0To10

  /**
   * 4.2 Создайте коллекцию из 10 выше описанных эффектов (eff)
   */
  lazy val effects = List.fill(10)(eff)

  /**
   * 4.3 Напишите программу которая вычислит сумму элементов коллекци "effects",
   * напечатает ее в консоль и вернет результат, а также залогирует затраченное время на выполнение,
   * можно использовать ф-цию printEffectRunningTime, которую мы разработали на занятиях
   */

  lazy val app: ZIO[Console with Random with Clock, Nothing, Unit] =
    ZIO.foldLeft(effects)(0)((sum, nextEffect) => nextEffect.map(_ + sum))
       .flatMap(sum => putStrLn(s"sum = $sum"))


  lazy val printEffectRunningTimeApp = printEffectRunningTime(app)
  /**
   * 4.4 Усовершенствуйте программу 4.3 так, чтобы минимизировать время ее выполнения
   */

  lazy val appSpeedUp =
    ZIO.collectAllPar(effects)
       .flatMap(effs => ZIO.effect(effs.sum))
       .flatMap(effs => putStrLn(s"sum = $effs"))

  lazy val printEffectRunningTimeAppSpeedUp =
    printEffectRunningTime(appSpeedUp)

  /**
   * 5. Оформите ф-цию printEffectRunningTime разработанную на занятиях в отдельный сервис, так чтобы ее
   * молжно было использовать аналогично zio.console.putStrLn например
   */


   /**
     * 6.
     * Воспользуйтесь написанным сервисом, чтобы созадть эффект, который будет логировать время выполнения прогаммы из пункта 4.3
     *
     * 
     */

  lazy val appWithTimeLogg = ???

  /**
    * 
    * Подготовьте его к запуску и затем запустите воспользовавшись ZioHomeWorkApp
    */

  lazy val runApp = ???
  
}
