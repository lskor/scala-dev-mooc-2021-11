package module3.zio_homework

import java.nio.file.Paths

import module3.zio_homework.config.AppConfig
import pureconfig.ConfigSource
import zio.Task
import zio.duration.durationInt
import zio.random.nextBoolean
import zio.test.Assertion.{equalTo, hasSize}
import zio.test.TestAspect.timeout
import zio.test._
import zio.test.environment.{TestClock, TestConsole, TestEnvironment, TestRandom}
import pureconfig.generic.auto._

import scala.language.postfixOps

object ZIOHomework extends DefaultRunnableSpec {

	private lazy val win = testM("You are win") {
		for{
			_ <- TestRandom.feedInts(5)
			_ <- TestConsole.feedLines("5")
			_ <- guessProgram
			value <- TestConsole.output
		} yield {
			assert(value)(hasSize(equalTo(4))) &&
			assert(value(3))(equalTo("You are win!\n"))
		}
	}

	private lazy val wrongNumber = testM("Wrong number") {
		for{
			_ <- TestRandom.feedInts(3)
			_ <- TestConsole.feedLines("1")
			_ <- guessProgram
			value <- TestConsole.output
		} yield {
			assert(value)(hasSize(equalTo(4))) &&
			assert(value(3))(equalTo("O, no! You are wrong. My number is 3!\n"))
		}
	}

	private lazy val notNumber = testM("Not number") {
		for{
			_ <- TestRandom.feedInts(2)
			_ <- TestConsole.feedLines("Hi!")
			_ <- guessProgram
			value <- TestConsole.output
		} yield {
			assert(value)(hasSize(equalTo(4))) &&
			assert(value(3))(equalTo("O, no! You are wrong. My number is 2!\n"))
		}
	}

	private lazy val iterateWhile = testM("Iterate while") {
		for{
			_ <- TestRandom.feedBooleans(false, false, true)
			_ <- doWhile(nextBoolean)
		} yield assertCompletes
	}

	private def loadConfig(path: String): Task[AppConfig] =
		Task.effect(ConfigSource.file(Paths.get(path)).loadOrThrow[AppConfig])

	private lazy val loadDefaultConfig = testM("Load default config") {
		loadConfigOrDefault(loadConfig("app.conf")).map(conf => assert(conf)(equalTo(defaultConfig)))
	}

	private lazy val failureLoadConfig = testM("Failure load config") {
		val load = loadConfig("src/test/resource/failureApp.conf")
		loadConfigOrDefault(load).map(conf => assert(conf)(equalTo(defaultConfig)))
	}

	private lazy val successfulLoadConfig = testM("Successful load config") {
		val expectedConf = AppConfig("portal", "http://www.portal.com")
		val load = loadConfig("src/test/resource/successApp.conf")
		loadConfigOrDefault(load).map(conf => assert(conf)(equalTo(expectedConf)))
	}

	private lazy val effectsApp = testM("Effects application") {
		for{
			_ <- TestRandom.feedInts(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
			fiber <- app.fork
			_ <- TestClock.adjust(10 seconds)
			_ <- fiber.join
			result <- TestConsole.output
		} yield {
			assert(result)(hasSize(equalTo(1))) &&
			assert(result(0))(equalTo("sum = 45\n"))
		}
	} @@ timeout(15.seconds)

	private lazy val effectsAppSpeedUp = testM("Effects application speedup") {
		for{
			_ <- TestRandom.feedInts(9, 1, 2, 3, 4, 5, 6, 7, 8, 9)
			fiber <- appSpeedUp.fork
			_ <- TestClock.adjust(1 seconds)
			_ <- fiber.join
			result <- TestConsole.output
		} yield {
			assert(result)(hasSize(equalTo(1))) &&
			assert(result(0))(equalTo("sum = 54\n"))
		}
	} @@ timeout(15.seconds)

	private lazy val guessProgramSuite = suite("Guess program suite")(win, wrongNumber, notNumber)
	private lazy val doWhileSuite = suite("Do while suite")(iterateWhile)
	private lazy val loadConfigSuite = suite("Load config suite")(loadDefaultConfig, failureLoadConfig, successfulLoadConfig)
	private lazy val effectsAppSuite = suite("Effects application suite")(effectsApp, effectsAppSpeedUp)

	override def spec: ZSpec[TestEnvironment, Any] =
		suite("ZIO Homework")(guessProgramSuite, doWhileSuite, loadConfigSuite, effectsAppSuite)
}