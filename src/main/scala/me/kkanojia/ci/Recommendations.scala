package me.kkanojia.ci

class Recommendations(db: ReviewDb) {

  private def reviewsBy(r: Reviewer) = {
    db.findAllReviews().filter {
      _.reviewer == r
    }
  }

  private[ci] def reviewedMovies(r1: Reviewer, r2: Reviewer): List[Movie] = {
    db.findAllReviews().filter(_.reviewer == r1).map(_.movie).intersect(db.findAllReviews().filter(_.reviewer == r2).map(_.movie))
  }

  def euclideanDistance(r1: Reviewer, r2: Reviewer): Double = {
    val bothReviewed = reviewedMovies(r1, r2)

    if (bothReviewed.isEmpty) return 0.0

    val reviewMap: Map[Movie, (Rate, Rate)] = db.findAllReviews().filter { r => bothReviewed.contains(r.movie) }
      .filter { r => r.reviewer == r1 || r.reviewer == r2}
      .groupBy{ _.movie}
      .map { case (k, v) => k -> (v.head.rate, v(1).rate)}

    val sumOfSquares = reviewMap.values.foldLeft(0.0) { (a, b) => a + math.pow(b._1.value - b._2.value, 2) }
    1 / ( 1 + sumOfSquares)
  }
}

object Main extends App {
  println(new Recommendations(InMemoryReviewDb).euclideanDistance(InMemoryReviewDb.lisa, InMemoryReviewDb.gene))
}
