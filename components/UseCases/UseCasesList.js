import React from 'react';
import {FlatList, StyleSheet} from 'react-native';

import {GlobalStyles} from '../../constants/styles';
import UseCasesListItem from './UseCasesListItem';

function UseCasesList({useCases}) {
  function renderListItem(itemData) {
    return (
      <UseCasesListItem
        routeName={itemData.item.routeName}
        title={itemData.item.title}></UseCasesListItem>
    );
  }

  return (
    <FlatList
      style={styles.list}
      keyExtractor={item => item.routeName}
      data={useCases}
      renderItem={renderListItem}></FlatList>
  );
}

export default UseCasesList;

const styles = StyleSheet.create({
  list: {
    width: '100%',
    height: '100%',
    backgroundColor: GlobalStyles.colors.systemBackground,
  },
});
