import React, {useEffect, useState, useContext} from 'react';
import {
  SafeAreaView,
  View,
  Text,
  ScrollView,
  StyleSheet,
  NativeModules,
  findNodeHandle,
  Alert,
} from 'react-native';

import PrimaryButton from '../../../components/UI/PrimaryButton';

// 1. Import VGS Show native view.
import VGSShowCardView from '../../../NativeWrappers/CollectViews/VGSShowCardView';

import {CollectShowCardDataContext} from '../../../state/CollectShowCardDataContext';

import {constants} from '../../../constants/constants';
import LoadingOverlay from '../../../components/UI/LoadingOverlay';

// 2. Import VGS Show native manager.
const VGSShowManager = NativeModules.VGSShowManagerAdvanced;

function ShowCardDataScreen() {
  const [isSubmitting, setIsSubmitting] = useState(false);
  const collectShowCardDataContext = useContext(CollectShowCardDataContext);

  let text = '';
  if (collectShowCardDataContext.hasPayload()) {
    text = JSON.stringify(collectShowCardDataContext.payload, null, 2);
  } else {
    text = 'No data to reveal! Collect card data first!';
  }

  useEffect(() => {
    // 3. Setup VGSShowManager once with useEffect([]).
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

  // 4. Create ref for native view, (cannot use useRef hook).
  let showCardViewRef;

  useEffect(() => {
    // 5. Find react node with native view ref.
    const showCardViewNode = findNodeHandle(showCardViewRef);
    if (showCardViewNode) {
      console.log('found show view node!!!');

      // 6. Bind VGSShowManager to view in native code.
      VGSShowManager.setupShowViewFromManager(
        showCardViewNode,
        {
          cardNumberFieldName: 'json.payment_card_number',
          expDateFieldName: 'json.payment_card_expiration_date',
        },
        result => {
          console.log(result);
          console.log('success setup show view!');
        },
      );
    }
    return () => {};
  }, [showCardViewRef]);

  function copyCardNumberHandler() {
    // Copy card number.
    VGSShowManager.copyCardNumber();
  }

  function revealHandler() {
    // 7. Reveal data if has payload.
    if (!collectShowCardDataContext.hasPayload()) {
      Alert.alert('No data to reveal!', 'Collect data first!');
    } else {
      setIsSubmitting(true);
      VGSShowManager.revealData(collectShowCardDataContext.payload, () => {
        setIsSubmitting(false);
        text = 'reveal success';
      });
    }
  }

  const payload = collectShowCardDataContext.payload;

  return (
    <SafeAreaView>
      <ScrollView style={styles.scrollView}>
        <VGSShowCardView
          style={styles.showCardView}
          ref={e => (showCardViewRef = e)}
        />
        <View style={styles.buttons}>
          <PrimaryButton onPress={revealHandler} buttonStyle={styles.button}>
            Reveal
          </PrimaryButton>
          <View style={styles.spacerView}></View>
          <PrimaryButton
            onPress={copyCardNumberHandler}
            buttonStyle={styles.button}
            icon="camera">
            Copy Card
          </PrimaryButton>
        </View>
        <Text numberOfLines={0} style={styles.consoleText}>
          {text}
        </Text>
      </ScrollView>
      {isSubmitting && <LoadingOverlay></LoadingOverlay>}
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
    fontSize: 16,
    fontWeight: '800',
  },
});
