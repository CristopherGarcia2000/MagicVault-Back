## Introducción

¡Bienvenido al repositorio de la API de MagicVault! Esta API forma parte del emocionante proyecto MagicVault, una plataforma dedicada a los entusiastas y coleccionistas de cartas MTG. Diseñada para ofrecer una experiencia completa, la API brinda una variedad de funciones que incluyen autenticación de usuarios, gestión de mazos y colecciones, búsqueda avanzada de cartas, y mucho más.

## Índice
1. [Servicios](#servicios)
   - [Autenticación](#servicio-de-autenticación)
   - [Mazos](#servicio-de-mazos)
   - [Colecciones](#servicio-de-colecciones)
   - [Cartas](#servicio-de-cartas)

## Servicios

### Servicio de Autenticación

#### Registro de Usuario
- **URL**: /auth/register
- **Método**: POST
- **Respuesta**: JSON que contiene el token de autenticación.

#### Inicio de Sesión
- **URL**: /auth/login
- **Método**: POST
- **Respuesta**: JSON que contiene el token de autenticación.

### Servicio de Mazos

#### Obtener Todos los Mazos
- **URL**: /decks
- **Método**: GET
- **Respuesta**: JSON array de todos los mazos.

#### Obtener Mazos de Usuario
- **URL**: /decks/user/:username
- **Método**: GET
- **Respuesta**: JSON array de los mazos del usuario.

#### Agregar Mazo
- **URL**: /decks
- **Método**: POST
- **Respuesta**: JSON object representando el mazo creado.

#### Eliminar Mazo
- **URL**: /decks/delete
- **Método**: DELETE
- **Respuesta**: JSON object representando el estado de la eliminación.

#### Agregar Carta al Mazo
- **URL**: /decks/addCard
- **Método**: PUT
- **Respuesta**: JSON object representando el mazo actualizado.

#### Eliminar Carta del Mazo
- **URL**: /decks/removeCard
- **Método**: DELETE
- **Respuesta**: JSON object representando el mazo actualizado.

### Servicio de Colecciones

#### Obtener Colecciones de Usuario
- **URL**: /collections/user/:username
- **Método**: GET
- **Respuesta**: JSON array de las colecciones del usuario.

#### Agregar Colección
- **URL**: /collections
- **Método**: POST
- **Respuesta**: JSON object representando la colección creada.

#### Eliminar Colección
- **URL**: /collections/delete
- **Método**: DELETE
- **Respuesta**: JSON object representando el estado de la eliminación.

#### Agregar Carta a la Colección
- **URL**: /collections/addCard
- **Método**: PUT
- **Respuesta**: JSON object representando la colección actualizada.

#### Eliminar Carta de la Colección
- **URL**: /collections/removeCard
- **Método**: DELETE
- **Respuesta**: JSON object representando la colección actualizada.

### Servicio de Cartas

#### Buscar Cartas
- **URL**: /search-cards
- **Método**: POST
- **Respuesta**: JSON array de las cartas que coinciden con los criterios de búsqueda.

#### Obtener Expansiones
- **URL**: /sets
- **Método**: GET
- **Respuesta**: JSON array de las expansiones disponibles.

#### Obtener Comandante Aleatorio
- **URL**: /random-commander
- **Método**: GET
- **Respuesta**: JSON object representando un comandante aleatorio.
