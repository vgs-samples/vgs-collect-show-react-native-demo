//
//  VGSManager.swift
//  AwesomeProject
//
//  Created by Dima on 28.01.2020.
//  Copyright © 2020 Facebook. All rights reserved.


import Foundation
import VGSCollectSDK

// Insert you <vauilt id here>
let vaultId = "vaultId"
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

    // Reset shared collector - this will create new instance of `VGSCollect`
     CardCollector.shared.resetCollector()

    vgsCollector.observeStates = { textFields in
      textFields.forEach { textField in
        print(textField.state.description)
      }
    }
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
    extraData["cardHolderName"] = "Joe Business"
    
    // send data
    DispatchQueue.main.async { [weak self] in
      self?.vgsCollector.submit(path: "post", extraData: extraData, completion: { (json, error) in
        if error == nil, let json = json {
            print(json)
            let jsonText = (json.compactMap({ (key, value) -> String in
                return "\(key)=\(value)"
            }) as Array).joined(separator: ";\n")
            
            callback([jsonText])
          } else {
            let errorText = "Error: \(String(describing: error?.localizedDescription))"
            print(errorText)
            callback([errorText])
          }
      })
    }
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
