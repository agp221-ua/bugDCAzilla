# Documentación de BugDCAzilla

## Introducción

BugDCAzilla es una herramienta de seguimiento de problemas que permite gestionar y rastrear los errores y problemas en tu proyecto de desarrollo.

## Inicio de Sesión

Para comenzar a utilizar BugDCAzilla, sigue estos pasos:

1. Accede a la página de inicio de sesión.
2. Ingresa tu nombre de usuario y contraseña.
3. Haz clic en el botón "Iniciar sesión" o "Registrarse" para crear una cuenta.

> **Nota:** Para crear una cuenta de administrador debes crear una cuenta y modificar el campo _false_ por _true_ en el fichero de base de datos `users.bd`

## Creación de un Nuevo Bug

Para registrar un nuevo error o problema en BugDCAzilla:

1. Inicia sesión en tu cuenta.
2. Dirígete a la sección "Crear Nuevo Bug".
3. Rellena los detalles del bug, incluyendo el título, la descripción y las etiquetas.
4. Guarda el bug.

## Búsqueda y Filtros

Utiliza las funciones de búsqueda y filtros de BugDCAzilla para encontrar bugs específicos o realizar un seguimiento de los problemas que te interesan.

- Utiliza palabras clave en la barra de búsqueda para buscar bugs por título o descripción o etiquetas.
- Puedes buscar solo por etiquetas empezando la busqueda por `:`, por ejemplo, para buscar por la etiqueta `dca`, tendrias que buscar por `:dca`.

## Administracion

- Para acceder a la sección de administración, debes iniciar sesión con una cuenta de administrador. Como
se ha mencionado anteriormente, para crear una cuenta de administrador debes crear una cuenta y modificar el campo _false_ por _true_ en el fichero de base de datos `users.bd`.
- Para cerrar o abrir un bug simplemente debes hacer clic en el texto de `[CL]` para reabrir el bug o en `[OP]` para cerrarlo (todo ello desde una cuenta de administrador).
- Si no deja ejecutar con doble click, simplemente ejecuta desde terminal con java -jar <fichero.jar>
  - El ejecutable se puede encontrar en [https://github.com/agp221-ua/bugDCAzilla/releases/tag/v1.0.1](https://github.com/agp221-ua/bugDCAzilla/releases/tag/v1.0.1) 