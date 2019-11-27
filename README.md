# Ejemplos de mocking

**IMPORTANTE**: Eston son pruebas que involucra `Mocks`, no confundir con `Stub`.

## Mock

Van a encontrar los siguentes ejemplos:

- `MockingTest`: Prueba y verificación de utilización de listas.
- `DataBugTest`: Esto es una clase más compleja que levanta un json y permite acceder a las claves del objeto iterandolas y validando la nulidad. Pruebas a considerar: 
  - `testShouldIterateAnArrayExecutingLambdaTwice`
  - `testShouldIterateAnArrayExecutingNoLambdaBecauseValueIsNull`

## Stubs

Van a encontrar los siguentes ejemplos:

- `FileSystemTest`: Aquí el FS (file system) es un impostor del tipo `Stub` donde se consume pero no se verifica que se hayan producido las llamadas a los métodos simulados.
