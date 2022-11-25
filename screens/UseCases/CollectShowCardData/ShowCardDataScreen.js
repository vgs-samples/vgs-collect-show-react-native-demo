import React from 'react';
import {SafeAreaView, View, Text, ScrollView, StyleSheet} from 'react-native';

import PrimaryButton from '../../../components/UI/PrimaryButton';
import VGSShowCardView from '../../../NativeWrappers/ios/CollectViews/VGSShowCardView';

function ShowCardDataScreen() {
  return (
    <SafeAreaView>
      <ScrollView style={styles.scrollView}>
        <VGSShowCardView style={styles.showCardView} />
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
