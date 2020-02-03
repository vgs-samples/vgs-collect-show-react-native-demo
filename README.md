## VGS Collect iOS SDK - React Native Demo

This examples shows how easily you can integrate <a href="https://github.com/verygoodsecurity/vgs-collect-ios">VGS Collect iOS SDK</a> 
into your React Native application and secure sensitive data with us.

## How to run it?

### Requirements

- Installed latest <a href="https://apps.apple.com/us/app/xcode/id497799835?mt=12" target="_blank">Xcode</a>
- Installed <a href="https://guides.cocoapods.org/using/getting-started.html#installation" target="_blank">CocoaPods</a>
- Organization with <a href="https://www.verygoodsecurity.com/">VGS</a>


#### Step 1

Go to your <a href="https://dashboard.verygoodsecurity.com/" target="_blank">VGS organization</a> and establish <a href="https://www.verygoodsecurity.com/docs/getting-started/quick-integration#securing-inbound-connection" target="_blank">Inbound connection</a>. 

#### Step 2

Clone the application repository.

#### Step 3

Install pods.

Open Terminal and change working directory to `ios` folder that is inside `react-native-vgscollect-ios-demo` folder:

`$ cd ~/react-native-vgscollect-ios-demo`

Install pods:

`pod install`


#### Step 4

In `react-native-vgscollect-ios-demo` folder find and open `AwesomeProject.xcworkspace` file.
In the app go to `ViewController.swift` file, find the line:

`let vaultId = "vaultId"`

and replace `vaultId` with your organization
 <a href="https://www.verygoodsecurity.com/docs/terminology/nomenclature#vault" target="_blank">vault id</a>. 
 
### Step 5 

Run the application and submit the form. 
Then go to the Logs tab on <a href="http://dashboard.verygoodsecurity.com" target="_blank">Dashboard</a>, find request and secure a payload. 
Instruction for this step you can find <a href="https://www.verygoodsecurity.com/docs/getting-started/quick-integration#securing-inbound-connection" target="_blank">here</a>.

### Useful links

- <a href="https://www.verygoodsecurity.com/docs/vgs-collect/ios-sdk/index" target="_blank">Documentation</a> 
- <a href="https://github.com/verygoodsecurity/vgs-collect-ios" target="_blank">Repo</a> 
- <a href="http://cocoapods.org/pods/VGSCollectSDK" target="_blank">CocoaPods</a> 




In Terminal Navigate to iOS folder.
Run 'pod install'.
Open `AwesomeProject.xcworkspace` in Xcode.
Run the app.

