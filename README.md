
# Projekt: Smart Home

- Projekt představuje aplikaci pro virtuální simulaci inteligentního domu

- Projekt má dva scenario pro různé konfigurace domy

- Konfigurace domu včetně stavu jednotlivých zařízení se nahrávána přímo z třídy, nebo když už existuje nahrávána z souboru location.json

- Design patterny:
  - State machine
  - Factory
  - Singleton
  - Listener

F1
- Entity se kterými pracujeme je dům, garáž, patro v domu, každá lokace muže mít vice lokace a několik devices
- Sensory lide v místnosti zapíná světla, sensor na vlhkost, jistič (výpadek elektřiny) provokuje zapnuti generátory elektřiny a vypnutí všech nedůležitých spotřebičů, fire sensor, temperaturní sensor zapne a vypne přímotopy nebo airconditioners
- Spotřebiče audio, coffe machine, dishwasher, generator electriny, heate, keetle, lamp, oven, refrigerator, stove, TV, washing machine, watercloset, waterconcumer.
- Vše lide a domácí zvíře reprezentovaný jednou třídou skinbag, osoby májí dovolení používat jednotlivá zařízení
- Nemáme sportovní zarizení

F2
- Zařízení mají stav, který lze měnit pomocí API na jeho ovládání
- Akce z API jsou použitelné podle stavu zařízení
- Jednotlivá zařízení mohou měnit svůj stav samostatně

F3
- Spotřebiče mají svojí spotřebu elektřiny, vody a paliva v aktivním stavu, nebo stavu různých programů, které odehrává

F4
- State implements ConsumingState pomoci kterého sbíráme data jako spotřeba elektřiny, benzin, vody

F5
- Jednotlivé osoby a zvířata mohou provádět aktivity(akce), které mají nějaký efekt na zařízení.

F6
- Jednotlivá zařízení a osoby se v každém okamžiku vyskytují v jedné místnosti a náhodně generují eventy. Eventy můžou posílat ne jenom jednotlivým zařízení  ale i ve cele lokaci, např vypnut_svetlo

F7
- Osoby májí dovolení používat jednotlivá zařízení. Sensory ma nekolik Listenerů na které podepsaný zařízení a automatické provadí akce když něco se stane.

F8
- Consuming report generuje informace, kolik jednotlivé spotřebiče spotřebovaly elektřiny, benzíny, vody. A generuje finanční report.

F9
- Realizováno jednoduché všechno opravuje jeden skinbag když vide že stav BROKEN, a trvá to 50 minut.

F10
- Rodina je passivní a volný čas tráví doma (COVID_19!!! STAY HOME!!!)
