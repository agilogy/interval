package com.agilogy.interval

sealed trait IntervalBound[T]

case class OpenBound[T](value: T) extends IntervalBound[T]

case class ClosedBound[T](value: T) extends IntervalBound[T]

case class Interval[T: Ordering](from: Option[IntervalBound[T]], to: Option[IntervalBound[T]]) {

  lazy val ordering = implicitly[Ordering[T]]

  def includes(value: T): Boolean = {
    val notBeforeFrom = from match {
      case None => true
      case Some(OpenBound(f)) => ordering.lt(f, value)
      case Some(ClosedBound(f)) => ordering.lteq(f, value)
    }
    lazy val notAfterTo = to match {
      case None => true
      case Some(OpenBound(t)) => ordering.gt(t, value)
      case Some(ClosedBound(t)) => ordering.gteq(t, value)
    }
    notBeforeFrom && notAfterTo
  }
}
