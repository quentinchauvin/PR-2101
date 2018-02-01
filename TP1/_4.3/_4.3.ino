#define led1 11
int i = 0;
float sommeTmp = 0;

// the setup routine runs once when you press reset:
void setup()
{
    // initialize serial communication at 9600 bits per second:
    Serial.begin(9600);
    pinMode(led1, OUTPUT);
}

// the loop routine runs over and over again forever:
void loop()
{

  if(i==3){
    i+0;
    Serial.print("Temperature moyenne : ");
    Serial.println(sommeTmp/3);
    sommeTmp=0;
    i=0;
    }
  else{
    i++;
    sommeTmp+=getTmp();
    }
  
  
  delay(1000);        // delay in between reads for stability
}

float getTmp()
{
    // read the input on analog pin 0:
    int sensorValue = analogRead(A0); // converting that reading to voltage, for 3.3v arduino use 3.3
    Serial.print(sensorValue);
    Serial.println(" sensorValue");
    float voltage = sensorValue * 5.0;
    voltage = voltage *0.1+ 0.64;
    voltage /= 1024.0;

    // print out the voltage
    Serial.print(voltage);
    Serial.println(" volts");

   
    float temperatureC = (voltage - 0.5) * 100; 
   
    Serial.print(temperatureC);
    Serial.println(" degrees C");
    // print out the value you read:
    return temperatureC;
}
