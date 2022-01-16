package futures

import HomeworksUtils.TaskSyntax

import scala.List
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

object task_futures_sequence {

  /**
   * В данном задании Вам предлагается реализовать функцию fullSequence,
   * похожую на Future.sequence, но в отличии от нее,
   * возвращающую все успешные и не успешные результаты.
   * Возвращаемое тип функции - кортеж из двух списков,
   * в левом хранятся результаты успешных выполнений,
   * в правово результаты неуспешных выполнений.
   * Не допускается использование методов объекта Await и мутабельных переменных var
   */
  /**
   * @param futures список асинхронных задач
   * @return асинхронную задачу с кортежом из двух списков
   */
  def fullSequence[A](futures: List[Future[A]])
                     (implicit ex: ExecutionContext): Future[(List[A], List[Throwable])] = {

    futures.foldLeft(Future.successful(List[A](), List[Throwable]())) {
      (result, future) => result.flatMap {
        case (sList, tList) =>
          future.map(a => (sList :+ a, tList))
                .recover { case throwable: Throwable => (sList, tList :+ throwable) }
      }
    }
  }
}
