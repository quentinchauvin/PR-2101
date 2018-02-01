int motorPin = 3;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  if (Serial.available()){
    char ch = Serial.read();
    if (isDigit(ch)){
      int speed = map(ch,'0','9',0,255);
      analogWrite(motorPin, speed);
      Serial.println(speed);      
    }
    else {
      Serial.print("Unexpected Char ");
      Serial.print(ch);
    }
  }
}
