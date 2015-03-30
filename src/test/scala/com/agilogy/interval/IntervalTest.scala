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

}
