//
//  VGSManager.swift
//  AwesomeProject
//
//  Created by Dima on 28.01.2020.
//  Copyright © 2020 Facebook. All rights reserved.


import Foundation
import VGSCollectSDK

@objc(VGSManager)
class VGSManager: NSObject {
  
  let view = VGSTextField()
  
  @objc
  static func requiresMainQueueSetup() -> Bool {
    return true
  }
  
  @objc(presentCardIO)
  func presentCardIO() {
    DispatchQueue.main.async {
      guard let viewController = UIApplication.shared.windows.first!.rootViewController else {
        print("NO ROOT VC")
        return
      }
      print("WILL present CARD IO")
      let scanVC = VGSCardIOScanController()
      scanVC.presentCardScanner(on: viewController, animated: true) {
        print("did present CARD IO")
      }
    }
  }

}
