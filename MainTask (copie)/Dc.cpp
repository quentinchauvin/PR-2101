#include "Dc.h"

Dc::Dc(int _pin) {
  int motorSpeed = 0;
  int pin = _pin
  pinMode(pin, OUTPUT);
}

int Dc::getMotorSpeed(){
  return motorSpeed
}

void Dc::setMotorSpeed(int _motorSpeed){
  motorSpeed = _motorSpeed;
}

void Dc::activate(){
  analogWrite(pin, motorSpeed);
}


