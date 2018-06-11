/*
 * La BtTask est chargée de la communication bluetooth. 
 * 
 * Elle ne recoit donc qu un parametre qui est une valeur pwm
 * soit une valeur comprise dans l intervalle [0 ; 255] 
 * 
 * Le flux d'infomation que cette task recoit est :
 *    <== feedBacks
 *    
 * Les getters et setters sont detailles dans le fichier "FeedBacks.cpp"
 */



void BtSetup() {  
  Serial.begin(115200);  

  Serial1.begin(115200);  
  //Serial1.print("$"); 
  //Serial1.print("$");
  //Serial1.print("$");  
  delay(100);  
  //Serial1.println("U,9600,N"); 
  //Serial1.begin(9600); 
  Serial.println("GO");
  Serial1.println("GO");
  Serial.println(createRandData());
}

void Btloop() {
  delay(500);
  
    int y = ((int)Serial1.read());
  if(y != -1)  // If the bluetooth sent any characters
  {
    // Send any characters the bluetooth prints to the serial monitor

    if(((char)y) == 'a'){
      Serial.println("Send");
      int temp = rand()%300;
      int dist = rand()%1000;
      int gas = rand()%20;
      Serial1.print(createRandData());
    }
      
  }
  if(Serial.available())  // If stuff was typed in the serial monitor
  {
    // Send any characters the Serial monitor prints to the bluetooth
    Serial1.print((char)Serial.read());
  }
}

String createRandData() {
  String var = "/";
  //var += String(feedBacks.getHotendTemp());
  var += String(rand()%250);
  var += "t";
  var += String(rand()%90);
  var += "p";
  var += String(rand()%1000);
  var += "d";
  var += String(rand()%10);
  var += "g";
  Serial.println(var);
  return var;
}

