

# Projekt: Smart Home

- Projekt představuje aplikaci pro virtuální simulaci inteligentního domu

- Projekt má dva scenario pro různé konfigurace domy

- Konfigurace domu včetně stavu jednotlivých zařízení se nahrávána přímo z třídy, nebo když už existuje nahrávána z souboru location.json

- Design patterny:
  - State machine
  - Factory
  - Singleton
  - Listener
- Zarižení domu jsou rozděleno do zarizení, spotřebiče a sensoriu
  - Sensory lide v místnosti zapíná světla
  - Sensor na vlhkost
  - Jistič (výpadek elektřiny) provokuje zapnuti generátory elektřiny a vypnutí všech nedůležitých spotřebičů
  - Fire sensor
  - Temperaturní sensor zapne a vypne přímotopy nebo airconditioner

- Spotřebiče mají svojí spotřebu elektřiny, vody a paliva v aktivním stavu, nebo stavu různých program, které odehrává
- Consuming report generuje informace Kolik jednotlivé spotřebiče spotřebovaly elektřiny, benzíny, vody. A generuje finanční report.

- Aplikace běží s určitým zrychlením, které se dá nastavit
- Osoby májí dovolení používat jednotlivá zařízení
- Jednotlivá zařízení mohou měnit svůj stav podle programy