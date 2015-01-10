package fr.renoux.nightbirds.engine

class Random {
  private val wrapped = new scala.util.Random

  def nextInt(upperLimit: Int) = wrapped.nextInt(upperLimit)

  def pick[T](source: Seq[T]): T = source(nextInt(source.length))

  def pick[T](source: Iterable[T], count: Int): Iterable[T] =
    wrapped.shuffle(source).take(count)

}