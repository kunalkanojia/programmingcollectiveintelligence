def root(x: Double): Option[Double] = {
  if (x > 0) Some(math.sqrt(x)) else None
}


case class Asset(name: String, sedol: Option[String])



val stuff = (42, "fish")

val x = future { someExpensiveComputation() }
val y = future { someOtherExpensiveComputation() }
val z = for (a <- x; b <- y) yield a*b
for (c <- z) println("Result: " + c)
println("Meanwhile, the main thread goes on!")