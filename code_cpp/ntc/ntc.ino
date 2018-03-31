#include <stdio.h>
#include <math.h>

void setup()
{
  Serial.begin(9600);
  
  
}

void loop()
{
  //float R;  
  double B = 4036.0;
  double R = 52589.0;
  double R0 = 100000.0;
  double T0 = 25.0 + 273.0;
  double T = 0.0;
  
  float a = R/( (5.0/2.4) - 1);
  
  Serial.print("Intermédiaire : ");
  Serial.println(a);
  
  T = 1/( (log((a/R0)*1000.0)-log(1000.0))/B + 1/T0)-273;
  
  Serial.print("Temperature : ");
  Serial.println(T);
  
  Serial.println("");
  
  delay(500);

}

