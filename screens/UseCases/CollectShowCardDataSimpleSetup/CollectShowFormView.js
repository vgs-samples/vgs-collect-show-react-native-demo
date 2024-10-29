import React, {useState} from 'react';
import {Button, NativeModules, StyleSheet, Text, TextInput, View} from 'react-native';
import CardTextField from '../../../NativeWrappers/CollectViews/CardTextField';
import ExpDateTextField from '../../../NativeWrappers/CollectViews/ExpDateTextField';
import CardNumberLabel from '../../../NativeWrappers/ShowViews/CardNumberLabel';
import ExpDateLabel from '../../../NativeWrappers/ShowViews/ExpDateLabel';

const VGSCollectManager = NativeModules.VGSCollectManager;
const VGSShowManager = NativeModules.VGSShowManager;

const CollectShowFormView = () => {
    const [jsonText, setJsonText] = useState('No response data');
    const [isTextInputFocused, setTextInputFocused] = useState(false);
    const defaultBorderColor = '#808080';
    const focusedBorderColor = '#262626';
    return (
        <View style={styles.sectionContainer}>
            <Text style={styles.sectionHint}>Card Number</Text>
            <CardTextField
                style={{height: 50}}
                borderColors={[focusedBorderColor, defaultBorderColor]}
            />
            <View style={{
                flexDirection: 'row',
                paddingTop: 16
            }}>
                <View style={{flex: 1}}>
                    <Text style={styles.sectionHint}>Expiration Date</Text>
                    <ExpDateTextField
                        style={{height: 50}}
                        borderColors={[focusedBorderColor, defaultBorderColor]}
                    />
                </View>
                <View style={{width: 16}}/>
                <View style={{flex: 1}}>
                    <Text style={styles.sectionHint}>CVV</Text>
                    <TextInput
                        onFocus={() => setTextInputFocused(true)}
                        onBlur={() => setTextInputFocused(false)}
                        style={{
                            height: 50,
                            borderWidth: isTextInputFocused ? 2 : 1,
                            borderRadius: 4,
                            borderColor: isTextInputFocused ? focusedBorderColor : defaultBorderColor,
                            paddingLeft: 10
                        }}
                    />
                </View>
            </View>
            <View style={{height: 16}}/>
            <Button
                title="START SCANNING"
                onPress={() => VGSCollectManager.presentCardScanner()}
            />
            <View style={{height: 16}}/>
            <Button
                title="CONFIRM DATA"
                onPress={() =>
                    VGSCollectManager.submitData(value => {
                        setJsonText(value);
                    })
                }
            />
            <View style={{height: 16}}/>
            <CardNumberLabel style={{height: 50, margin: 8}}/>
            <ExpDateLabel style={{height: 50, margin: 8}}/>
            <View style={{height: 16}}/>
            <Button
                title="REVEAL DATA"
                onPress={() =>
                    VGSShowManager.revealData(value => {
                        setJsonText(value);
                    })
                }
            />
            <Text style={styles.sectionDescription}>{jsonText}</Text>
        </View>
    );
};

const styles = StyleSheet.create({
    sectionContainer: {
        margin: 24,
    },
    sectionTitle: {
        fontSize: 24,
        fontWeight: '600',
    },
    sectionDescription: {
        marginTop: 12,
        fontSize: 16,
        fontWeight: '800',
    },
    sectionHint: {
        fontSize: 12,
        color: '#767676',
        paddingBottom: 4,
    },
    highlight: {
        fontWeight: '700',
    },
});

export default CollectShowFormView;
