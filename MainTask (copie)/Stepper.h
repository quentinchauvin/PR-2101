#include <Energia.h>
#include <xdc/runtime/Error.h>
#include <ti/sysbios/knl/Event.h>
#include <ti/sysbios/BIOS.h>

class Stepper {
 private:
  int motorSpeed;

 public:
  Stepper();
  void setMotorSpeed(int);
  int getMotorSpeed();
};

