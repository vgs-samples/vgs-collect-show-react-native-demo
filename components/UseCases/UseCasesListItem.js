import React from 'react';
import {Text, StyleSheet, Pressable, Image} from 'react-native';
// import {Ionicons} from '@expo/vector-icons';

import {useNavigation} from '@react-navigation/native';

function UseCasesListItem({title, routeName}) {
  const navigation = useNavigation();

  function pressItemHandler() {
    navigation.navigate(routeName);
  }

  console.log('Image!');

  return (
    <Pressable
      onPress={pressItemHandler}
      style={({pressed}) => [styles.item, pressed && styles.pressed]}>
      <Text style={styles.text}>{title}</Text>
      <Image
        style={styles.icon}
        source={require('../../assets/baseline_chevron_right.png')}></Image>
      {/* <Ionicons name="chevron-forward" size={24} color="#7c7c7c"></Ionicons> */}
    </Pressable>
  );
}

export default UseCasesListItem;

const styles = StyleSheet.create({
  item: {
    backgroundColor: 'white',
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginHorizontal: 24,
    marginVertical: 8,
    padding: 24,
    elevation: 2,
    shadowColor: 'black',
    shadowOffset: {width: 1, height: 1},
    shadowOpacity: 0.1,
    shadowRadius: 2,
    borderColor: '#cccccc',
    borderWidth: 1,
    borderRadius: 4,
  },
  pressed: {
    opacity: 0.7,
  },
  text: {
    fontSize: 22,
  },
  icon: {
    width: 200,
    height: 200,
  },
});
