
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
	firebase.database().ref("users").once("value").then(function(snapshot) {
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
	
	console.log("Fin de la recup de data");
	var tab = setTimeout(tabSort(tab0,tab1), 2000);
	setTimeout(tabDisp(tab.tab2,tab.tab3), 4000);
	
});

function tabDisp(tab2,tab3){
		for (var i=0; i<tab2.length; i++) {	
		console.log("Joueur : ",tab2[i] ,"Score : ", tab3[i]);	
	}
}

function tabSort(tab0,tab1){
	console.log("tab0.length 2 : ", tab0.length);
	console.log("tab1.length 2 : ", tab1.length);
	var tab2 = [];
	var tab3 = [];
	for (var i=0; i<tab0.length; i++){
	 	if (i==0){
			tab2.push(tab0[tabMax(tab1)]);
			tab3.push(tab1[tabMax(tab1)]);
			console.log("Yeet");
		}
		else {
			tab0.splice(tabMax(tab1),1);
			tab2.push(tab0[tabMax(tab1)]);	
			tab3.push(tab1[tabMax(tab1)]);
			console.log("Yah");
		}
			
	}
	return {
			tab2 : tab2,
			tab3 : tab3
	};
}	

function tabMax(tab){
	var max;
	var v;
	for (i=0; i<tab.length; i++) {
		if (i == 0) {
			v = 0;
		}
		else {
			if (tab[i] > tab[v]){
				v = i;
			}
		}
	}
	return v;
}
