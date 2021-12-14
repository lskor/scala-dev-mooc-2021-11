package module1

import module1.list._
import org.junit.Assert.assertEquals
import org.junit.Test

class ListTest
{
	@Test
	def apply {

		assertEquals(Nil, List())

		val one = ::(1, Nil)
		assertEquals(one, List(1))

		val two = ::(1, ::(2, Nil))
		assertEquals(two, List(1, 2))

		val three = ::(1, ::(2, ::(3, Nil)))
		assertEquals(three, List(1, 2, 3))

		val four = ::("1", ::("2", ::("3", ::("4", Nil))))
		assertEquals(four, List("1", "2", "3", "4"))
	}
}