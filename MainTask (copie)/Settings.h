#include <Energia.h>
#include <xdc/runtime/Error.h>
#include <ti/sysbios/knl/Event.h>
#include <ti/sysbios/BIOS.h>

class Settings {
  private:
    float temp;
    int motor_fan_state;
    int fil_fan_state;
    int spool_speed;

  public:
    Settings();

    void setTemp(float);
    void setMotorFanState(int);
    void setFilFanState(int);
    void setSpoolSpeed(int);

    float getTemp();
    int getMotorFanState();
    int getFilFanState();
    int getSpoolSpeed();
};
