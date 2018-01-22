/*
  AnalogReadSerial
  Reads an analog input on pin 0, prints the result to the serial monitor.
  Graphical representation is available using serial plotter (Tools > Serial Plotter menu)
  Attach the center pin of a potentiometer to pin A0, and the outside pins to +5V and ground.

  This example code is in the public domain.
*/

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
  int sensorValue = analogRead(A0); 
  int voltage = sensorValue * 5000 / 1024;
  int temp = (voltage - 500)/10;
  // print out the value you read:
  int intensite = map (sensorValue,0,1023,0,255);
  //analogWrite(led1, intensite);
  Serial.println(temp);
  Serial.println(intensite);
  delay(1000);        // delay in between reads for stability
}
