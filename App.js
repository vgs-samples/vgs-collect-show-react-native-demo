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
// import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';

import {UseCases} from './models/UseCases';
import UseCasesScreen from './screens/UseCasesScreen';
import CollectCustomCardDataScreen from './screens/UseCases/CollectCustomCardData/CollectCustomCardDataScreen';
import CollectShowTabScreen from './screens/UseCases/CollectShowCardData/CollectShowTabScreen';

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
          name={UseCases.CollectCustomCardData}
          component={CollectCustomCardDataScreen}
          options={{
            title: 'Collect Custom Card Data',
          }}
        />
        <Stack.Screen
          name={UseCases.CollectShowCardData}
          component={CollectShowTabScreen}
          options={{
            title: 'Collect & Show Card Data',
          }}
        />
        <Stack.Screen
          name={UseCases.TokenizeCardData}
          component={CollectCustomCardDataScreen}
          options={{
            headerShown: true,
            title: 'Tokenize Card Data',
          }}
        />
      </Stack.Navigator>
    </NavigationContainer>
  );
}

/*
import React from 'react';
import type {Node} from 'react';
import VGSFormView from './NativeWrappers/VGSFormView';

import {
  SafeAreaView,
  ScrollView,
  StatusBar,
  StyleSheet,
  Text,
  useColorScheme,
  View,
  Button,
} from 'react-native';

import {
  Colors,
  DebugInstructions,
  Header,
  LearnMoreLinks,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

const Section = ({children, title}): Node => {
  const isDarkMode = useColorScheme() === 'dark';
  return <VGSFormView />;
};

const App: () => Node = () => {
  const isDarkMode = useColorScheme() === 'dark';

  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
    flexGrow: 0,
  };

  return (
    <SafeAreaView style={backgroundStyle}>
      <StatusBar barStyle={isDarkMode ? 'light-content' : 'dark-content'} />
      <View
        style={{
          backgroundColor: isDarkMode ? Colors.darker : Colors.white,
          height: '100%',
          width: '100%',
        }}>
        <VGSFormView />
      </View>
    </SafeAreaView>
  );
};

export default App;
*/
