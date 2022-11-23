import React, {useEffect, useState} from 'react';
import {
  SafeAreaView,
  View,
  Text,
  ScrollView,
  StyleSheet,
  NativeModules,
  NativeEventEmitter,
  Alert,
  Button,
  ActivityIndicator,
} from 'react-native';

import PrimaryButton from '../../../components/UI/PrimaryButton';
import VGSFormView from '../../../NativeWrappers/VGSFormView.ios';

import VGSCollectFormView from '../../../NativeWrappers/VGSCollectFormView';
import {TapGestureHandler, State} from 'react-native-gesture-handler';
import LoadingOverlay from '../../../components/UI/LoadingOverlay';

const VGSCollectManager = NativeModules.VGSCollectManager;

function CollectCardDataScreen() {
  const [stateDescription, setStateDescription] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    console.log('useEffect!');
    VGSCollectManager.showKeyboardOnCardNumber();

    const myModuleEvt = new NativeEventEmitter(NativeModules.VGSCollectManager);
    myModuleEvt.addListener('stateDidChange', data =>
      setStateDescription(data.state),
    );
    myModuleEvt.addListener('userDidCancelScan', data =>
      Alert.alert('User did cancel scan!', 'Handle cancel scan'),
    );
    myModuleEvt.addListener('userDidFinishScan', data =>
      Alert.alert('User did finish scan!', 'Handle finish scan'),
    );

    const unsubscribe = () => {
      myModuleEvt.removeAllListeners('stateDidChange');
      myModuleEvt.removeAllListeners('userDidCancelScan');
      myModuleEvt.removeAllListeners('userDidFinishScan');

      VGSCollectManager.unregisterAllTextFields();
    };

    // Remove all listeners, because there have to be no listeners on unmounted screen
    return () => unsubscribe();
  }, []);

  function hideKeyboard() {
    VGSCollectManager.hideKeyboard();
  }

  const onSingleTap = event => {
    if (event.nativeEvent.state === State.ACTIVE) {
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
          <VGSCollectFormView style={styles.collectFormView} />
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
