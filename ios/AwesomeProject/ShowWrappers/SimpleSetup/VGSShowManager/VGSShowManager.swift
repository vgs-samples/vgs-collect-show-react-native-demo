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
  var show = VGSShow(id: SharedConfig.shared.vaultId, environment: SharedConfig.shared.environment.rawValue)

  @objc
  func resetShow() {
    show = VGSShow.init(id: SharedConfig.shared.vaultId, environment: SharedConfig.shared.environment.rawValue)
  }

  @objc
  override static func requiresMainQueueSetup() -> Bool {
    return true
  }
}

@objc(VGSShowManager)
class VGSShowManager: RCTViewManager {

  let vgsShow = CardShow.shared.show

  override init() {
    super.init()

    // Enable loggers only for debug!

    // Setup VGS Show loggers:
    // Show warnings and errors.
    VGSLogger.shared.configuration.level = .info
    // Show network session for reveal requests.
    VGSLogger.shared.configuration.isNetworkDebugEnabled = true
    // *You can stop all VGS Show loggers in app:
    // VGSLogger.shared.disableAllLoggers()
    
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
      // Send data to your Vault
      self.vgsShow.request(path: "/post", method: .post, payload: SharedConfig.shared.payload, completion: { result in
                  var text = ""
          switch result {
          case .success(let code):
            text = "Success data reveal, code: \(code)"
            print(text)
          case .failure(let code, let error):
            text = error?.localizedDescription ?? "Reveal Error, code: \(code)"
            print("error! code: \(code))")
          }
          callback([text])
          return
    })
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
