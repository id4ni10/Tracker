#include <avr/pgmspace.h>
#include "ESP8266.h"
#include <SoftwareSerial.h>
//#include <SPI.h>
#include <SD.h>

const char file[] PROGMEM = "datalog.txt";
const char ssid[] PROGMEM = "Claro-9704";
const char pass[] PROGMEM = "c412f5389704";
const char host[] PROGMEM = "ws.imap.org.br";
const char hello[] PROGMEM = "GET /sigem/api/horarios/138 HTTP/1.1\r\nHost: ws.imap.org.br\r\nConnection: close\r\n\r\n";

const char* const string_table[] PROGMEM = {file, ssid, pass, host, hello};
char _short[15];
char _long[90];

SoftwareSerial ss(2, 3);
ESP8266 wifi(ss);

void setup()
{
  Serial.begin(9600);
  while (!Serial);

  wifi.setOprToStationSoftAP();

  strcpy_P(_short, (char*)pgm_read_word(&(string_table[1])));
  strcpy_P(_long, (char*)pgm_read_word(&(string_table[2])));

  wifi.joinAP(_short, _long);
  wifi.disableMUX();

  Serial.println(wifi.getLocalIP().c_str());

  Serial.println(F("ready!"));

  SD.begin(4);
}

void loop()
{
  strcpy_P(_short, (char*)pgm_read_word(&(string_table[3])));
  
  if (wifi.createTCP(_short, 80)) {
    Serial.println(F("TCP on"));//<-- ROFLMAO
    strcpy_P(_long, (char*)pgm_read_word(&(string_table[4])));    

    wifi.send((const uint8_t*)_long, strlen(_long));
    
    uint8_t buffer[1024] = {0};
    uint32_t len = wifi.recv(buffer, sizeof(buffer), 10000);

    if (len > 0) {
      for (int i = 0; i < len; i++) {
        Serial.print((char)buffer[i]);
      }
      Serial.print(F("\r\n"));
    }
  } else {
    Serial.println(F("TCP off"));
  }
}

/*void append(char *text) {
  strcpy_P(_short, (char*)pgm_read_word(&(string_table[0])));
  File dataFile = SD.open(_short, FILE_WRITE);

  if (dataFile) {
    dataFile.println(text);
    dataFile.close();
  }
  }*/
