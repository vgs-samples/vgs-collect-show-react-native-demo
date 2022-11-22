import React from 'react';
import {Text, Pressable, StyleSheet} from 'react-native';
// import {Ionicons} from '@expo/vector-icons';

import {GlobalStyles} from '../../constants/styles';

function PrimaryButton({buttonStyle, textStyle, onPress, icon, children}) {
  return (
    <Pressable
      style={({pressed}) => [
        styles.button,
        pressed && styles.pressed,
        buttonStyle && buttonStyle,
      ]}
      onPress={onPress}>
      {/* <Ionicons style={styles.icon} name={icon} size={24} color="white" /> */}
      <Text style={[styles.text, textStyle && textStyle]}>{children}</Text>
    </Pressable>
  );
}

export default PrimaryButton;

const styles = StyleSheet.create({
  button: {
    height: 60,
    flexDirection: 'row',
    paddingHorizontal: 12,
    paddingVertical: 6,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: GlobalStyles.colors.systemBlue,
    borderRadius: 6,
  },
  pressed: {
    opacity: 0.7,
  },
  icon: {
    marginRight: 6,
  },
  text: {
    color: 'white',
    fontSize: 22,
    fontWeight: 'bold',
  },
});
