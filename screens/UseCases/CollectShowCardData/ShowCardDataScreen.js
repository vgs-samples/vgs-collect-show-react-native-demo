import React, {useEffect, useState} from 'react';
import {
  SafeAreaView,
  View,
  Text,
  ScrollView,
  StyleSheet,
  NativeModules,
  findNodeHandle,
} from 'react-native';

import PrimaryButton from '../../../components/UI/PrimaryButton';
import VGSShowCardView from '../../../NativeWrappers/ios/CollectViews/VGSShowCardView';

import {constants} from '../../../constants/constants';

const VGSShowManager = NativeModules.VGSShowManagerAdvanced;

function ShowCardDataScreen() {
  let showCardViewRef;

  useEffect(() => {
    VGSShowManager.setupVGSShow(
      {
        vaultId: constants.vaultId,
        environment: constants.environment,
      },
      setupResult => {
        console.log(setupResult);
      },
    );

    return () => {};
  }, []);

  useEffect(() => {
    const showCardViewNode = findNodeHandle(showCardViewRef);
    if (showCardViewNode) {
      console.log('found show view node!!!');
      VGSShowManager.setupShowViewFromManager(
        showCardViewNode,
        {
          cardNumberFieldName: 'card_number',
          expDateFieldName: 'card_expirationrDate',
        },
        result => {
          console.log(result);
          console.log('success setup show view!');
        },
      );
    }
    return () => {};
  }, [showCardViewRef]);

  return (
    <SafeAreaView>
      <ScrollView style={styles.scrollView}>
        <VGSShowCardView
          style={styles.showCardView}
          ref={e => (showCardViewRef = e)}
        />
        <View style={styles.buttons}>
          <PrimaryButton buttonStyle={styles.button}>Reveal</PrimaryButton>
          <View style={styles.spacerView}></View>
          <PrimaryButton buttonStyle={styles.button} icon="camera">
            Copy Card
          </PrimaryButton>
        </View>
        <Text numberOfLines={0} style={styles.consoleText}>
          No data to reveal! Collect some data first!
        </Text>
      </ScrollView>
    </SafeAreaView>
  );
}

export default ShowCardDataScreen;

const styles = StyleSheet.create({
  scrollView: {
    height: '100%',
  },
  showCardView: {
    height: 150,
    marginHorizontal: 24,
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
