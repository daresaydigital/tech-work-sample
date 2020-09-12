import Foundation

extension Date {
  /// The year as a `String`.
  var year: String {
    let format = DateFormatter()
    format.dateFormat = "yyyy"
    return format.string(from: self)
  }
}
