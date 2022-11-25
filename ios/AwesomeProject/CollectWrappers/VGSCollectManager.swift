//
//  VGSManager.swift
//  AwesomeProject
//



import Foundation
import VGSCollectSDK
import UIKit

extension UIView {
    func findViews<T: UIView>(subclassOf: T.Type) -> [T] {
        return recursiveSubviews.compactMap { $0 as? T }
    }

    var recursiveSubviews: [UIView] {
        return subviews + subviews.flatMap { $0.recursiveSubviews }
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
class VGSCollectManager: RCTEventEmitter {
  
  var vgsCollector: VGSCollect?
  let scanVC = VGSCardIOScanController()

  func provideExpDateTextField() -> RCTViewManager {
    return VGSCardTextFieldManager()
  }

  override init() {
    super.init()
    
    // Enable loggers only for debug!
    VGSCollectLogger.shared.configuration.level = .info
    VGSCollectLogger.shared.configuration.isNetworkDebugEnabled = true
    // *You can stop all VGS Show loggers in app:
    // VGSCollectLogger.shared.disableAllLoggers()
    
    // Reset shared collector - this will create new instance of `VGSCollect`
    // CardCollector.shared.resetCollector()

    vgsCollector?.textFields.forEach { (textField) in
      textField.delegate = self
    }

    // Observing text fields. The call back return all textfields with updated states. You also can you VGSTextFieldDelegate
    vgsCollector?.observeStates = { [weak self] form in

      var text = ""
      form.forEach({ textField in
        text.append(textField.state.description)
        text.append("\n")
      })

      self?.sendEvent(withName: "stateDidChange" , body: ["state": text])
    }
  }

  @objc
  func setupVGSCollect(_ configuration: [String: Any], callback: @escaping RCTResponseSenderBlock) {
    DispatchQueue.main.async { [weak self] in

      guard let vaultId = configuration["vaultId"] as? String,
            let environment = configuration["environment"] as? String else {
        callback([["status" : "setup_failed"]])
        assertionFailure("Cannot config VGSCollect!")
        return
      }
      self?.vgsCollector = VGSCollect(id: vaultId, environment: environment)


      self?.vgsCollector?.textFields.forEach { (textField) in
        textField.delegate = self
      }

      // Observing text fields. The call back return all textfields with updated states. You also can you VGSTextFieldDelegate
      self?.vgsCollector?.observeStates = { [weak self] form in

        var text = ""
        form.forEach({ textField in
          text.append(textField.state.description)
          text.append("\n")
        })

        self?.sendEvent(withName: "stateDidChange" , body: ["state": text])
      }
      callback([["status" : "setup_success"]])

//      guard let viewController = UIApplication.shared.windows.first!.rootViewController else {
//        return
//      }
//
//      let subViews = viewController.view.subviews
//
//      let fields = viewController.view.findViews(subclassOf: VGSCardTextField.self)
//      for view in fields {
//        print("Subview: \(view)")
//      }
//
//      let component = self?.bridge.uiManager.view(             // 3
//        forReactTag: 12                                     // 4
//      ) as! VGSCardTextField                                       // 5
//      //component.update(value: count)
    }
  }

  @objc func setupCollectViewFromManager(_ node: NSNumber, configuration: [String: Any], callback: @escaping RCTResponseSenderBlock) {

    DispatchQueue.main.async {[weak self] in
      let collectView = self?.bridge.uiManager.view(
        forReactTag: node
      ) as! VGSCollectCardView
      //component.update(value: count)

      guard let collect = self?.vgsCollector else {
        callback([["status" : "failed"]])
        return
      }

      guard let cardNumberFieldName = configuration["cardNumberFieldName"] as? String,
            let expDateFieldName = configuration["expDateFieldName"] as? String else {
        callback([["status" : "failed"]])
        return
      }

      // Set card number configuration.
      let cardNumberConfig = VGSConfiguration(collector: collect, fieldName: cardNumberFieldName)
      cardNumberConfig.type = .cardNumber

      /// Set exp date configuration and field type.
      let expDateConfig = VGSConfiguration(collector: collect, fieldName: expDateFieldName)
      expDateConfig.type = .expDate

      collectView.cardNumberField.configuration = cardNumberConfig
      collectView.expDateField.configuration = expDateConfig
      callback([])
    }
  }

  override func supportedEvents() -> [String]! {
    return ["stateDidChange", "userDidCancelScan", "userDidFinishScan"]
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

  @objc(showKeyboardOnCardNumber)
  func showKeyboardOnCardNumber() {
    DispatchQueue.main.async { [weak self] in
      guard let field = self?.vgsCollector?.textFields.first(where: {$0.configuration?.type == .cardNumber}) else {
        return
      }

      field.becomeFirstResponder()
    }
  }

  @objc(unregisterAllTextFields)
  func unregisterAllTextFields() {
    DispatchQueue.main.async { [weak self] in
      self?.vgsCollector?.unsubscribeAllTextFields()
    }
  }

  @objc(hideKeyboard)
  func hideKeyboard() {
    DispatchQueue.main.async { [weak self] in
      guard let field = self?.vgsCollector?.textFields.first(where: {$0.isFocused}) else {
        return
      }

      field.resignFirstResponder()
    }
  }

  @objc
  func isFormValid(_ callback: @escaping RCTResponseSenderBlock) {
    print("Call isFormValid")
    DispatchQueue.main.async { [weak self] in
      guard let strongSelf = self, let collect = strongSelf.vgsCollector else {return}
      let invalidFields = collect.textFields.compactMap{$0.state.isValid}.filter({$0 == false})
      callback([["isValid": invalidFields.isEmpty]])
    }
  }

  @objc var onStateChange: RCTBubblingEventBlock?
  
  @objc
  func submitData(_ callback: @escaping RCTResponseSenderBlock) {
    // add extra data to submit
    var extraData = [String: Any]()
    extraData["extraData"] = "Some extra value"
    
    //All UI changes should be done on main thread.
    DispatchQueue.main.async { [weak self] in
      self?.vgsCollector?.textFields.forEach { (textfield) in
        /// Check textField state before submit
        if !textfield.state.isValid {
          /// if state is not valid, set border color as red
          textfield.borderColor = .red
        }
        /// hide keyboard(if field was active)
        textfield.resignFirstResponder()
      }
      
      // Send data to your Vault
      self?.vgsCollector?.sendData(path: "/post", extraData: extraData) { (response) in
        
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

extension VGSCollectManager: VGSCardIOScanControllerDelegate {
  
  func userDidCancelScan() {
    scanVC.dismissCardScanner(animated: true, completion: { [weak self] in
      self?.sendEvent(withName: "userDidCancelScan" , body: [:])
    })
  }
  
  func userDidFinishScan() {
    scanVC.dismissCardScanner(animated: true, completion: {[weak self] in
      self?.sendEvent(withName: "userDidFinishScan" , body: [:])
    })
  }
  
  func textFieldForScannedData(type: CradIODataType) -> VGSTextField? {
    switch type {
    case .cardNumber:
      return vgsCollector?.getTextField(fieldName: VGSCardTextFieldManager.fieldName)
    case .expirationDate:
      return vgsCollector?.getTextField(fieldName: VGSExpDateTextFieldManager.fieldName)
    default:
      return nil
    }
  }
}
