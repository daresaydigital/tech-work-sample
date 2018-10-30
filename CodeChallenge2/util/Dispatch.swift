//
//  Dispatch.swift
//  Stingy
//
//  Created by Vincent Berihuete on 12/29/16.
//

import Foundation

public struct Dispatch {
    
    public typealias Block = () -> ()
}

// MARK: - Main / Async

extension Dispatch {
    
    ///Runs a block in the main queue
    public static func main(delay: Double = 0.0, block: @escaping Block) {
        async(DispatchQueue.main, delay: delay, block: block)
    }
    
    ///Runs an async thread with a default queue of type serial and using the identifier defined
    ///with the constant StingyQueue.SERIAL_ASYNC_QUEUE
    public static func async(_ queue: DispatchQueue = DispatchQueue(label: "vbqueue", attributes: []), delay: Double = 0.0, block: @escaping Block) {
        let after = DispatchTime.now() + Double(Int64(delay * Double(NSEC_PER_SEC))) / Double(NSEC_PER_SEC)
        queue.asyncAfter(deadline: after, execute: block)
    }
}

