package com.agilogy.interval

import org.scalatest.FlatSpec

class IntervalTest extends FlatSpec {

  behavior of "Infinite Interval"

  it should "include any value" in {
    val i = Interval[Int](None, None)
    assert(i.includes(Integer.MIN_VALUE) === true)
    assert(i.includes(Integer.MAX_VALUE) === true)
    assert(i.includes(0) === true)
  }

  behavior of "Open ended interval"

  it should "include any value in the interval, but not the bounds" in {
    val i = Interval[Float](Some(OpenBound(2.3f)), Some(OpenBound(2.4f)))
    assert(i.includes(2.30001f) === true)
    assert(i.includes(2.39999f) === true)
    assert(i.includes(2.3f) === false)
    assert(i.includes(2.4f) === false)
  }

  behavior of "Close ended interval"

  it should "include any value in the interval and its bounds" in {
    val i = Interval[Float](Some(ClosedBound(2.3f)), Some(ClosedBound(2.4f)))
    assert(i.includes(2.30001f) === true)
    assert(i.includes(2.39999f) === true)
    assert(i.includes(2.3f) === true)
    assert(i.includes(2.4f) === true)
  }
}
