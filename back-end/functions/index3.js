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
	var i = 0;
	firebase.database().ref("users").once("value").then(function(snapshot) {
    	snapshot.forEach(function(childSnapshot) {	
      	var key = childSnapshot.key;
      	var childData = childSnapshot.val();
	tab0[i] = key;
	tab1[i] = childSnapshot.child("score").val();
	i++;
      	console.log("Données de ",key," : ",childData);

		var score = childSnapshot.child("score").val();
		console.log("Score : ",score);
  		});
	});

	console.log("Fin de la recup de data");
	var tab2 = [];
	var tab3 = [];
	for (i=0; i<tab0.length; i++){
	 	if (i==0){
			tab2[i]=tab0[tabMax(tab0)];
			tab3[i]=tab1[tabMax(tab0)];
		}
		else {
			tab0.splice(tabMax(tab0),1);
			tab2[i]=tab0[tabMax(tab0)];	
			tab3[i]=tab1[tabMax(tab0)];
		}
			
	}
	console.log("tab");
	for (i=0; i<tab2.length; i++) {	
		console.log("Joueur : ",tab3[i] ,"Score : ", tab2[i]);	
	}
});

function tabMax(tab){
	var max;
	var v;
	for (i=0; i<tab.length; i++) {
		if (i == 0) {
			v = 0;
		}
		else {
			if (tab[i] > v){
				v = i;
			}
		}
	}
	return v;
}
