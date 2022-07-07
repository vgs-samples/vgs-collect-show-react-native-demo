//
//  VGSCardLabelManager.swift
//  AwesomeProject
//

import Foundation
import VGSShowSDK

@objc(VGSCardLabelManager)
class VGSCardLabelManager: RCTViewManager {

  static let contentPath = "json.payment_card_number"

  @objc override static func requiresMainQueueSetup() -> Bool {
    return true
  }

  override func view() -> UIView! {
    let cardNumberLabel = VGSLabel()
    cardNumberLabel.placeholder = "Revealed Card Number"
    cardNumberLabel.paddings = .init(top: 8, left: 8, bottom: 8, right: 8)
    cardNumberLabel.contentPath = VGSCardLabelManager.contentPath
    
    /// Transfor revealed card number format
    let cardNumberPattern = "(\\d{4})(\\d{4})(\\d{4})(\\d{4})"
    let template = "$1 $2 $3 $4"
    // Use force try! here for sample.
    let regex = try! NSRegularExpression(pattern: cardNumberPattern, options: [])
    // Add transformation regex and template to your label.
    cardNumberLabel.addTransformationRegex(regex, template: template)
    
    CardShow.shared.show.subscribe(cardNumberLabel)
    return cardNumberLabel
  }
}

@objc(VGSExpDateLabelManager)
class VGSExpDateLabelManager: RCTViewManager {

  static let contentPath = "json.payment_card_expiration_date"

  @objc override static func requiresMainQueueSetup() -> Bool {
    return true
  }

  override func view() -> UIView! {
    let expDateLabel = VGSLabel()
    expDateLabel.placeholder = "Revealed Expiration Date"
    expDateLabel.paddings = .init(top: 8, left: 8, bottom: 8, right: 8)
    expDateLabel.contentPath = VGSExpDateLabelManager.contentPath
    CardShow.shared.show.subscribe(expDateLabel)
    return expDateLabel
  }
}

