import React from 'react';
import {Image} from 'react-native';

import {createBottomTabNavigator} from '@react-navigation/bottom-tabs';
// import Icon from 'react-native-ionicons';

import CollectCardDataScreen from './CollectCardDataScreen';
import ShowCardDataScreen from './ShowCardDataScreen';
import CollectShowCardDataContextProvider from '../../../state/CollectShowCardDataContext';

const BottomTabs = createBottomTabNavigator();

function CollectShowTabScreen() {
  return (
    <CollectShowCardDataContextProvider>
      <BottomTabs.Navigator>
        <BottomTabs.Screen
          name="ShowCardDataScreen"
          component={ShowCardDataScreen}
          options={{
            tabBarLabel: 'VGS Show',
            headerShown: false,
            tabBarIcon: ({color, size}) => (
              <Image
                style={{width: size, height: size, tintColor: color}}
                source={require('../../../assets/baseline_credit_card_black.png')}
              />
            ),
          }}
        />
        <BottomTabs.Screen
          name="Collect & Show Card Data"
          component={CollectCardDataScreen}
          options={{
            tabBarLabel: 'VGS Collect',
            headerShown: false,
            tabBarIcon: ({size, color}) => (
              <Image
                style={{width: size, height: size, tintColor: color}}
                source={require('../../../assets/baseline_add_card_black.png')}
              />
            ),
          }}
        />
      </BottomTabs.Navigator>
    </CollectShowCardDataContextProvider>
  );
}

export default CollectShowTabScreen;
