/*
 * La Stepper est chargée de la gestion de l enrouleur. 
 * 
 * Elle ne recoit donc qu un parametre qui est une valeur pwm
 * soit une valeur comprise dans l intervalle [0 ; 255] 
 * 
 * Le flux d'infomation que cette task recoit/envoie sont :
 *    <== stepper
 *    
 * Les getters et setters sont detailles dans le fichier "Stepper.cpp"
 */

#include <Stepper.h>
int dirPin = 36;
int stepPin = 35;
int steps = 1024;

void StepperSetup() {  
  Stepper stepper = new Stepper(steps, dirPin, stepPin);
  stepper.setSpeed(6);
}

void StepperLoop() {
  stepper.step(1024);
  delay(1000);
  //Serial.print("Motor speed : ");
  //Serial.println(stepper.getMotorSpeed());
}
