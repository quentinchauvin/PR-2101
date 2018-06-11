/*
 * La ScreenTask est chargée de la gestion de l ecran Kentec. Chaque parametre que rentre l utilisateur est appele "settings". 
 * Les settings comportent :
 *    - temperature (voulue pour la buse)
 *    - vitesse des ventilateurs moteur (pwm => [0 ; 255])
 *    - vitesse des ventilateurs filament (pwm => [0 ; 255])
 *    - vitesse de l enrouleur (pwm => [0 ; 255])
 *    
 * Les flux d'infomation que cette task recoit/envoie sont :
 *    ==> settings
 *    
 * Les getters et setters sont detailles dans le fichier "Settings.cpp"
 */

#include "Settings.h"

Settings settings;

// the setup routine runs once when you press reset:
void ScreenSetup() {  
  //Serial.begin(115200);
}

void ScreenLoop() {
  delay(1000);  
  
  settings.setTemp(500);
  settings.setMotorFanState(255);
  settings.setFilFanState(255);
  settings.setSpoolSpeed(125);
}

