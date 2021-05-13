//
//  VGSShowManagerBridge.m
//  AwesomeProject
//

#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>
#import <React/RCTConvert.h>
#import <React/RCTViewManager.h>

@interface RCT_EXTERN_MODULE(VGSShowManager, RCTViewManager)

RCT_EXTERN_METHOD(revealData: (RCTResponseSenderBlock)callback);

@end

@interface RCT_EXTERN_MODULE(CardShow, RCTViewManager)

RCT_EXTERN_METHOD(resetShow);

@end
