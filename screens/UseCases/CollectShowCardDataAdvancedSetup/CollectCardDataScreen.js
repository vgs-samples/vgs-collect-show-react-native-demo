import React, {useEffect, useState, useContext} from 'react';
import {
  SafeAreaView,
  View,
  Text,
  ScrollView,
  StyleSheet,
  NativeModules,
  NativeEventEmitter,
  Alert,
  KeyboardAvoidingView,
  Platform,
  findNodeHandle,
} from 'react-native';

import {TapGestureHandler, State} from 'react-native-gesture-handler';

import PrimaryButton from '../../../components/UI/PrimaryButton';

// 1. Import native view wrapper.
import VGSCollectCardView from '../../../NativeWrappers/CollectViews/VGSCollectCardView';

import LoadingOverlay from '../../../components/UI/LoadingOverlay';
import {constants} from '../../../constants/constants';

import {CollectShowCardDataContext} from '../../../state/CollectShowCardDataContext';

// 2. Import native collect module.
const VGSCollectManager = NativeModules.VGSCollectAdvancedManager;

function CollectCardDataScreen() {
  const [stateDescription, setStateDescription] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);
  const collectShowCardDataContext = useContext(CollectShowCardDataContext);

  useEffect(() => {
    // 3. Create native events emitter. (optional)
    const myModuleEvt = new NativeEventEmitter(
      NativeModules.VGSCollectAdvancedManager,
    );

    // 4. Setup VGSCollect with useEffect([]) once.
    VGSCollectManager.setupVGSCollect(
      {
        vaultId: constants.vaultId,
        environment: constants.environment,
      },
      setupResult => {
        console.log(setupResult);

        //5. Subscribe to native events. (optinal)
        myModuleEvt.addListener('stateDidChange', data =>
          setStateDescription(data.state),
        );
        myModuleEvt.addListener('userDidCancelScan', data =>
          Alert.alert('User did cancel scan!', 'Handle cancel scan'),
        );
        myModuleEvt.addListener('userDidFinishScan', data =>
          Alert.alert('User did finish scan!', 'Handle finish scan'),
        );
      },
    );

    // 6. Unsubscribe from native events,unregister all textFields (optional).
    const unsubscribe = () => {
      myModuleEvt.removeAllListeners('stateDidChange');
      myModuleEvt.removeAllListeners('userDidCancelScan');
      myModuleEvt.removeAllListeners('userDidFinishScan');

      VGSCollectManager.unregisterAllTextFields();
    };

    // Remove all listeners, because there have to be no listeners on unmounted screen
    return () => unsubscribe();
  }, []);

  // 7. Create ref for native view (cannot use useRef hook).
  let collectViewRef;

  useEffect(() => {
    console.log('useRef');

    // 8. Find react node with native view ref.
    const collectViewNode = findNodeHandle(collectViewRef);
    if (collectViewNode) {
      console.log('found card number node!!!');

      // 9. Bind VGSCollect with native view in native code.
      VGSCollectManager.setupCollectViewFromManager(
        collectViewNode,
        {
          cardNumberFieldName: 'cardNumber',
          expDateFieldName: 'expDate',
        },
        result => {
          console.log(result);
          console.log('success show keyboard!');

          // 10. Show keyboard when binding is finished.
          VGSCollectManager.showKeyboardOnCardNumber();
        },
      );
    }
  }, [collectViewRef]);

  function submitData() {
    VGSCollectManager.isFormValid(data => {
      // 12. Submit data is isValid.
      if (!data.isValid) {
        Alert.alert('Form is invalid!', 'Check your inputs!');
      } else {
        setIsSubmitting(true);
        VGSCollectManager.submitData(value => {
          console.log('VALUE:');
          console.log(value);
          setIsSubmitting(false);
          setStateDescription(JSON.stringify(value, null, 2));

          const payloadToReveal = {
            payment_card_number: value.json.cardNumber,
            payment_card_expiration_date: value.json.expDate,
          };

          collectShowCardDataContext.updatePayload(payloadToReveal);
          console.log('context payload!');
          console.log(collectShowCardDataContext.payload);
        });
      }
      console.log('IsFormValid');
      console.log(data);
    });
  }

  function scanPressHandler() {
    VGSCollectManager.presentCardScanner();
  }

  return (
      <SafeAreaView>
        <ScrollView style={styles.scrollView}>
          <KeyboardAvoidingView
            style={{flex: 1}}
            behavior={Platform.OS === 'ios' ? 'padding' : 'height'}>
            <VGSCollectCardView
              style={{
                height: 150,
                marginHorizontal: 24,
              }}
              ref={e => (collectViewRef = e)}></VGSCollectCardView>
            <View>
              <View style={styles.buttons}>
                <PrimaryButton onPress={submitData} buttonStyle={styles.button}>
                  Submit
                </PrimaryButton>
                <View style={styles.spacerView}></View>
                <PrimaryButton
                  onPress={scanPressHandler}
                  buttonStyle={styles.button}
                  icon="camera">
                  Scan
                </PrimaryButton>
              </View>

              <Text numberOfLines={0} style={styles.consoleText}>
                {stateDescription}
              </Text>
            </View>
          </KeyboardAvoidingView>
        </ScrollView>
        {isSubmitting && <LoadingOverlay></LoadingOverlay>}
      </SafeAreaView>
  );
}

export default CollectCardDataScreen;

const styles = StyleSheet.create({
  scrollView: {
    height: '100%',
  },
  collectFormView: {
    marginHorizontal: 24,
    marginVertical: 24,
  },
  item: {
    height: 80,
    backgroundColor: 'orange',
    marginHorizontal: 24,
    marginVertical: 8,
  },
  buttons: {
    marginHorizontal: 24,
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  spacerView: {
    width: 16,
  },
  button: {
    flex: 1,
  },
  consoleText: {
    marginHorizontal: 24,
    marginTop: 16,
    marginBottom: 24,
    flex: 1,
    fontSize: 16,
    fontWeight: '800',
  },
});
