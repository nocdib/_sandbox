import React from 'react';
import {FlatList, Text, StyleSheet} from 'react-native';
import shortid from 'shortid'; // libary to generate a unique ID

const ageLimit = 90;

// Generate random age
const getRandomAge = (maxAge) => Math.floor(Math.random() * Math.floor(maxAge)) + 1;

// Generate a random color for the background of each list item
const randomColor = () => '#' + Math.floor(Math.random()*16777215).toString(16);

const data = [
    {name: 'Friend 1', age: getRandomAge(ageLimit), id: shortid.generate()},
    {name: 'Friend 2', age: getRandomAge(ageLimit), id: shortid.generate()},
    {name: 'Friend 3', age: getRandomAge(ageLimit), id: shortid.generate()},
    {name: 'Friend 4', age: getRandomAge(ageLimit), id: shortid.generate()},
    {name: 'Friend 5', age: getRandomAge(ageLimit), id: shortid.generate()},
];

const ListScreen = () => {
    return(
        <FlatList
        data={data}
        keyExtractor={friend => friend.id}
        renderItem={
            // Using an inline stylesheet so that the background color is generate each time a <Text> is rendered
            ({item}) => <Text style={{
                backgroundColor: randomColor(),
                margin: 5,
            }}> {item.name} - Age {item.age}</Text>
        }   
        
        />
            
    );
};

export default ListScreen;