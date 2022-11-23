import React, {useEffect} from 'react';

import {
  NativeModules,
  View,
  StyleSheet,
  InteractionManager,
} from 'react-native';
import CardTextField from './ios/CollectViews/CardTextField';
import ExpDateTextField from './ios/CollectViews/ExpDateTextField';
import VGSCollectCardView from './ios/CollectViews/VGSCollectCardView';

///
const VGSCollectManager = NativeModules.VGSCollectManager;

function VGSCollectFormView() {
  return (
    <View style={styles.section}>
      {/* <VGSCollectCardView /> */}
      <CardTextField style={styles.field} />
      <ExpDateTextField style={styles.field} />
    </View>
  );
}

export default VGSCollectFormView;

const styles = StyleSheet.create({
  section: {
    marginHorizontal: 24,
    marginVertical: 16,
  },
  field: {
    height: 50,
    marginVertical: 16,
  },
});
