import React from 'react';
import {StyleSheet} from 'react-native';

import {SafeAreaView} from 'react-native';

import UseCasesList from '../components/UseCases/UseCasesList';
import {useCases} from '../models/UseCaseData';

function UseCasesScreen() {
  return (
    <SafeAreaView>
      <UseCasesList useCases={useCases} />
    </SafeAreaView>
  );
}

export default UseCasesScreen;

const styles = StyleSheet.create({});
