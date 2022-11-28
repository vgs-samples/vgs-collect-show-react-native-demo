/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react';
import type {Node} from 'react';
import {StyleSheet, Text, View, SafeAreaView} from 'react-native';

import {NavigationContainer} from '@react-navigation/native';
import {createStackNavigator} from '@react-navigation/stack';

import {UseCases} from './models/UseCases';
import UseCasesScreen from './screens/UseCasesScreen';
import CollectCardDataScreenSimple from './screens/UseCases/CollectShowCardDataSimpleSetup/CollectShowCardDataSimple';
import CollectShowTabScreen from './screens/UseCases/CollectShowCardDataAdvancedSetup/CollectShowTabScreen';

const Stack = createStackNavigator();

export default function App() {
  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen
          name="UseCasesScreen"
          component={UseCasesScreen}
          options={{headerShown: false}}
        />
        <Stack.Screen
          name={UseCases.CollectShowCardDataSimple}
          component={CollectCardDataScreenSimple}
          options={{
            title: 'Collect & Show Card Data',
          }}
        />
        <Stack.Screen
          name={UseCases.CollectShowCardDataAdvanced}
          component={CollectShowTabScreen}
          options={{
            title: 'Collect & Show Card Data',
          }}
        />
      </Stack.Navigator>
    </NavigationContainer>
  );
}
