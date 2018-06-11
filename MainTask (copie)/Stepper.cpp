#include "Stepper.h"

Stepper::Stepper() {
  int motorSpeed = 0;
}

void Stepper::setMotorSpeed(int _motorSpeed) {
  motorSpeed = _motorSpeed;
}

int Stepper::getMotorSpeed() {
  return motorSpeed;
}
