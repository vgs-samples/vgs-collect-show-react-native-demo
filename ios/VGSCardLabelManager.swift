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
    cardNumberLabel.placeholder = "Card Number"
    cardNumberLabel.paddings = .init(top: 8, left: 8, bottom: 8, right: 8)
    cardNumberLabel.contentPath = VGSCardLabelManager.contentPath
    CardShow.shared.show.subscribe(cardNumberLabel)
    return cardNumberLabel
  }
}

