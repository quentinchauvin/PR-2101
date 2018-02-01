/*********************

Example code for the Adafruit RGB Character LCD Shield and Library

This code displays text on the shield, and also reads the buttons on the keypad.
When a button is pressed, the backlight changes color.

**********************/

// include the library code:
#include <Wire.h>
#include <Adafruit_RGBLCDShield.h>
#include <utility/Adafruit_MCP23017.h>

// The shield uses the I2C SCL and SDA pins. On classic Arduinos
// this is Analog 4 and 5 so you can't use those for analogRead() anymore
// However, you can connect other I2C sensors to the I2C bus and share
// the I2C bus.
Adafruit_RGBLCDShield lcd = Adafruit_RGBLCDShield();

// These #defines make it easy to set the backlight color
#define RED 0x1
#define YELLOW 0x3
#define GREEN 0x2
#define TEAL 0x6
#define BLUE 0x4
#define VIOLET 0x5
#define WHITE 0x7

int i = 0;
float sommeTmp = 0;

void setup()
{
    // Debugging output
    Serial.begin(9600);
    // set up the LCD's number of columns and rows:
    lcd.begin(16, 2);

    // Print a message to the LCD. We track how long it takes since
    // this library has been optimized a bit and we're proud of it :)
    int time = millis();
    //lcd.print("Hello, world!");
    time = millis() - time;
    Serial.print("Took ");
    Serial.print(time);
    Serial.println(" ms");
    lcd.setBacklight(WHITE);
    lcd.setCursor(0,0);
    lcd.print("Temperature :");

    lcd.setCursor(6,1);
    lcd.print("");
}

void loop()
{
    if(i==3){
    i+0;
    Serial.print("Temperature moyenne : ");
    Serial.println(sommeTmp/3);
    

    lcd.setCursor(0, 1);
    lcd.print(sommeTmp/3);
    sommeTmp=0;
    i=0;
    }
  else{
    i++;
    sommeTmp+=getTmp();
    }

    delay(500);
}

float getTmp()
{
    // read the input on analog pin 0:
    int sensorValue = analogRead(A0); // converting that reading to voltage, for 3.3v arduino use 3.3
    float voltage = sensorValue * 5.0;
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

