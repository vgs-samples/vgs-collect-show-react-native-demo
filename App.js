/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {useState} from 'react';
import {
  SafeAreaView,
  StyleSheet,
  View,
  Text,
  TextInput,
  Button,
  TouchableOpacity,
  ScrollView,
  requireNativeComponent,
} from 'react-native';

import {Colors} from 'react-native/Libraries/NewAppScreen';

import {NativeModules} from 'react-native';

var CardCollector = NativeModules.CardCollector;
var VGSManager = NativeModules.VGSManager;
var VGSShowManager = NativeModules.VGSShowManager;
const CardTextField = requireNativeComponent('VGSCardTextField');
const ExpDateTextField = requireNativeComponent('VGSExpDateTextField');
const CVCTextField = requireNativeComponent('VGSCVCTextField');

const CardNumberLabel = requireNativeComponent('VGSCardLabel');

const App: () => React$Node = () => {
  const [jsonText, setJsonText] = useState('No response data');

  return (
    <SafeAreaView style={styles.container}>
      <ScrollView
        style={styles.scrollView}
        contentContainerStyle={{flexGrow: 1}}
        ref={ref => (this.scrollView = ref)}
        onContentSizeChange={(contentWidth, contentHeight) => {
          this.scrollView.scrollToEnd({animated: true});
        }}>
        <View style={styles.body}>
          <CardTextField style={{height: 50, margin: 20}} />
          <ExpDateTextField style={{height: 50, margin: 20}} />
          <CVCTextField style={{height: 50, margin: 20}} />
          <Button
            title="START SCANNING"
            onPress={() => VGSManager.presentCardIO()}
          />
          <Button
            title="CONFIRM DATA"
            onPress={() =>
              VGSManager.submitData(value => {
                setJsonText(value);
              })
            }
          />
          <Text style={styles.sectionDescription}>{jsonText}</Text>
          <CardNumberLabel style={{height: 50, margin: 20}} />
          <Button
            title="REVEAL DATA"
            onPress={() =>
              VGSShowManager.revealData(value => {
                //setJsonText(value);
              })
            }
          />
        </View>
      </ScrollView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  scrollView: {
    backgroundColor: Colors.green,
    flexGrow: 1,
  },
  engine: {
    position: 'absolute',
    right: 0,
  },
  body: {
    backgroundColor: '#00aeef',
    flex: 1,
  },
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
    color: Colors.black,
  },
  sectionDescription: {
    paddingHorizontal: 24,
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
    color: Colors.dark,
  },
  highlight: {
    fontWeight: '700',
  },
  footer: {
    color: Colors.dark,
    fontSize: 12,
    fontWeight: '600',
    padding: 4,
    paddingRight: 12,
    textAlign: 'right',
  },
  wrapper: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    height: 60,
    left: 50,
    color: 'black',
  },
});

export default App;
