# Diccionario de Datos - Bibliotech

En este documento se detallan las tablas de la base de datos PostgreSQL/Supabase utilizadas para el proyecto Bibliotech.

---

## 1. Tabla: members

| Campo | Tipo | Nulo | Llave | Descripción |
| :--- | :--- | :---: | :---: | :--- |
| id | serial | no | pk | ID único del usuario |
| name | varchar(100) | no | | Nombre del usuario |
| email | varchar(150) | no | unique | Correo del usuario |
| password | varchar(255) | no | | Contraseña encriptada |
| role | varchar(20) | no | | Rol (admin, member, librarian) |
| created_at | timestamp | no | | Fecha de registro |

---

## 2. Tabla: books

| Campo | Tipo | Nulo | Llave | Descripción |
| :--- | :--- | :---: | :---: | :--- |
| id | serial | no | pk | ID del libro |
| isbn | varchar(20) | no | unique | Código ISBN |
| title | varchar(200) | no | | Título del libro |
| author | varchar(150) | no | | Autor del libro |
| publication_year | int | si | | Año de publicación |
| category | varchar(50) | si | | Categoría o género |

---

## 3. Tabla: copies

| Campo | Tipo | Nulo | Llave | Descripción |
| :--- | :--- | :---: | :---: | :--- |
| id | serial | no | pk | ID del ejemplar |
| book_id | bigint | no | fk | Relación con la tabla books |
| barcode | varchar(50) | no | unique | Código de barras de la copia |
| status | varchar(20) | no | | Estado (available, loaned, maintenance) |

---

## 4. Tabla: loans

| Campo | Tipo | Nulo | Llave | Descripción |
| :--- | :--- | :---: | :---: | :--- |
| id | serial | no | pk | ID del préstamo |
| copy_id | bigint | no | fk | ID de la copia prestada |
| member_id | bigint | no | fk | ID del usuario que prestó |
| loan_date | timestamp | no | | Fecha del préstamo |
| due_date | timestamp | no | | Fecha límite para devolver |
| return_date | timestamp | si | | Fecha real en que devolvió |
| status | varchar(20) | no | | Estado (active, returned, overdue) |

---

## 5. Tabla: fines

| Campo | Tipo | Nulo | Llave | Descripción |
| :--- | :--- | :---: | :---: | :--- |
| id | serial | no | pk | ID de la multa |
| loan_id | bigint | no | fk | ID del préstamo que generó la multa |
| amount | decimal(10,2) | no | | Monto a pagar |
| paid | boolean | no | | Estado del pago (true/false) |

---

## 6. Tabla: reservations

| Campo | Tipo | Nulo | Llave | Descripción |
| :--- | :--- | :---: | :---: | :--- |
| id | serial | no | pk | ID de la reserva |
| book_id | bigint | no | fk | ID del libro reservado |
| member_id | bigint | no | fk | ID del usuario que reservó |
| reservation_date | timestamp | no | | Fecha en que reservó |
| status | varchar(20) | no | | Estado (pending, fulfilled, cancelled) |