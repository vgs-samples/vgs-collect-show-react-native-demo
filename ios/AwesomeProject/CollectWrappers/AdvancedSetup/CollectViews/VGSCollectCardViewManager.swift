//
//  VGSCollectCardViewManager.swift
//  AwesomeProject

import Foundation

@objc(VGSCollectCardViewManager)
class VGSCollectCardViewManager: RCTViewManager {

  @objc override static func requiresMainQueueSetup() -> Bool {
    return true
  }

  override func view() -> UIView! {
    return VGSCollectCardView(frame: .zero)
  }
}
