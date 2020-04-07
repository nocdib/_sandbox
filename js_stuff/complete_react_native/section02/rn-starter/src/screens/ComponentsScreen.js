import React from 'react';
import {View, Text, StyleSheet} from 'react-native';

const ComponentsScreen = function() {
    const name = 'Greg';

    return( 
    <View>
        <Text style={styles.style1}>Getting started with React Native</Text>
        <Text style={styles.style2}>My name is {name}.</Text>
    </View>
    );
};

const styles = StyleSheet.create({
    style1: {
        fontSize: 45,
    },
    style2: {
        fontSize: 20,
    }
});

export default ComponentsScreen;