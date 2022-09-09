import React, { Component } from 'react';
import {StyleSheet, View, Text, NativeEventEmitter, Button } from 'react-native';

import {
  Colors,
} from 'react-native/Libraries/NewAppScreen';

import CardNumberLayout from './android/fields/number/NativeView';

import CardExpDateLayout from './android/fields/date/NativeView';

import VGSTextView from './android/fields/show/text/NativeView';

import VGSShow from './android/module/show/VGSShow';

import VGSCollect from './android/module/collect/VGSCollect';

import { DeviceEventEmitter } from 'react-native';

export default class VGSFormView extends  Component<Props> {
    constructor(props) {
        super(props);
        this.listener = DeviceEventEmitter.addListener('VGSCollectOnVGSResponse', e => this.showUserData(e));
        this.listener = DeviceEventEmitter.addListener('cardNumberToken', e => this.showCardNumberToken(e));
        this.listener = DeviceEventEmitter.addListener('expirationDateToken', e => this.showExpirationDateToken(e));
        this.state = {
          bodyText: "Code:",
          cardNumberToken: " ",
          expirationDateToken: " ",
        };
    }

    showUserData = (msg) => {
        this.setState({ bodyText: msg })
    }

    showCardNumberToken = (msg) => {
        this.setState({ cardNumberToken: msg })
    }

    showExpirationDateToken = (msg) => {
        this.setState({ expirationDateToken: msg })
    }

    revealData = () => {
        var data = {
            'payment_card_number': this.state.cardNumberToken,
            'payment_card_expiration_date': this.state.expirationDateToken
        };

        VGSShow.submitAsync(data)
    }

    render() {
        return (
            <View style={{
                flex: 1,
                flexDirection: 'row',
                justifyContent:'center'
            }}>
                <View style={{
                    width:'55%', height: '100%', padding:3,
                }}>
                    <CardNumberLayout
                        style={styles.collectField}
                        hint={'Card Number'}
                        fiendName={'cardNumber'}
                        corners={12}
                        fontSize={12}
                        padding={3}
                    />
                    <Text
                        ref= {(el) => { this.cardNumberToken = el; }}
                        style={styles.tokenInfo}
                        numberOfLines={1}
                        onChangeText = {this.showCardNumberToken}>
                            {this.state.cardNumberToken}
                    </Text>
                    <CardExpDateLayout
                        style={styles.collectField}
                        hint={'Expiration Date'}
                        fiendName={'expDate'}
                        corners={12}
                        fontSize={12}
                        padding={3}
                     />
                     <Text
                         ref= {(el) => { this.expirationDateTokenValue = el; }}
                         style={styles.tokenInfo}
                         numberOfLines={1}
                         onChangeText = {this.showExpirationDateToken}>
                         {this.state.expirationDateToken}
                     </Text>

                    <View style={{
                        marginBottom:20, marginLeft:20, marginRight:20
                    }}>
                        <Button style={styles.button}
                            title="Submit"
                            onPress={ () => VGSCollect.submitAsync() }
                        />
                    </View>

                    <View style={styles.bodyResponse }>
                        <Text style={styles.titleText}
                            onPress={this.onPressTitle}
                            onChangeText = {this.showUserData}
                            >
                            {this.state.bodyText}
                        </Text>
                    </View>
                </View>


                <View style={{
                        width: 1, height: '100%', backgroundColor: 'black'
                      }} />


                <View style={{ padding:3, width:'44%', height: '100%' }}>

                    <VGSTextView
                        style={styles.showField}
                        hint={'Card Number'}
                        contentPath={'json.payment_card_number'}
                        corners={12}
                        fontSize={12}
                        padding={3}
                    />

                    <VGSTextView
                        style={styles.showField}
                        hint={'Expiration Date'}
                        contentPath={'json.payment_card_expiration_date'}
                        corners={12}
                        fontSize={12}
                        padding={3}
                    />

                    <View style={{
                        marginBottom:20, marginLeft:20, marginRight:20
                    }}>
                        <Button style={styles.revealButton}
                            title="Reveal"
                            onPress={this.revealData}
                        />
                    </View>

                </View>
            </View>
    );}}

var styles = StyleSheet.create({
  tokenInfo: {
    fontSize: 9,
    marginBottom: 16
  },
  collectField: {
    width:'100%',
    height: 40,
    marginBottom: 3
  },
  showField: {
    width:'100%',
    height: 40,
    marginTop: 12,
    marginBottom: 20
  },
  button: {
    padding: 14,
    backgroundColor:'skyblue'
  },
  revealButton: {
    padding: 14,
    backgroundColor:'skyblue'
  },
});