//
//  VGSShowCardViewManagerBridge.m
//  AwesomeProject

#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>
#import <React/RCTConvert.h>
#import <React/RCTViewManager.h>

@interface RCT_EXTERN_MODULE(VGSShowManagerAdvanced, RCTViewManager)

RCT_EXTERN_METHOD(setupVGSShow: (NSDictionary*)configuration callback:(RCTResponseSenderBlock)callback);
RCT_EXTERN_METHOD(revealData:(NSDictionary*)payload callback:(RCTResponseSenderBlock)callback);
RCT_EXTERN_METHOD(
                  setupShowViewFromManager:(nonnull NSNumber *)node
                  configuration:(NSDictionary*)configuration
                  callback:(RCTResponseSenderBlock)callback)
RCT_EXTERN_METHOD(copyCardNumber);

@end

