#include <Thermistor.h>

Thermistor::Thermistor(int _pin) {
  double t = 0.0;
  int pin = _pin
  pinMode(pin, INPUT);
}

double Thermistor::getT(){
	double R = 55300.0;
	double R0 = 100000.0;
	double B = 4036.0;
	double T0 = 25.0+273.0;
	double Vo = analogRead(pin);
	float a = R/( (1024/Vo) - 1)
	t = 1/( (log((a/R0)*1000.0)-log(1000.0))/B + 1/T0)-273;
	return t;
}

