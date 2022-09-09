import React from 'react';
import { requireNativeComponent, View } from 'react-native';
import PropTypes from 'prop-types';

const NativeView = {
    name:'VGSTextView',
    propTypes: {
        hint:PropTypes.String
    }
}

module.exports = requireNativeComponent('VGSTextView', NativeView);