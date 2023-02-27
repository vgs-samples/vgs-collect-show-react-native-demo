import React from 'react';
import {View, ActivityIndicator, StyleSheet} from 'react-native';
import {GlobalStyles} from '../../constants/styles';

function LoadingOverlay() {
  return (
    <View style={styles.container}>
      <ActivityIndicator size="large" color="blue"></ActivityIndicator>
    </View>
  );
}

export default LoadingOverlay;

const styles = StyleSheet.create({
  container: {
    position: 'absolute',
    left: 0,
    right: 0,
    top: 0,
    bottom: 0,
    opacity: 0.5,
    backgroundColor: '#cccccc',
    justifyContent: 'center',
    alignItems: 'center',
    // backgroundColor: '#cccccc',
    // flex: 1,
    // justifyContent: 'center',
    // alignItems: 'center',
    // padding: 24,
    // backgroundColor: '#cccccc',
  },
});
