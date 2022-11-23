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
} from 'react-native';

import PrimaryButton from '../../../components/UI/PrimaryButton';
import VGSFormView from '../../../NativeWrappers/VGSFormView.ios';

import VGSCollectFormView from '../../../NativeWrappers/VGSCollectFormView';
import {TapGestureHandler, State} from 'react-native-gesture-handler';

const VGSCollectManager = NativeModules.VGSCollectManager;

function CollectCardDataScreen() {
  const [stateDescription, setStateDescription] = useState('');
  useEffect(() => {
    VGSCollectManager.showKeyboardOnCardNumber();

    const myModuleEvt = new NativeEventEmitter(NativeModules.VGSCollectManager);
    myModuleEvt.addListener('stateDidChange', data =>
      setStateDescription(data.state),
    );
  }, [setStateDescription]);

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
        Alert.alert('Form is valid!', 'Send data..');
      }
      console.log('IsFormValid');
      console.log(data);
    });
  }

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
              <PrimaryButton buttonStyle={styles.button} icon="camera">
                Scan card.io
              </PrimaryButton>
            </View>
            <Text numberOfLines={0} style={styles.consoleText}>
              {stateDescription}
            </Text>
          </View>
        </ScrollView>
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
  },
});
