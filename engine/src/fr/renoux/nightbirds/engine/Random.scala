package fr.renoux.nightbirds.engine

class Random {
  private val wrapped = new scala.util.Random

  def nextInt(upperLimit: Int) = wrapped.nextInt(upperLimit)

  def pick[T](source: Seq[T]): T = source(nextInt(source.length))

  /** Pick in a random order from the source element */
  def pick[T](source: Iterable[T], count: Int): Seq[T] = shuffle(source).take(count)

  def shuffle[T](source: Iterable[T]) = wrapped.shuffle(source).toSeq

}