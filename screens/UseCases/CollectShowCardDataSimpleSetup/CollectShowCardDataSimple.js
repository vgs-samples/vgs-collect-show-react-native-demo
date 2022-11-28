import React from 'react';
import {SafeAreaView, View, Text, ScrollView, StyleSheet} from 'react-native';

import PrimaryButton from '../../../components/UI/PrimaryButton';
import VGSFormView from '../../../NativeWrappers/VGSFormView.ios';

function CollectCustomCardDataScreen() {
  return (
    <SafeAreaView>
      <ScrollView style={styles.scrollView}>
        <VGSFormView />
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
      </ScrollView>
    </SafeAreaView>
  );
}

export default CollectCustomCardDataScreen;

const styles = StyleSheet.create({
  scrollView: {
    height: '100%',
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
