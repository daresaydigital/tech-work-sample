import Foundation

enum MovieSorting: String, CaseIterable, Identifiable {
  case popularity
  case rating
  var id: String { self.rawValue }
}

