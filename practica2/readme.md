# ArenaUSAC - Práctica 2 (IPC1)

## Descripción
Este proyecto es una simulación de batallas automáticas entre personajes estilo **Pokémon**, desarrollado en **Java con interfaz gráfica (Swing)**.  
Los combates se ejecutan de forma **concurrente mediante hilos (threads)**, mostrando en pantalla la bitácora de ataques, el estado de los combatientes y el historial de batallas realizadas.

## Funcionalidades principales
- **Gestión de personajes**: Agregar, editar, eliminar y visualizar personajes registrados.  
- **Validaciones**: Cada personaje debe tener nombre único y atributos en rangos válidos (HP, ataque, defensa, velocidad, agilidad).  
- **Simulación de batallas automáticas**:
  - Cada personaje ataca desde un hilo independiente.
  - La velocidad determina el tiempo entre ataques.
  - La agilidad define la probabilidad de esquivar.
  - La defensa reduce el daño recibido.
- **Historial de batallas**: Registro de participantes, fecha/hora, bitácora completa y ganador.  
- **Guardar y cargar estado** en archivo `.txt` (incluye personajes y batallas).  
- **Datos del estudiante**: Se muestra dentro del programa según corresponda.

## Interfaz gráfica
La aplicación utiliza **Java Swing**.  
Componentes principales:
- Tabla de personajes con todos los atributos.
- Formularios para agregar/editar personajes.
- Botones para eliminar y gestionar.
- Selección de combatientes A y B para iniciar la batalla.
- Área de bitácora que muestra en tiempo real los ataques.
- Menú para **Guardar/Cargar** estados del sistema.

## Estructura del proyecto

- ├── src/
- │   ├── model/        (Clases de dominio: Personaje, Batalla, Historial)
- │   ├── workers/      (Manejador de hilos para las batallas)
- │   ├── util/         (Módulo de persistencia)
- │   └── ui/           (Interfaz gráfica y clase principal)
- ├── Manuales/
- │   └── README.md     (Este archivo)
- ├── Informe.pdf
- ├── ManualTecnico.pdf
- └── ManualUsuario.pdf

## Problemas conocidos

- Si se cierra la aplicación durante una batalla, los hilos pueden quedar activos (se solucionó con cancelación, pero puede necesitar más pruebas).
- El archivo .txt puede corromperse si se edita manualmente con separadores inválidos.
- El refresco de la bitácora se hace con un Timer de Swing; en batallas muy rápidas puede omitir algunos logs (mejora pendiente).

## Posibles mejoras

- Migrar a JavaFX para una interfaz más moderna.
- Usar JSON en lugar de texto plano para guardar/cargar estado.
- Agregar un modo multibattle (torneo entre varios personajes).
- Incluir animaciones gráficas en lugar de texto en la bitácora.


