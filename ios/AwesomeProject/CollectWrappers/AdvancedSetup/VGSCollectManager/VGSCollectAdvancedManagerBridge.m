//
//  VGSCollectAdvancedManagerBridge.m
//  AwesomeProject
//

#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>
#import <React/RCTConvert.h>
#import <React/RCTViewManager.h>

@interface RCT_EXTERN_MODULE(VGSCollectAdvancedManager, RCTViewManager)

RCT_EXTERN_METHOD(presentCardScanner);
RCT_EXTERN_METHOD(showKeyboardOnCardNumber);
RCT_EXTERN_METHOD(hideKeyboard);
RCT_EXTERN_METHOD(unregisterAllTextFields);
RCT_EXTERN_METHOD(isFormValid: (RCTResponseSenderBlock)callback);
RCT_EXTERN_METHOD(submitData: (RCTResponseSenderBlock)callback);
RCT_EXTERN_METHOD(setupVGSCollect: (NSDictionary*)configuration callback:(RCTResponseSenderBlock)callback);
RCT_EXTERN_METHOD(
                  setupCollectViewFromManager:(nonnull NSNumber *)node
                  configuration:(NSDictionary*)configuration
                  callback:(RCTResponseSenderBlock)callback)

@end
