import React, {Component} from 'react';
import {Button, DeviceEventEmitter, StyleSheet, Text, View} from 'react-native';

import CardNumberLayout from './android/fields/number/NativeView';

import CardExpDateLayout from './android/fields/date/NativeView';

import VGSTextView from './android/fields/show/text/NativeView';

import VGSShow from './android/module/show/VGSShow';

import VGSCollect from './android/module/collect/VGSCollect';

export default class VGSFormView extends Component<Props> {
    constructor(props) {
        super(props);
        this.listener = DeviceEventEmitter.addListener('VGSCollectOnVGSResponse', e => this.showUserData(e));
        this.listener = DeviceEventEmitter.addListener('cardNumberToken', e => this.showCardNumberToken(e));
        this.listener = DeviceEventEmitter.addListener('expirationDateToken', e => this.showExpirationDateToken(e));
        this.state = {
            bodyText: "Code:", cardNumberToken: " ", expirationDateToken: " ",
        };
    }

    showUserData = (msg) => {
        this.setState({bodyText: msg})
    }

    showCardNumberToken = (msg) => {
        this.setState({cardNumberToken: msg})
    }

    showExpirationDateToken = (msg) => {
        this.setState({expirationDateToken: msg})
    }

    revealData = () => {
        var data = {
            'payment_card_number': this.state.cardNumberToken,
            'payment_card_expiration_date': this.state.expirationDateToken
        };

        VGSShow.submitAsync(data)
    }

    copyCardNumber = () => {
        VGSShow.copyToClipboard('json.payment_card_number', 'FORMATTED')
    }

    render() {
        return (<View style={{
            flex: 1, flexDirection: 'column'
        }}>
            <View style={{
                width: '100%', height: '50%', justifyContent: 'center', padding: 24
            }}>
                <CardNumberLayout
                    style={styles.collectField}
                    hint={'Card Number'}
                    fiendName={'cardNumber'}
                    corners={12}
                    fontSize={16}
                />
                <Text
                    ref={(el) => {
                        this.cardNumberToken = el;
                    }}
                    style={styles.tokenInfo}
                    numberOfLines={1}
                    onChangeText={this.showCardNumberToken}>
                    {this.state.cardNumberToken}
                </Text>

                <View style={{
                    width: '100%', height: 8
                }}/>

                <CardExpDateLayout
                    style={styles.collectField}
                    hint={'Expiration Date'}
                    fiendName={'expDate'}
                    corners={12}
                    fontSize={16}
                />
                <Text
                    adjustsFontSizeToFit={true}
                    ref={(el) => {
                        this.expirationDateTokenValue = el;
                    }}
                    style={styles.tokenInfo}
                    numberOfLines={1}
                    onChangeText={this.showExpirationDateToken}>
                    {this.state.expirationDateToken}
                </Text>

                <View style={{
                    width: '100%', height: 8
                }}/>

                <Button style={styles.button}
                        title="Submit"
                        onPress={() => VGSCollect.submitAsync()}
                />

                <View style={{
                    width: '100%', height: 8
                }}/>

                <View style={styles.bodyResponse}>
                    <Text style={styles.titleText}
                          onPress={this.onPressTitle}
                          onChangeText={this.showUserData}
                    >
                        {this.state.bodyText}
                    </Text>
                </View>
            </View>

            <View style={{
                width: '100%', height: 1, backgroundColor: 'black'
            }}/>

            <View style={{width: '100%', height: '50%', justifyContent: 'center', padding: 24}}>

                <VGSTextView
                    style={styles.showField}
                    hint={'Card Number'}
                    contentPath={'json.payment_card_number'}
                    corners={12}
                    fontSize={16}
                />

                <View style={{
                    width: '100%', height: 16
                }}/>


                <VGSTextView
                    style={styles.showField}
                    hint={'Expiration Date'}
                    contentPath={'json.payment_card_expiration_date'}
                    corners={12}
                    fontSize={16}
                />

                <View style={{
                    width: '100%', height: 16
                }}/>

                <Button style={styles.revealButton}
                        title="Reveal"
                        onPress={this.revealData}
                />

                <View style={{
                        width: '100%', height: 16
                }}/>

                <Button style={styles.revealButton}
                        title="Copy"
                        onPress={this.copyCardNumber}
                />

            </View>
        </View>);
    }
}

var styles = StyleSheet.create({
    tokenInfo: {
        fontSize: 14
    }, collectField: {
        width: '100%', height: 56, padding: 8
    }, showField: {
        width: '100%', height: 40,
    }, button: {
        backgroundColor: 'skyblue'
    }, revealButton: {
        backgroundColor: 'skyblue'
    },
});