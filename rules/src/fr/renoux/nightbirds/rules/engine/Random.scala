package fr.renoux.nightbirds.rules.engine

class Random(val seed : Long = scala.util.Random.nextInt()) {
  private val wrapped = new scala.util.Random(seed)

  def nextInt(upperLimit: Int) = wrapped.nextInt(upperLimit)

  def nextBoolean = wrapped.nextBoolean

  def nextBoolean(probability: Double) = wrapped.nextDouble() <= probability

  def pick[T](source: Seq[T]): T = source(nextInt(source.length))

  def pick[T](source: Iterable[T]): T = pick(source.toSeq)

  /** Pick in a random order from the source element. If not enough elements, returns as much as possible. */
  def pick[T](source: Iterable[T], count: Int): Seq[T] = shuffle(source).take(count)

  def shuffle[T](source: Iterable[T]) = wrapped.shuffle(source).toSeq

}