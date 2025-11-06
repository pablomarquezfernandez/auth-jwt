# Auth Service

Servicio de autenticación y gestión de usuarios/empresas con Spring Boot.

## Tecnologías

- Java 17
- Spring Boot 3.3.5
- PostgreSQL
- JWT Authentication
- Swagger/OpenAPI
- Lombok
- ModelMapper

## Configuración del Entorno

### Variables de Entorno

Crear archivo `export` en la raíz del proyecto:

```bash
export DB_HOST
export DB_PORT=5432
export DB_NAME=
export DB_TEST_NAME=
export DB_USER=
export DB_PASSWORD=
export JWT_SECRET=
export SWAGGER_PATH=/auth/swagger-ui/index.html
export API_DOCS_PATH=/auth/v3/api-docs
```

### Base de Datos

Asegurarse de que PostgreSQL esté ejecutándose y crear las bases de datos necesarias.

## SonarQube Setup - On-Premise

### 🚀 **Inicio Rápido**

#### 1. Levantar SonarQube Localmente
```bash
# Levantar SonarQube con PostgreSQL
docker-compose -f docker-compose.sonarqube.yml up -d

# Esperar a que inicie (puede tomar 1-2 minutos)
echo "Esperando a que SonarQube inicie..."
sleep 60

# Verificar que esté corriendo
curl -s http://localhost:9000 | head -n 5
```

#### 2. Acceder a SonarQube
- **URL**: http://localhost:9000
- **Usuario**: admin
- **Contraseña**: admin

#### 3. Ejecutar Análisis Local
```bash
# Análisis con configuración por defecto (localhost:9000, admin/admin)
./sonar-local.sh

# O especificar configuración personalizada
./sonar-local.sh http://tu-servidor:9000 tu-usuario tu-password
```

### 🔧 **Configuración Detallada**

#### Variables de Entorno para CI/CD
Agrega estos secrets en tu repositorio de GitHub:

```bash
SONAR_HOST_URL=http://tu-sonarqube-server:9000
SONAR_LOGIN=tu-usuario-sonarqube
SONAR_PASSWORD=tu-password-sonarqube
```

#### Configuración Personalizada
Edita `sonar-project.properties` según tus necesidades:

```properties
# Cambia estos valores según tu configuración
sonar.host.url=http://tu-sonarqube-server:9000
sonar.login=tu-usuario
sonar.password=tu-password
sonar.projectKey=tu-proyecto-unico
```

### 🐳 **Comandos Docker Útiles**

```bash
# Ver logs de SonarQube
docker-compose -f docker-compose.sonarqube.yml logs -f sonarqube

# Detener SonarQube
docker-compose -f docker-compose.sonarqube.yml down

# Reiniciar con datos limpios
docker-compose -f docker-compose.sonarqube.yml down -v
docker-compose -f docker-compose.sonarqube.yml up -d

# Ver estado de los contenedores
docker-compose -f docker-compose.sonarqube.yml ps
```

### 📊 **Verificación del Análisis**

1. Ve a http://localhost:9000
2. Inicia sesión con admin/admin
3. Ve a **Projects** → **auth**
4. Revisa las métricas:
   - **Bugs**: Problemas de código
   - **Vulnerabilities**: Vulnerabilidades de seguridad
   - **Code Smells**: Malos olores de código
   - **Coverage**: Cobertura de tests
   - **Duplications**: Código duplicado

### 🔐 **Configuración de Seguridad**

#### Cambiar Contraseña por Defecto
1. Ve a http://localhost:9000
2. Inicia sesión con admin/admin
3. Ve a **Administration** → **Security** → **Users**
4. Cambia la contraseña del usuario admin

#### Crear Token de Análisis
1. Ve a **My Account** → **Security**
2. Genera un nuevo token
3. Actualiza `sonar-project.properties` o las variables de entorno

### 📈 **Integración con CI/CD**

#### GitHub Actions (On-Premise)
El workflow está configurado para usar variables de entorno. Solo necesitas configurar los secrets en tu repositorio.

#### Configuración Personalizada
Si tienes SonarQube en un servidor diferente:

```yaml
# En tu workflow de GitHub Actions
- name: Build and analyze with SonarQube
  run: |
    mvn verify sonar:sonar \
      -Dsonar.host.url=http://tu-servidor:9000 \
      -Dsonar.login=${{ secrets.SONAR_LOGIN }} \
      -Dsonar.password=${{ secrets.SONAR_PASSWORD }}
```

### 🛠 **Solución de Problemas**

#### Error de Conexión
```bash
# Verificar que SonarQube esté corriendo
curl -s http://localhost:9000/api/system/status

# Verificar conectividad
telnet localhost 9000
```

#### Error de Autenticación
```bash
# Verificar credenciales
curl -u admin:admin http://localhost:9000/api/system/info
```

#### Problemas con JaCoCo
```bash
# Limpiar y reconstruir
mvn clean compile
mvn test
mvn jacoco:report
```

### 📚 **Recursos Adicionales**

- [Documentación Oficial de SonarQube](https://docs.sonarsource.com/sonarqube/latest/)
- [Guía de Instalación](https://docs.sonarsource.com/sonarqube/latest/setup-and-upgrade/install-the-server/)
- [Configuración de Proyectos Java](https://docs.sonarsource.com/sonarqube/latest/analyzing-source-code/scanners/sonarscanner-for-maven/)

## Ejecutar la Aplicación

```bash
# Cargar variables de entorno
source export

# Ejecutar aplicación
mvn spring-boot:run
```

## API Documentation

Una vez ejecutándose la aplicación, acceder a:
- Swagger UI: http://localhost:8080/auth/swagger-ui/index.html
- API Docs: http://localhost:8080/auth/v3/api-docs

## Endpoints Principales

### Autenticación
- `POST /auth/login` - Login de usuario
- `POST /auth/register` - Registro de usuario

### Usuarios
- `GET /users` - Listar usuarios
- `POST /users` - Crear usuario
- `GET /users/{id}` - Obtener usuario por ID
- `PUT /users/{id}` - Actualizar usuario
- `DELETE /users/{id}` - Eliminar usuario (soft delete)

### Empresas
- `GET /enterprises` - Listar empresas
- `POST /enterprises` - Crear empresa
- `GET /enterprises/{id}` - Obtener empresa por ID
- `PUT /enterprises/{id}` - Actualizar empresa
- `DELETE /enterprises/{id}` - Eliminar empresa (soft delete)

## Características

- ✅ Autenticación JWT
- ✅ Autorización basada en roles
- ✅ Soft delete con auditoría
- ✅ Validación de datos
- ✅ Manejo global de excepciones
- ✅ Documentación con Swagger
- ✅ Análisis de calidad con SonarQube
- ✅ Cobertura de tests con JaCoCo
- ✅ Integración continua con GitHub Actions
