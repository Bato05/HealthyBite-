# Healthy Bite - Registro Nutricional Inteligente 🥦

https://github.com/user-attachments/assets/187e7222-9dae-48dc-b119-06d261d73c5f

## Descripción General
Healthy Bite es una aplicación de gestión nutricional desarrollada en Android con Kotlin que permite a los usuarios registrar los alimentos consumidos durante el día, calcular su impacto calórico y mantener un historial persistente.

## Características Principales
* **Registro de Alimentos**: Ingresa el nombre del alimento, calorías base, categoría (Desayuno, Almuerzo, Cena, Snack) y especifica si es procesado.
* **Cálculo Calórico**: Calcula automáticamente las calorías totales, aplicando un recargo del 10% adicional si el alimento es "Procesado".
* **Historial / Mi Diario**: Visualiza todos los alimentos registrados durante la sesión a través de una lista interactiva.
* **Persistencia Local**: Guarda el nombre de usuario y el total de calorías acumuladas en el día utilizando `SharedPreferences`.
* **Validación**: Control estricto de ingresos (ej. no permite calorías negativas o valores vacíos, necesario de completar estrcitamente todos los campos).

## Arquitectura y Tecnologías
* **Arquitectura MVVM**: Separación clara entre la interfaz (View), la lógica matemática/negocio (ViewModel) y los datos (Model/Repository).
* **View Binding**: Acceso seguro y eficiente a las vistas de la interfaz para prescindir de `findViewById`.
* **RecyclerView**: Implementación de listas dinámicas con adaptadores y `ViewHolder` personalizados para la pantalla de "Mi Diario".
* **Navegación y Parcelable**: Transporte seguro de objetos complejos (`FoodItem`) entre pantallas utilizando el plugin `@Parcelize`.
* **LiveData**: Comunicación reactiva entre el ViewModel y las Activities.

## Estructura del Proyecto
* `Activity 1 (Registro/Login-Home page)`: Pantalla inicial para cargar la información del alimento.
* `Activity 2 (Resumen)`: Cálculo y confirmación del desglose calórico.
* `Activity 3 (Diario)`: Listado con todos los registros de la sesión.
