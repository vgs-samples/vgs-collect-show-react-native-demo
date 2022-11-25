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

@objc(VGSShowManagerAdvanced)
class VGSShowManagerAdvanced: RCTViewManager {

  var vgsShow: VGSShow?
  var vgsShowCardView: VGSShowCardView?

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

//    vgsShow.subscribedLabels.forEach { (label) in
//      label.delegate = self
//    }
  }

  @objc
  override static func requiresMainQueueSetup() -> Bool {
    return true
  }

  @objc
  func revealData(_ callback: @escaping RCTResponseSenderBlock) {
      // Send data to your Vault
      self.vgsShow?.request(path: "/post", method: .post, payload: SharedConfig.shared.payload, completion: { result in
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

  @objc
  func setupVGSShow(_ configuration: [String: Any], callback: @escaping RCTResponseSenderBlock) {
    DispatchQueue.main.async { [weak self] in

      guard let vaultId = configuration["vaultId"] as? String,
            let environment = configuration["environment"] as? String else {
        callback([["status" : "setup_failed"]])
        assertionFailure("Cannot config VGSShow!")
        return
      }
      self?.vgsShow = VGSShow(id: vaultId, environment: environment)

      callback([["status" : "setup_success"]])
    }
  }

  @objc func setupShowViewFromManager(_ node: NSNumber, configuration: [String: Any], callback: @escaping RCTResponseSenderBlock) {

    DispatchQueue.main.async {[weak self] in
      let showView = self?.bridge.uiManager.view(
        forReactTag: node
      ) as! VGSShowCardView
      //component.update(value: count)

      guard let vgsShow = self?.vgsShow else {
        callback([["status" : "failed"]])
        return
      }

//      guard let cardNumberFieldName = configuration["cardNumberFieldName"] as? String,
//            let expDateFieldName = configuration["expDateFieldName"] as? String else {
//        callback([["status" : "failed"]])
//        return
//      }

      vgsShow.subscribe(showView.cardNumberLabel)
      vgsShow.subscribe(showView.expDateLabel)
      self?.vgsShowCardView = showView

      callback([])
    }
  }

  @objc func copyCardNumber() {
    DispatchQueue.main.async {[weak self] in
      self?.vgsShowCardView?.cardNumberLabel.copyTextToClipboard()
    }
  }
}

// MARK: - VGSLabelDelegate

extension VGSShowManagerAdvanced: VGSLabelDelegate {

  func labelTextDidChange(_ label: VGSLabel) {
    label.borderColor = .green
  }

  func labelRevealDataDidFail(_ label: VGSLabel, error: VGSShowError) {
    label.borderColor = .red
  }
}

