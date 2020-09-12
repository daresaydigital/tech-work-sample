import Foundation

enum MovieSorting: String, CaseIterable, Identifiable {
  case rating
  case popularity
  var id: String { self.rawValue }
}

