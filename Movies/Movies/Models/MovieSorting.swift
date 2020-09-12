import Foundation

enum MovieSorting {
  case popularity
  case rating(voteCountGreaterThan: Int)
}

