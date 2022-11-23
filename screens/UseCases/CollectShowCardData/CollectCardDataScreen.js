import React, {useEffect} from 'react';
import {
  SafeAreaView,
  View,
  Text,
  ScrollView,
  StyleSheet,
  NativeModules,
} from 'react-native';

import PrimaryButton from '../../../components/UI/PrimaryButton';
import VGSFormView from '../../../NativeWrappers/VGSFormView.ios';

import VGSCollectFormView from '../../../NativeWrappers/VGSCollectFormView';
import {TapGestureHandler, State} from 'react-native-gesture-handler';

const VGSCollectManager = NativeModules.VGSCollectManager;

function CollectCardDataScreen() {
  useEffect(() => {
    VGSCollectManager.showKeyboardOnCardNumber();
  }, []);

  function hideKeyboard() {
    VGSCollectManager.hideKeyboard();
  }

  const onSingleTap = event => {
    if (event.nativeEvent.state === State.ACTIVE) {
      hideKeyboard();
    }
  };

  return (
    <TapGestureHandler onHandlerStateChange={onSingleTap}>
      <SafeAreaView>
        <ScrollView style={styles.scrollView}>
          <VGSCollectFormView
            pointerEvent="none"
            style={styles.collectFormView}
          />
          <View>
            <View style={styles.buttons}>
              <PrimaryButton buttonStyle={styles.button}>Submit</PrimaryButton>
              <View style={styles.spacerView}></View>
              <PrimaryButton buttonStyle={styles.button} icon="camera">
                card.io
              </PrimaryButton>
            </View>
            <Text numberOfLines={0} style={styles.consoleText}>
              Collect Custom Card Data
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
