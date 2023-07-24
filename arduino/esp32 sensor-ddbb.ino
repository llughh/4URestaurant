#if ! (ESP8266 || ESP32 )
  #error This code is intended to run on the ESP8266/ESP32 platform! Please check your Tools->Board setting
#endif

#include "Credentials.h"
#include "DHT.h"
#include <TinyGPS++.h>
#include <SoftwareSerial.h>

// asignacion pin sensor temperatura
#define DHTPIN_C 25
#define DHTPIN_F 26
#define DHTTYPE_C DHT11
#define DHTTYPE_F DHT11
//asignacion pin sensor gps
static const int RXPin = 27, TXPin = 14;
static const uint32_t GPSBaud = 115200;

#define MYSQL_DEBUG_PORT      Serial

// Debug Level from 0 to 4
#define _MYSQL_LOGLEVEL_      1

#include <MySQL_Generic.h>

#define USING_HOST_NAME     true

TinyGPSPlus gps;
SoftwareSerial ss(RXPin, TXPin);

DHT dhtC (DHTPIN_C, DHTTYPE_C);  //declaracion sensor de calor
DHT dhtF (DHTPIN_F, DHTTYPE_F); //declaracion sensor de frio

#if USING_HOST_NAME
  char server[] = "195.235.211.197";
#else
  IPAddress server(195, 235, 211, 197);
#endif

uint16_t server_port = 3306;    

char default_database[] = "pri4URestaurant";           
char default_table[]    = "sensor";          


MySQL_Connection conn((Client *)&client);

MySQL_Query *query_mem;


void setup()
{
  Serial.begin(115200);
  while (!Serial && millis() < 5000); // wait for serial port to connect

  MYSQL_DISPLAY1("\nStarting Basic_Insert_ESP on", ARDUINO_BOARD);
  MYSQL_DISPLAY(MYSQL_MARIADB_GENERIC_VERSION);

  // Begin WiFi section
  MYSQL_DISPLAY1("Connecting to", ssid);
  
  WiFi.begin(ssid, pass);
  
  while (WiFi.status() != WL_CONNECTED) 
  {
    delay(500);
    MYSQL_DISPLAY0(".");
  }

  // print out info about the connection:
  MYSQL_DISPLAY1("Connected to network. My IP address is:", WiFi.localIP());

  MYSQL_DISPLAY3("Connecting to SQL Server @", server, ", Port =", server_port);
  MYSQL_DISPLAY5("User =", user, ", PW =", password, ", DB =", default_database);

  //inicializar sensores de temperatura, humedad y gps
  dhtC.begin();
  dhtF.begin();
  ss.begin(GPSBaud);
}

String runInsert(String INSERT)
{
  


  // Initiate the query class instance
  MySQL_Query query_mem = MySQL_Query(&conn);

  if (conn.connected())
  {
    MYSQL_DISPLAY(INSERT);
    
    // Execute the query
    // KH, check if valid before fetching
    if ( !query_mem.execute(INSERT.c_str()) )
    {
      MYSQL_DISPLAY("Insert error");
    }
    else
    {
      MYSQL_DISPLAY("Data Inserted.");
    }
  }
  else
  {
    MYSQL_DISPLAY("Disconnected from Server. Can't insert.");
  }
}
void displayInfo()
{
  Serial.print(F("Location: ")); 
  if (gps.location.isValid())
  {
    Serial.print(gps.location.lat(), 6);
    Serial.print(F(","));
    Serial.print(gps.location.lng(), 6);
  }
  else
  {
    Serial.print(F("INVALID"));
  }
  Serial.print(F("  Date/Time: "));
  if (gps.date.isValid())
  {
    Serial.print(gps.date.month());
    Serial.print(F("/"));
    Serial.print(gps.date.day());
    Serial.print(F("/"));
    Serial.print(gps.date.year());
  }
  else
  {
    Serial.print(F("INVALID"));
  }
  Serial.print(F(" "));
  if (gps.time.isValid())
  {
    if (gps.time.hour() < 10) Serial.print(F("0"));
    Serial.print(gps.time.hour());
    Serial.print(F(":"));
    if (gps.time.minute() < 10) Serial.print(F("0"));
    Serial.print(gps.time.minute());
    Serial.print(F(":"));
    if (gps.time.second() < 10) Serial.print(F("0"));
    Serial.print(gps.time.second());
  }
  else
  {
    Serial.print(F("INVALID"));
  }
  Serial.println();
}

void loop()
{
  int idSensor = 1;
  String longitud = "(gps.location.lng(), 6)";
  String latitud = "(gps.location.lat(), 6)";
  
  MYSQL_DISPLAY("Connecting...");
  
  //if (conn.connect(server, server_port, user, password))
  if (conn.connectNonBlocking(server, server_port, user, password) != RESULT_FAIL)
  {
    delay(5000);

    // leer humedad de ambos sensores y guardarlos en sus respectivas variables
    float humC = dhtC.readHumidity();
    float humF = dhtF.readHumidity();
    // leer temperatura de ambos sensores y guardarlos en sus respectivas variables
    float tempC = dhtC.readTemperature();
    float tempF = dhtF.readTemperature();

    //comprobar sensores
     
    if (isnan(humC) || isnan(tempC)) {
      Serial.println("Failed to read from DHT sensor!");
    } else {
      Serial.print("Humidity: ");
      Serial.print(humC);
      Serial.print("%");
  
      Serial.print("  |  "); 
  
      Serial.print("Temperature: ");
      Serial.print(tempC);
      Serial.print("°C ~ ");
   
   }
   if (isnan(humF) || isnan(tempF)) {
    Serial.println("Failed to read from DHT sensor!");
   } else {
    Serial.print("Humidity 2: ");
    Serial.print(humF);
    Serial.print("%");

    Serial.print("  |  "); 

    Serial.print("Temperature second sensor: ");
    Serial.print(tempF);
    Serial.print("°C ~ ");

   }

    
    //fin comprobacion
    while (ss.available() > 0)
    if (gps.encode(ss.read()))
      displayInfo(); // mostrar por consola los datos del sensor gps
    if (millis() > 5000 && gps.charsProcessed() < 10)
    {
      Serial.println(F("No GPS detected: check wiring."));
      
    }
    
  
  // Sample query
  String INSERT_SQL = String("INSERT INTO ") + default_database + "." + default_table + " (idSensor, tempC, humC, tempF, humF, longitud, latitud ) VALUES ('" + idSensor + "','"+tempC+"','"+humC+"','"+tempF+"','"+humF+"','"+longitud+"','"+latitud+"')";
    
    runInsert(INSERT_SQL);                      //llamar funcion runInsert
    conn.close();                     // close the connection
  } 
  else 
  {
    MYSQL_DISPLAY("\nConnect failed. Trying again on next iteration.");
  }

  MYSQL_DISPLAY("\nSleeping...");
  MYSQL_DISPLAY("================================================");
 
  delay(60000);
}
