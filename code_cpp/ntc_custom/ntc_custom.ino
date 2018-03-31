int cartridgePin = 3;
#define ThermistorPin A0

double B = 4036.0;
double R = 39000.0;
double R0 = 100000.0;
double T0 = 25.0 + 273.0;
double T = 0.0;
double Vo = 0.0;


void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  if (Serial.available()) {

    char ch = Serial.read();

    if (isDigit(ch)) {
      int speed = map(ch, '0', '9', 0, 255);
      analogWrite(cartridgePin, speed);
      Serial.println(speed);
    }
    else {
      Serial.print("Unexpected Char ");
      Serial.print(ch);
    }
  }
  

  Vo = analogRead(ThermistorPin);
  Serial.print("Analog In ; ");
  Serial.println(Vo);
  
  float a = R/( (1024/Vo) - 1);
  
  T = 1/( (log((a/R0)*1000.0)-log(1000.0))/B + 1/T0)-273;
  
  Serial.print("Temperature : ");
  Serial.println(T);
  Serial.println("");
  
  delay(500);
  
}


