  <!-- firebase config -->
<script src="https://cdn.firebase.com/libs/firebaseui/3.5.2/firebaseui.js"></script>
<link type="text/css" rel="stylesheet" href="https://cdn.firebase.com/libs/firebaseui/3.5.2/firebaseui.css" />

   <script>
   
   var ui = new firebaseui.auth.AuthUI(firebase.auth());
   ui.start('#firebaseui-auth-container', {
  signInOptions: [
    // List of OAuth providers supported.
    firebase.auth.GoogleAuthProvider.PROVIDER_ID,
    firebase.auth.FacebookAuthProvider.PROVIDER_ID,
    firebase.auth.TwitterAuthProvider.PROVIDER_ID,
    firebase.auth.GithubAuthProvider.PROVIDER_ID
  ],
  // Other config options...
});


   
   
     const firebaseConfig = {
  	apiKey: "AIzaSyAjaTBN4kTCJ7TZCzDZ15Eqs7NdYexoGRA",
  	authDomain: "project-4248963297216318184.firebaseapp.com",
  	databaseURL: "https://project-4248963297216318184.firebaseio.com",
  	projectId: "project-4248963297216318184",
  	storageBucket: "project-4248963297216318184.appspot.com",
  	messagingSenderId: "350160857770",
  	appId: "1:350160857770:web:83ae2115296dadccd43ed3"
  };
   // Initialize Firebase
  var defaultProject = firebase.initializeApp(firebaseConfig);
	alert(defaultProject);
  </script>
  var provider = new firebase.auth.GoogleAuthProvider();
  firebase.auth().signInWithPopup(provider).then(function(result) {
  // This gives you a Google Access Token. You can use it to access the Google API.
  var token = result.credential.accessToken;
  // The signed-in user info.
  var user = result.user;
  alert("user: "+user.getDisplayName());
  // ...
}).catch(function(error) {
  // Handle Errors here.
  var errorCode = error.code;
  var errorMessage = error.message;
  // The email of the user's account used.
  var email = error.email;
  // The firebase.auth.AuthCredential type that was used.
  var credential = error.credential;
  // ...
});
  

  <!-- firebase config end-->