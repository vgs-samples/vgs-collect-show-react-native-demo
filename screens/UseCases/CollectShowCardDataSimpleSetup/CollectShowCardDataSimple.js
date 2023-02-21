import React from 'react';
import {SafeAreaView, View, Text, ScrollView, StyleSheet} from 'react-native';

import VGSFormView from '../../../NativeWrappers/VGSFormView';

function CollectCustomCardDataScreen() {
  return (
    <SafeAreaView>
      <ScrollView style={styles.scrollView}>
        <VGSFormView />
      </ScrollView>
    </SafeAreaView>
  );
}

export default CollectCustomCardDataScreen;

const styles = StyleSheet.create({
  scrollView: {
    height: '100%',
  },
});
