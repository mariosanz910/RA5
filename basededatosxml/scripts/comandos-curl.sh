# Comandos cURL para eXist-db REST API
# Ejercicio 3 - Actividad 2

# 1. Listar colección raíz
curl -u admin: http://localhost:8080/exist/rest/db

# 2. Verificar documento (devuelve 404 si no existe)
curl -u admin: -I http://localhost:8080/exist/rest/db/test.xml

# 3. Ejecutar XQuery
curl -u admin: -X POST \
  -H "Content-Type: application/xquery" \
  -d 'for $i in 1 to 5 return <num>{$i}</num>' \
  http://localhost:8080/exist/rest/db

# 4. Crear un documento XML
curl -u admin: -X PUT \
  -H "Content-Type: application/xml" \
  -d '<libro><titulo>Don Quijote</titulo></libro>' \
  http://localhost:8080/exist/rest/db/libro.xml

# 5. Leer un documento
curl -u admin: http://localhost:8080/exist/rest/db/libro.xml

# 6. Eliminar un documento
curl -u admin: -X DELETE \
  http://localhost:8080/exist/rest/db/libro.xml