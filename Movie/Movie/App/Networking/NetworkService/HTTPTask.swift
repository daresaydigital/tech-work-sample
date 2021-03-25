//
//  HTTPTask.swift
//  
//
//  Created by Andrian Sergheev on 2019-02-25.
//  Copyright © 2019 Andrian Sergheev. All rights reserved.
//

public enum HTTPTask {

/*
	If bodyParameters and urlParameters are nil,
	encoding should be .none.
*/

	case request
	case requestParameters(bodyParameters: Parameters?,
		bodyEncoding: ParameterEncoding,
		urlParameters: Parameters?)

	case requestParametersAndHeaders(bodyParameters: Parameters?,
		bodyEncoding: ParameterEncoding,
		urlParameters: Parameters?,
		additionalHeaders: HTTPHeaders?)
}
