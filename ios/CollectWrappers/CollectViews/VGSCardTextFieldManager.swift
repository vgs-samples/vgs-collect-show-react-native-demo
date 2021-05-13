//
//  VGSTextFieldManager.swift
//  AwesomeProject
//
//  Created by Dima on 02.02.2020.
//  Copyright © 2020 Facebook. All rights reserved.
//

import Foundation
import VGSCollectSDK

@objc(VGSCardTextFieldManager)
class VGSCardTextFieldManager: RCTViewManager {
  
  static let fieldName = "card_number"
  
  @objc override static func requiresMainQueueSetup() -> Bool {
    return true
  }
  
  override func view() -> UIView! {
    /// Set configuration and field type
    let config = VGSConfiguration(collector: CardCollector.shared.collector, fieldName: Self.fieldName)
    config.type = .cardNumber

    let cardTextfield = VGSCardTextField()
    cardTextfield.placeholder = "Card Number"
    cardTextfield.padding = .init(top: 8, left: 8, bottom: 8, right: 8)
    cardTextfield.configuration = config
    return cardTextfield
  }
}


@objc(VGSExpDateTextFieldManager)
class VGSExpDateTextFieldManager: RCTViewManager {
  
  static let fieldName = "card_expirationDate"
  
  @objc override static func requiresMainQueueSetup() -> Bool {
    return true
  }
  
  override func view() -> UIView! {
    /// Set configuration and field type
    let config = VGSConfiguration(collector: CardCollector.shared.collector, fieldName: Self.fieldName)
    config.type = .expDate

    let expDateTextfield = VGSExpDateTextField()
    expDateTextfield.placeholder = "Expiration Date"
    expDateTextfield.padding = .init(top: 8, left: 8, bottom: 8, right: 8)
    expDateTextfield.configuration = config
    expDateTextfield.monthPickerFormat = .longSymbols
    return expDateTextfield
  }
}
