#include "Settings.h"

Settings::Settings() {
  float temp = 210;
  int motor_fan_state = 0;
  int fil_fan_state = 0;
  int spool_speed = 0;
}

void Settings::setTemp(float _temp) {
  temp = _temp;
}
void Settings::setMotorFanState(int _motor_fan_state) {
  motor_fan_state = _motor_fan_state;
}
void Settings::setFilFanState(int _fil_fan_state) {
  fil_fan_state = _fil_fan_state;
}
void Settings::setSpoolSpeed(int _spool_speed) {
  spool_speed = _spool_speed;
}

float Settings::getTemp() {
  return temp;
}
int Settings::getMotorFanState() {
  return motor_fan_state;
}
int Settings::getFilFanState() {
  return fil_fan_state;
}
int Settings::getSpoolSpeed() {
  return spool_speed;
}
