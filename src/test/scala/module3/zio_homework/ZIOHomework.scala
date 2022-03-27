package module3.zio_homework

import zio.test.Assertion.{equalTo, hasSize}
import zio.test._
import zio.test.environment.{TestConsole, TestEnvironment, TestRandom}

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

	private lazy val guessProgramSuite = suite("Guess program suite") (win, wrongNumber, notNumber)

	override def spec: ZSpec[TestEnvironment, Any] =
		suite("ZIO Homework")(guessProgramSuite)
}