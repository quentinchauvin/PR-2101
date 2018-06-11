#include "Button.h"
#include "FeedBacks.h"
#include "Stepper.h"
#include <Thermistor.h>

/*
 * Cette task recoit l information de tous les capteurs soit :
 *    - NTC 100K -> temperature de la buse 
 *               -> temperature du tuyeau
 *    - MiCS5524 -> gaz
 *    
 * Elle gère aussi le moteur DC, les buses de chauffes et les sets de ventilateurs.
 *    
 * Les flux d'infomation que cette task recoit/envoie sont :
 *    ==> stepper
 *    ==> feedBacks
 *    <== settings 
 *    
 */

// Initialisation des objets de flux sortant
Button button(PUSH1);
FeedBacks feedBacks;
Stepper stepper;
Thermistor thermoBuse = new Thermistor(23);
Thermistor thermoTube = new Thermistor(24);
#define DC 25
bool dcOn = false;

void MainSetup() {  

}

void MainLoop() {
	dcOn = false;
  if (thermoBuse.getT()>=200){
	dcOn = true;
	analogWrite(DC, 128);
	delay(100);
  }
}

