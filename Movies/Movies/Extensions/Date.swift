import Foundation

extension Date {
  var year: String {
    let format = DateFormatter()
    format.dateFormat = "yyyy"
    return format.string(from: self)
  }
}

