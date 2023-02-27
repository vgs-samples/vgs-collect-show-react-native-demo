
#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>
#import <React/RCTConvert.h>
#import <React/RCTViewManager.h>

@interface RCT_EXTERN_MODULE(VGSCollectManager, RCTViewManager)

RCT_EXTERN_METHOD(presentCardIO);
RCT_EXTERN_METHOD(submitData: (RCTResponseSenderBlock)callback);

@end

@interface RCT_EXTERN_MODULE(CardCollector, RCTViewManager)

RCT_EXTERN_METHOD(resetCollector);

@end
