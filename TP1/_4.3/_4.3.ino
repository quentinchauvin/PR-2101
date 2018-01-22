#define led1 11

// the setup routine runs once when you press reset:
void setup() {
  // initialize serial communication at 9600 bits per second:
  Serial.begin(9600);
  pinMode(led1, OUTPUT);
}

// the loop routine runs over and over again forever:
void loop() {
  
  // read the input on analog pin 0:
  int sensorValue = analogRead(A0); // converting that reading to voltage, for 3.3v arduino use 3.3
 float voltage = sensorValue * 5.0;
 voltage /= 1024.0; 
 
 // print out the voltage
 Serial.print(voltage); Serial.println(" volts");
 
 // now print out the temperature
 float temperatureC = (voltage - 0.5) * 100 ;  //converting from 10 mv per degree wit 500 mV offset
                                               //to degrees ((voltage - 500mV) times 100)
 Serial.print(temperatureC); Serial.println(" degrees C");
  // print out the value you read:
  int intensite = map (sensorValue,0,1023,0,255);
  //analogWrite(led1, intensite);
  Serial.println(intensite);
  delay(1000);        // delay in between reads for stability
}
