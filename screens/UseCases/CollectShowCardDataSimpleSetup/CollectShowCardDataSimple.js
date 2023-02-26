import React from 'react';
import {SafeAreaView, View, Text, ScrollView, StyleSheet} from 'react-native';

import CollectShowFormView from './CollectShowFormView';

function CollectCustomCardDataScreen() {
  return (
    <SafeAreaView>
      <ScrollView style={styles.scrollView}>
        <CollectShowFormView />
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  scrollView: {
    height: '100%',
  },
});

export default CollectCustomCardDataScreen;