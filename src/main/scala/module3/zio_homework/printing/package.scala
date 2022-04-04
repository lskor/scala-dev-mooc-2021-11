package module3.zio_homework

import module3.zioConcurrency.printEffectRunningTime
import zio.clock.Clock
import zio.console.Console
import zio.{Has, ULayer, ZIO, ZLayer}

package object printing {
	type Printing = Has[Printing.Service]

	object Printing {
		trait Service {
			def ptrRunningTime[R, E, A](zio: ZIO[R, E, A]): ZIO[Console with Clock with R, E, A]
		}

		val live: ULayer[Printing] = ZLayer.succeed( new Service {
			override def ptrRunningTime[R, E, A](zio: ZIO[R, E, A]): ZIO[Console with Clock with R, E, A] =
				printEffectRunningTime(zio)
		})

		def ptrRunningTime[R, E, A](zio: ZIO[R, E, A]): ZIO[Printing with Console with Clock with R, E, A] =
			ZIO.accessM(_.get.ptrRunningTime(zio))
	}
}