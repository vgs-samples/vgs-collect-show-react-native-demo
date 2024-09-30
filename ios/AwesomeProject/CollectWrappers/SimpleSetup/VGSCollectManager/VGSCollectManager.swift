//
//  VGSManager.swift
//  AwesomeProject
//

import Foundation
import VGSCollectSDK
import UIKit

class SharedConfig {
  static let shared = SharedConfig()

  // Insert you <vauilt id here>
  let vaultId = "vaultId"
  // Set environment, `sandbox` or `live`
  let environment = Environment.sandbox
  // BlinkCard Scanner license key https://developer.microblink.com
  let blinkCardLicenseKey: String? = nil
  
  var payload: [String:Any] = [:]

  private init() {}
}

@objc(CardCollector)
class CardCollector: NSObject {
  static let shared = CardCollector()
  var collector = VGSCollect(id: SharedConfig.shared.vaultId, environment: SharedConfig.shared.environment)
  
  @objc
  func resetCollector() {
    collector = VGSCollect(id: SharedConfig.shared.vaultId, environment: SharedConfig.shared.environment)
  }
  
  @objc
  static func requiresMainQueueSetup() -> Bool {
    return true
  }
}

@objc(VGSCollectManager)
class VGSCollectManager: NSObject {

  let vgsCollector = CardCollector.shared.collector
  var scanVC: VGSBlinkCardController?
  
  override init() {
    super.init()

    // Enable loggers only for debug!
    VGSCollectLogger.shared.configuration.level = .info
    VGSCollectLogger.shared.configuration.isNetworkDebugEnabled = true
    // *You can stop all VGS Show loggers in app:
    // VGSCollectLogger.shared.disableAllLoggers()

    // Reset shared collector - this will create new instance of `VGSCollect`
    // CardCollector.shared.resetCollector()
    vgsCollector.textFields.forEach { (textField) in
      textField.delegate = self
    }
    activateCardScanner()
  }
  
  private func activateCardScanner() {
    // Init VGSBlinkCardController with BlinkCard license key https://developer.microblink.com
    guard let key = SharedConfig.shared.blinkCardLicenseKey else { return }
    self.scanVC = VGSBlinkCardController(licenseKey: key, delegate: self, onError: { errorCode in
      print("BlinkCard license error, code: \(errorCode)")
    })
  }

  @objc
  static func requiresMainQueueSetup() -> Bool {
    return true
  }

  @objc(presentCardScanner)
  func presentCardScanner() {
    DispatchQueue.main.async { [weak self] in
      guard let viewController = UIApplication.shared.windows.first!.rootViewController else {
        return
      }
      self?.scanVC?.presentCardScanner(on: viewController, animated: true, completion: nil)
    }
  }

  @objc
  func submitData(_ callback: @escaping RCTResponseSenderBlock) {
    // add extra data to submit
    var extraData = [String: Any]()
    extraData["extraData"] = "Some extra value"

    //All UI changes should be done on main thread.
    DispatchQueue.main.async { [weak self] in
      self?.vgsCollector.textFields.forEach { (textfield) in
        /// Check textField state before submit
        if !textfield.state.isValid {
          /// if state is not valid, set border color as red
          textfield.borderColor = .red
        }
        /// hide keyboard(if field was active)
        textfield.resignFirstResponder()
      }

      // Send data to your Vault
      self?.vgsCollector.sendData(path: "/post", extraData: extraData) { (response) in

        switch response {
          case .success(_, let data, _):
            var jsonText = ""
            if let data = data, let jsonData = try? JSONSerialization.jsonObject(with: data, options: []) as? [String: Any] {
              jsonText = (String(data: try! JSONSerialization.data(withJSONObject: jsonData["json"]!, options: .prettyPrinted), encoding: .utf8)!)

              // Map data for show.
              if let aliases = jsonData["json"] as? [String: Any],
                 let cardNumber = aliases["card_number"],
                 let expDate = aliases["card_expirationDate"] {

                let payload = [
                              "payment_card_number": cardNumber,
                              "payment_card_expiration_date": expDate
                ]

                SharedConfig.shared.payload = payload
              }

              }
              callback([jsonText])

              return
          case .failure(let code, _, _, let error):
            var errorText = ""
            switch code {
            case 400..<499:
              // Wrong request. This also can happend when your Routs not setup yet or your <vaultId> is wrong
              errorText = "Wrong Request Error: \(code)"
            case VGSErrorType.inputDataIsNotValid.rawValue:
              if let error = error as? VGSError {
                errorText = "Input data is not valid. Details:\n \(error)"
              }
            default:
              errorText = "Something went wrong. Code: \(code)"
            }
            print("Submit request error: \(code), \(String(describing: error))")
            callback([errorText])
            return
        }
      }
    }
  }
}

/// VGSTextFieldDelegate -handle VGSTextField changes
extension VGSCollectManager: VGSTextFieldDelegate {
  func vgsTextFieldDidBeginEditing(_ textField: VGSTextField) {
    /// Reset border color to default if the field was not valid on sendData(_:) request
    textField.borderColor = .gray
  }

  func vgsTextFieldDidChange(_ textField: VGSTextField) {
    print(textField.state.description)
  }
}

extension VGSCollectManager: VGSBlinkCardControllerDelegate{

  func userDidCancelScan() {
    scanVC?.dismissCardScanner(animated: true, completion: nil)
  }

  func userDidFinishScan() {
    scanVC?.dismissCardScanner(animated: true, completion: nil)
  }

  func textFieldForScannedData(type: VGSBlinkCardDataType) -> VGSTextField? {
    switch type {
    case .cardNumber:
      return vgsCollector.getTextField(fieldName: VGSCardTextFieldManager.fieldName)
    case .expirationDate:
      return vgsCollector.getTextField(fieldName: VGSExpDateTextFieldManager.fieldName)
    default:
      return nil
    }
  }
}
