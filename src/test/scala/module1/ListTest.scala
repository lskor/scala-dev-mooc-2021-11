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

	@Test
	def cons {

		assertEquals(List("a"), List().cons("a"))
		assertEquals(List("a", "b"), List("b").cons("a"))
		assertEquals(List("d", "a", "b", "c"), List("a", "b", "c").cons("d"))
	}

	@Test
	def mkString() {

		val comma = ", "
		assertEquals("", List().mkString(comma))
		assertEquals("1", List(1).mkString(comma))
		assertEquals("1, 2", List(1, 2).mkString(comma))
		assertEquals("1, 2, 3, 4, 5", List(1, 2, 3, 4, 5).mkString(comma))

		val list = List("id", "name", "document", "uuid")
		assertEquals("id|name|document|uuid", list.mkString("|"))
	}

	@Test
	def reverse {

		assertEquals(List(), List().reverse)
		assertEquals(List(1), List(1).reverse)
		assertEquals(List(13.5, 12.5), List(12.5, 13.5).reverse)
		assertEquals(List("a", "b", "c", "d", "e"), List("e", "d", "c", "b", "a").reverse)
	}
}