
var firebase = require("firebase");

var config = {
  apiKey: "AIzaSyCgu8NlOair0fbSJwuSOvzk9uZ1pn2tRdI",
  authDomain: "test-back-8653e.firebaseapp.com",
  databaseURL: "https://test-back-8653e.firebaseio.com/",
};

firebase.initializeApp(config);

// The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
const functions = require('firebase-functions');

const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.addMessage = functions.https.onRequest((req, res) => {
  const original = req.query.text;
  return admin.database().ref('/messages').push({original: original}).then((snapshot) => {
    return res.redirect(303, snapshot.ref);
  });
  console.log("heyyyyyyyyyyyyyyyyyyyy");
});


exports.makeUppercase = functions.database.ref('/messages/{pushId}/original').onWrite((event) => {
  const original = event.data.val();
  console.log('Uppercasing', event.params.pushId, original);
  const uppercase = original.toUpperCase();
  return event.data.ref.parent.child('uppercase').set(uppercase);
});

exports.test = functions.database.ref('/users/jojo/score').onWrite((event) => {
	console.log("onWrite listener OK");

	//const score = event.data.val();
	//console.log("Score is : ",score);
	var tab0 = [];
	var tab1 = [];
	var HS = firebase.database().ref("users");
	HS.orderByChild("score").once("value").then(function(snapshot) {
    	snapshot.forEach(function(childSnapshot) {	
      	var key = childSnapshot.key;
      	var childData = childSnapshot.val();
		
		tab0.push(key);
		tab1.push(childSnapshot.child("score").val());
      	console.log("Donnees de ",key," : ",childData);
		var score = childSnapshot.child("score").val();
		console.log("Score : ",score);
  		});
		


	});
});
