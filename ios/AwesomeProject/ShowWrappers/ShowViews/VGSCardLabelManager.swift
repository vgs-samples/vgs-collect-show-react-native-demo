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

@objc(VGSShowCardViewManager)
class VGSShowCardViewManager: RCTViewManager {

  static let contentPath = "json.payment_card_expiration_date"

  @objc override static func requiresMainQueueSetup() -> Bool {
    return true
  }

  override func view() -> UIView! {
    let view = VGSShowCardView(frame: .zero)

    return view
  }
}

class VGSShowCardView: UIView {
  lazy var cardNumberLabel: VGSLabel = {
    let cardNumberLabel = VGSLabel()
    cardNumberLabel.translatesAutoresizingMaskIntoConstraints = false

    cardNumberLabel.heightAnchor.constraint(equalToConstant: 50).isActive = true
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

    // CardShow.shared.show.subscribe(cardNumberLabel)
    return cardNumberLabel
  }()

  lazy var expDateLabel: VGSLabel = {
    let expDateLabel = VGSLabel()
    expDateLabel.translatesAutoresizingMaskIntoConstraints = false

    expDateLabel.placeholder = "Revealed Expiration Date"
    expDateLabel.paddings = .init(top: 8, left: 8, bottom: 8, right: 8)
    expDateLabel.contentPath = VGSExpDateLabelManager.contentPath
    //CardShow.shared.show.subscribe(expDateLabel)
    expDateLabel.heightAnchor.constraint(equalToConstant: 50).isActive = true

    return expDateLabel
  }()

  lazy var stackView: UIStackView = {
    let stackView = UIStackView(frame: .zero)
    stackView.translatesAutoresizingMaskIntoConstraints = false

    stackView.axis = .vertical
    stackView.isLayoutMarginsRelativeArrangement = true
    stackView.layoutMargins = UIEdgeInsets(top: 16, left: 0, bottom: 16, right: 0)
    stackView.spacing = 16

    //stackView.heightAnchor.constraint(equalToConstant: 150).isActive = true

    return stackView
  }()

  override init(frame: CGRect) {
    super.init(frame: frame)

    setupUI()
  }

  required init?(coder: NSCoder) {
    fatalError("init(coder:) has not been implemented")
  }

  private func setupUI() {
    addSubview(stackView)
    stackView.leadingAnchor.constraint(equalTo: leadingAnchor).isActive = true
    stackView.trailingAnchor.constraint(equalTo: trailingAnchor).isActive = true
    stackView.topAnchor.constraint(equalTo: topAnchor).isActive = true
    stackView.bottomAnchor.constraint(equalTo: bottomAnchor).isActive = true

    stackView.addArrangedSubview(cardNumberLabel)
    stackView.addArrangedSubview(expDateLabel)
  }
}
