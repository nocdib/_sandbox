import React from "react";
import { Text, StyleSheet, View, Button } from "react-native";

const HomeScreen = (props) => {
  return(
    <View>
      <Text style={styles.text}>HomeScreen</Text>
      <Button
        color='#FF1000'
        onPress={() => props.navigation.navigate('Components')}
        title='Components'
      />
      <Button 
        style={styles.button}
        onPress={() => props.navigation.navigate('List')}
        title='List'
      />
    </View>
  );
};

const styles = StyleSheet.create({
  text: {
    fontSize: 30
  },

});

export default HomeScreen;
