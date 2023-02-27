//
//  VGSShowCardViewManager.swift
//  AwesomeProject

import Foundation

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
