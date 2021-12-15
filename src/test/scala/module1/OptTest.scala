package module1

import module1.opt._
import org.junit.Assert.assertEquals
import org.junit.rules.ExpectedException
import org.junit.{Rule, Test}

import scala.annotation.meta.getter

class OptTest{

	@(Rule @getter)
	val thrown = ExpectedException.none

	@Test
	def apply {

		assertEquals(Option.Some("string"), Option("string"))
		assertEquals(Option.Some(true), Option(true))
		assertEquals(Option.Some(List()), Option(List()))
		assertEquals(Option.Some(Seq(1, 2)), Option(Seq(1, 2)))

		assertEquals(Option.None, Option(null))
	}

	@Test
	def unsafePrintIfAny {

		Option("Hello!").unsafePrintIfAny
	}

	@Test
	def exceptionUnsafePrintIfAny {

		thrown.expect(classOf[Exception])
		thrown.expectMessage("Get on empty option")

		Option(null).unsafePrintIfAny
	}

	@Test
	def printIfAny {

		Option("Hello!").printIfAny
		Option(null).printIfAny
	}

	@Test
	def zip {

		assertEquals(Some(("one","two")), Some("one").zip(Some("two")))
		assertEquals(None, Some("one").zip(None))
		assertEquals(None, None.zip(Some("two")))
		assertEquals(None, None.zip(None))
	}

	@Test
	def filter {

		val predicate: String => Boolean = t => t.contains("one")

		val someActual = Some("one person").filter(predicate)
		assertEquals(Some("one person"), someActual)

		val noneActual = Some("two person").filter(predicate)
		assertEquals(None, noneActual)

		assertEquals(None, Some("").filter(predicate))
		assertEquals(None, None.filter(predicate))
	}
}