//
//  VGSShowManager.swift
//  AwesomeProject
//

import Foundation
import VGSShowSDK
import VGSCollectSDK

@objc(CardShow)
class CardShow: RCTViewManager {
  static let shared = CardShow()
  var show = VGSShow(id: vaultId, environment: environment.rawValue)

  @objc
  func resetShow() {
    show = VGSShow(id: vaultId, environment: environment.rawValue)
  }

  @objc
  override static func requiresMainQueueSetup() -> Bool {
    return true
  }
}

class SharedConfig {
  static let shared = SharedConfig()

  // Insert you <vauilt id here>
  let vaultId = "vaultId"
  // Set environment, `sandbox` or `live`
  let environment = Environment.sandbox

  var payload: [String:Any] = [:]

  private init() {}
}

@objc(VGSShowManager)
class VGSShowManager: RCTViewManager {

  let vgsShow = CardShow.shared.show

  override init() {
    super.init()

    vgsShow.subscribedLabels.forEach { (label) in
      label.delegate = self
    }
  }

  @objc
  override static func requiresMainQueueSetup() -> Bool {
    return true
  }

  @objc
  func revealData(_ callback: @escaping RCTResponseSenderBlock) {
    DispatchQueue.main.async { [weak self] in

      // Send data to your Vault
      self?.vgsShow.request(path: "/post", method: .post, payload: SharedConfig.shared.payload, completion: { result in
        switch result {
        case .success(let code):
          callback([code])
        case .failure(let code, let error):
          let text = error?.localizedDescription ?? "Error"
          print("error! code: \(code))")
          callback([text])
          break
        }
      })
    }
  }
}

// MARK: - VGSLabelDelegate

extension VGSShowManager: VGSLabelDelegate {

  func labelTextDidChange(_ label: VGSLabel) {
    label.borderColor = .green
  }

  func labelRevealDataDidFail(_ label: VGSLabel, error: VGSShowError) {
    label.borderColor = .red
  }
}
