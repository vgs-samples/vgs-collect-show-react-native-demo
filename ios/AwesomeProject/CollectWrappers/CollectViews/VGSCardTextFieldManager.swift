//
//  VGSTextFieldManager.swift
//  AwesomeProject
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

@objc(VGSCollectCardViewManager)
class VGSCollectCardViewManager: RCTViewManager {

//  static let fieldName = "card_expirationDate"
//
//  static let fieldName = "card_expirationDate"

  @objc override static func requiresMainQueueSetup() -> Bool {
    return true
  }

  lazy var cardNumberField: VGSCardTextField = {
    let cardTextfield = VGSCardTextField()

    cardTextfield.translatesAutoresizingMaskIntoConstraints = false
    cardTextfield.heightAnchor.constraint(equalToConstant: 50).isActive = true
    cardTextfield.placeholder = "Card Number"
    cardTextfield.padding = .init(top: 8, left: 8, bottom: 8, right: 8)


    return cardTextfield
  }()

  lazy var expDateField: VGSExpDateTextField = {
    let expDateTextfield = VGSExpDateTextField()
    expDateTextfield.translatesAutoresizingMaskIntoConstraints = false

    expDateTextfield.placeholder = "Expiration Date 2"
    expDateTextfield.heightAnchor.constraint(equalToConstant: 50).isActive = true
    expDateTextfield.padding = .init(top: 8, left: 8, bottom: 8, right: 8)
    expDateTextfield.monthPickerFormat = .longSymbols

    return expDateTextfield
  }()

  lazy var stackView: UIStackView = {
    let stackView = UIStackView(frame: .zero)
    stackView.translatesAutoresizingMaskIntoConstraints = false

    stackView.axis = .vertical
    stackView.isLayoutMarginsRelativeArrangement = true
    stackView.layoutMargins = UIEdgeInsets(top: 16, left: 0, bottom: 16, right: 0)
    stackView.spacing = 16

    return stackView
  }()

  override func view() -> UIView! {
    stackView.addArrangedSubview(cardNumberField)
    stackView.addArrangedSubview(expDateField)

    return stackView
  }

  func setupWithVGSCollect(_ vgsCollect: VGSCollect) {

    let cardNumberConfig = VGSConfiguration(collector: vgsCollect, fieldName: "")
    cardNumberConfig.type = .cardNumber

    cardNumberField.configuration = cardNumberConfig

    let expDateConfig = VGSConfiguration(collector: CardCollector.shared.collector, fieldName: "")
    expDateConfig.type = .expDate

    expDateField.configuration = expDateConfig
  }
}
