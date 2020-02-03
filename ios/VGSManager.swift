//
//  VGSManager.swift
//  AwesomeProject
//
//  Created by Dima on 28.01.2020.
//  Copyright © 2020 Facebook. All rights reserved.


import Foundation
import VGSCollectSDK

class CollectManager {
  
  static let shared = CollectManager()
  let collector = VGSCollect(id: "tntgjtukyom", environment: .sandbox)
  
  init() {
    // observe textfield states
    collector.observeStates = { forms in
      
      forms.forEach { textfield in
        print(textfield.state.description)
      }
    }
  }
}


@objc(VGSManager)
class VGSManager: RCTViewManager {
  
  let vgsCollector = CollectManager.shared.collector
  let scanVC = VGSCardIOScanController()

  override init() {
    super.init()

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
      self?.scanVC.delegate = self
      self?.scanVC.presentCardScanner(on: viewController, animated: true, completion: nil)
    }
  }
  
  @objc
  func submitData(_ callback: RCTResponseSenderBlock) {
    // send data
    DispatchQueue.main.async { [weak self] in
      self?.vgsCollector.submit(path: "post", extraData: nil, completion: { (json, error) in
          if error == nil, let json = json {
            print(json)
          } else {
              print("Error: \(String(describing: error?.localizedDescription))")
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
