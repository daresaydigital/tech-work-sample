//
//  DateConverter.swift
//  Weather
//
//  Created by Christian  Huang on 07/11/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import Foundation


class DateConverter {
    
    static func timeIntervalToDayString(_ time: TimeInterval) -> String{
        let date = Date(timeIntervalSince1970: time)
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "EEEE"
        return dateFormatter.string(from: date)
    }
    static func timeIntervalToDayShortString(_ time: TimeInterval) -> String{
        let date = Date(timeIntervalSince1970: time)
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "EE"
        return dateFormatter.string(from: date)
    }
    
    static func timeIntervalToHourString(_ time: TimeInterval) -> String{
        let date = Date(timeIntervalSince1970: time)
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "HH"
        return dateFormatter.string(from: date)
    }
    
    static func timeIntervalToHourMinuteString(_ time: TimeInterval) -> String{
        let date = Date(timeIntervalSince1970: time)
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "HH':'mm"
        return dateFormatter.string(from: date)
    }
    
    static func stringToDate(dateString: String, timezone: String = "CEST") -> Date? {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy'-'MM'-'dd'T'HH':'mm':'ssZZZ"
        dateFormatter.timeZone = TimeZone(abbreviation: timezone)
        let date = dateFormatter.date(from: dateString)
        return date
    }
    
    static func dateToDateString(date: Date, timezone: String = "CEST") -> String {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "E. dd.MM"
        dateFormatter.timeZone = TimeZone(abbreviation: timezone)
        
        return dateFormatter.string(from:date)
    }
    
    static func dateToTimeString(date: Date, timezone: String = "CEST") -> String {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "hh.mm"
        dateFormatter.timeZone = TimeZone(abbreviation: timezone)
        
        return dateFormatter.string(from:date)
    }
}
