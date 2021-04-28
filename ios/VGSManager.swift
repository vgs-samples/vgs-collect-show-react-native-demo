//
//  VGSManager.swift
//  AwesomeProject
//
//  Created by Dima on 28.01.2020.
//  Copyright © 2020 Facebook. All rights reserved.


import Foundation
import VGSCollectSDK

// Insert you <vauilt id here>
let vaultId = ""
// Set environment, `sandbox` or `live`
let environment = Environment.sandbox

@objc(CardCollector)
class CardCollector: RCTViewManager {
  static let shared = CardCollector()
  var collector = VGSCollect(id: vaultId, environment: environment)
  
  @objc
  func resetCollector() {
    collector = VGSCollect(id: vaultId, environment: environment)
  }
  
  @objc
  override static func requiresMainQueueSetup() -> Bool {
    return true
  }
}

@objc(VGSManager)
class VGSManager: RCTViewManager {
  
  let vgsCollector = CardCollector.shared.collector
  let scanVC = VGSCardIOScanController()

  override init() {
    super.init()
      
    /// Send debug logs in console
    VGSCollectLogger.shared.configuration.level = .info
    VGSCollectLogger.shared.configuration.isNetworkDebugEnabled = true
    
    // Reset shared collector - this will create new instance of `VGSCollect`
    //     CardCollector.shared.resetCollector()

    vgsCollector.textFields.forEach { (textField) in
      textField.delegate = self
    }
    
    /// Another option to track changes in VGSTextFields
//    vgsCollector.observeStates = { textFields in
//      textFields.forEach { textField in
//        print(textField.state.description)
//      }
//    }
  }
  
  @objc
  override static func requiresMainQueueSetup() -> Bool {
    return true
  }
  
  @objc(presentCardIO)
  func presentCardIO() {
    DispatchQueue.main.async { [weak self] in
      guard let viewController = UIApplication.shared.windows.first!.rootViewController else {
        return
      }
      // Set preferred camera position
//      self?.scanVC.preferredCameraPosition = .front
      self?.scanVC.delegate = self
      self?.scanVC.presentCardScanner(on: viewController, animated: true, completion: nil)
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
      self?.vgsCollector.sendData(path: "/post", extraData: extraData) { [weak self](response) in
        
        switch response {
          case .success(_, let data, _):
            var jsonText = ""
            if let data = data, let jsonData = try? JSONSerialization.jsonObject(with: data, options: []) as? [String: Any] {
              jsonText = (String(data: try! JSONSerialization.data(withJSONObject: jsonData["json"]!, options: .prettyPrinted), encoding: .utf8)!)

              // Map data for show.
              if let aliases = jsonData["json"] as? [String: Any],
                 let cardNumber = aliases["cardNumber"] {
//                let expDate = aliases["expDate"],
//                let cardHolderName = aliases["cardHolderName"] {

                let payload = [
//                  "payment_card_holder_name": cardHolderName,
                              "payment_card_number": cardNumber,
//                              "payment_card_expiration_date": expDate
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
extension VGSManager: VGSTextFieldDelegate {
  func vgsTextFieldDidBeginEditing(_ textField: VGSTextField) {
    /// Reset border color to default if the field was not valid on sendData(_:) request
    textField.borderColor = .gray
  }
  
  func vgsTextFieldDidChange(_ textField: VGSTextField) {
    print(textField.state.description)
  }
}

extension VGSManager: VGSCardIOScanControllerDelegate {
  
  func userDidCancelScan() {
    scanVC.dismissCardScanner(animated: true, completion: nil)
  }
  
  func userDidFinishScan() {
    scanVC.dismissCardScanner(animated: true, completion: nil)
  }
  
  func textFieldForScannedData(type: CradIODataType) -> VGSTextField? {
    switch type {
    case .cardNumber:
      return vgsCollector.getTextField(fieldName: VGSCardTextFieldManager.fieldName)
    case .cvc:
      return vgsCollector.getTextField(fieldName: VGSCVCTextFieldManager.fieldName)
    case .expirationDate:
      return vgsCollector.getTextField(fieldName: VGSExpDateTextFieldManager.fieldName)
    default:
      return nil
    }
  }
}
