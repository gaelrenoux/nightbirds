package fr.renoux.nightbirds.utils

object Logger {

  val level : LogLevel = Warn

  def debug(msg : => String) = if (level <= Debug) println(msg)
  def info(msg : => String) = if (level <= Info) println(msg)
  def warn(msg : => String) = if (level <= Warn) println(msg)
  def error(msg : => String) = if (level <= Error) println(msg)
  def fatal(msg : => String) = if (level <= Fatal) println(msg)
}

sealed abstract class LogLevel(private val value: Int) extends Ordered[LogLevel] {
  def compare(that : LogLevel) = this.value.compare(that.value) 
}
object Debug extends LogLevel(0)
object Info extends LogLevel(1)
object Warn extends LogLevel(2)
object Error extends LogLevel(3)
object Fatal extends LogLevel(4)
