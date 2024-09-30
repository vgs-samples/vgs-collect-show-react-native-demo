import React, {useState} from 'react';
import {NativeModules} from 'react-native';
import CardTextField from '../../../NativeWrappers/CollectViews/CardTextField';
import ExpDateTextField from '../../../NativeWrappers/CollectViews/ExpDateTextField';
import CardNumberLabel from '../../../NativeWrappers/ShowViews/CardNumberLabel';
import ExpDateLabel from '../../../NativeWrappers/ShowViews/ExpDateLabel';

const VGSCollectManager = NativeModules.VGSCollectManager;
const VGSShowManager = NativeModules.VGSShowManager;

import {StyleSheet, Text, View, Button} from 'react-native';

const CollectShowFormView = () => {
  const [jsonText, setJsonText] = useState('No response data');
  return (
    <View style={styles.sectionContainer}>
      <CardTextField style={{height: 50, margin: 8}} />
      <ExpDateTextField style={{height: 50, margin: 8}} />
      <View style={{height: 8}}/>
      <Button
        title="START SCANNING"
        onPress={() => VGSCollectManager.presentCardScanner()}
      />
      <View style={{height: 16}}/>
      <Button
        title="CONFIRM DATA"
        onPress={() =>
          VGSCollectManager.submitData(value => {
            setJsonText(value);
          })
        }
      />
      <View style={{height: 16}}/>
      <CardNumberLabel style={{height: 50, margin: 8}} />
      <ExpDateLabel style={{height: 50, margin: 8}} />
      <View style={{height: 16}}/>
      <Button
        title="REVEAL DATA"
        onPress={() =>
          VGSShowManager.revealData(value => {
            setJsonText(value);
          })
        }
      />
      <Text style={styles.sectionDescription}>{jsonText}</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
  },
  sectionDescription: {
    marginTop: 12,
    fontSize: 16,
    fontWeight: '800',
  },
  highlight: {
    fontWeight: '700',
  },
});

export default CollectShowFormView;
