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
	var tab = new Array();
	int i = 0;
	firebase.database().ref("users").once("value").then(function(snapshot) {
    	snapshot.forEach(function(childSnapshot) {	
      	var key = childSnapshot.key;
      	var childData = childSnapshot.val();
	tab[i] = childSnapshot;
	i++;
      	console.log("Données de ",key," : ",childData);

		var score = childSnapshot.child("score").val();
		console.log("Score : ",score);
  		});
	});

	console.log("Fin de la recup de data");
	var tab2 = new Array();
	for (i=0, i<tab2.length, i++) {	
		if(i==0)
		tab2[i] = tabMax(tab);
		else 
		tab2[i] = tabMax(tab.splice(i,1));
	}
	console.log("tab");
	for (i=0, i<tab2.length, i++) {	
		console.log(tab2[i].child("score").val());	
	}
});

function tabMax(tab){
	var maxsnp;
	var snpv;
	for (i=0, i<tab.length, i++) {
		if (i == 0) {
			snpv = tab[i].child("score").val();
			maxsnp = tab[i];
		}
		else {
			if ( tab[i].child("score").val() > snpv){
				snpv = tab[i].child("score").val();
				maxsnp = tab[i];
			}
		}
	}
	return maxsnp;
}
