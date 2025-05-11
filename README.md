# Sistema de Venta de Entradas - Teatro Moro

## Descripción
Este programa simula el sistema de venta de entradas del Teatro Moro. Permite registrar reservas, aplicar descuentos automáticos según tipo de cliente, asignar asientos por sección y generar boletas de compra. Fue desarrollado en Java utilizando estructuras de datos eficientes y buenas prácticas de programación.

## Funcionalidades principales

- Venta de entradas con selección de sección y número de asiento.
- Aplicación automática de descuentos según el cliente:
  - Niños (menores de 12 años): 10%
  - Mujeres: 20%
  - Estudiantes: 15%
  - Personas mayores (65 años o más): 25%
- Validaciones de edad, género y condición de estudiante.
- Visualización, modificación y eliminación de reservas.
- Generación de boletas con detalle de transacción.
- Control de integridad en datos y consistencia de asientos ocupados.

## Estructuras de datos utilizadas

- `boolean[]`: para gestionar disponibilidad de asientos por sección.
- `ArrayList<String>`: para almacenar reservas activas.
- `LinkedList<String>`: para llevar un historial de transacciones confirmadas.
- Clases `Person` y `Discounts`: abstracción del cliente y lógica de descuentos.

## Validaciones implementadas

- Edad entre 0 y 120 años.
- Género válido: 'f' (femenino) o 'm' (masculino).
- Condición de estudiante: 's' o 'n'.
- Selección de asiento válida y no duplicada.
- Opciones de menú restringidas a valores permitidos.

## Pruebas realizadas

El sistema fue probado manualmente en los siguientes escenarios:

- Flujo completo de compra con distintos perfiles de cliente.
- Aplicación de todos los tipos de descuento.
- Errores de ingreso corregidos por validaciones (edad, género, opción).
- Modificación de reservas actualizando nombre, edad y cálculo de precio.
- Eliminación de reservas con liberación del asiento correspondiente.
- Generación de boletas solo para transacciones activas.

## Cómo ejecutar

1. Clona o descarga el proyecto.
2. Abre el archivo en un IDE como NetBeans, IntelliJ o Eclipse.
3. Ejecuta la clase `TeatroMorosSalesSystem`.
4. Utiliza las opciones del menú para interactuar con el sistema.

## Autor

Gustavo Domínguez Castillo
