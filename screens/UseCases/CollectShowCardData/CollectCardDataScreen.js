import React, {useEffect, useState, useRef} from 'react';
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
  UIManager,
  findNodeHandle,
} from 'react-native';

import PrimaryButton from '../../../components/UI/PrimaryButton';
import VGSFormView from '../../../NativeWrappers/VGSFormView.ios';
import CardTextField from '../../../NativeWrappers/ios/CollectViews/CardTextField';

import VGSCollectCardView from '../../../NativeWrappers/ios/CollectViews/VGSCollectCardView';
import VGSCollectFormView from '../../../NativeWrappers/VGSCollectFormView';
import {TapGestureHandler, State} from 'react-native-gesture-handler';
import LoadingOverlay from '../../../components/UI/LoadingOverlay';
import {constants} from '../../../constants/constants';

const VGSCollectManager = NativeModules.VGSCollectManager;

function CollectCardDataScreen() {
  const [stateDescription, setStateDescription] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    console.log('useEffect!');
    const myModuleEvt = new NativeEventEmitter(NativeModules.VGSCollectManager);
    VGSCollectManager.setupVGSCollect(
      {
        vaultId: constants.vaultId,
        environment: constants.environment,
      },
      setupResult => {
        console.log(setupResult);
        // Subscribe to native events.

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
    // Display keyboard on screen start.
    // VGSCollectManager.showKeyboardOnCardNumber();

    // Unsubscribe from native events,unregister all textFields.
    const unsubscribe = () => {
      myModuleEvt.removeAllListeners('stateDidChange');
      myModuleEvt.removeAllListeners('userDidCancelScan');
      myModuleEvt.removeAllListeners('userDidFinishScan');

      VGSCollectManager.unregisterAllTextFields();
    };

    // Remove all listeners, because there have to be no listeners on unmounted screen
    return () => unsubscribe();
  }, []);

  let collectViewRef;
  useEffect(() => {
    console.log('useRef');
    const collectViewNode = findNodeHandle(collectViewRef);
    if (collectViewNode) {
      console.log('found card number node!!!');
      VGSCollectManager.setupCollectViewFromManager(
        collectViewNode,
        {
          cardNumberFieldName: 'card_number',
          expDateFieldName: 'card_expirationDate',
        },
        result => {
          console.log(result);
          console.log('success show keyboard!');
          VGSCollectManager.showKeyboardOnCardNumber();
        },
      );
    }
  }, [collectViewRef]);

  function hideKeyboard() {
    VGSCollectManager.hideKeyboard();
  }

  const onSingleTap = event => {
    if (event.nativeEvent.state === State.ACTIVE) {
      console.log('tap');
      hideKeyboard();
    }
  };

  function submitData() {
    VGSCollectManager.isFormValid(data => {
      if (!data.isValid) {
        Alert.alert('Form is invalid!', 'Check your inputs!');
      } else {
        setIsSubmitting(true);
        VGSCollectManager.submitData(value => {
          console.log('VALUE:');
          console.log(value);
          setIsSubmitting(false);
          setStateDescription(value);
        });
      }
      console.log('IsFormValid');
      console.log(data);
    });
  }

  function scanPressHandler() {
    VGSCollectManager.presentCardIO();
  }

  //   if (isSubmitting) {
  //     return <LoadingOverlay></LoadingOverlay>;
  //   }

  return (
    <TapGestureHandler onHandlerStateChange={onSingleTap}>
      <SafeAreaView>
        <ScrollView style={styles.scrollView}>
          <KeyboardAvoidingView
            style={{flex: 1}}
            behavior={Platform.OS === 'ios' ? 'padding' : 'height'}>
            {/* <CardTextField style={{height: 50}} ref={e => (cardRef = e)} /> */}
            {/* <VGSCollectFormView style={styles.collectFormView} /> */}
            <VGSCollectCardView
              style={{
                // backgroundColor: 'orange',
                height: 150,
                // width: '100%',
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
                  Scan card.io
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
    </TapGestureHandler>
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
